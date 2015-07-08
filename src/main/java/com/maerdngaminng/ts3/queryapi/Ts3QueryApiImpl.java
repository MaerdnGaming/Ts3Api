package com.maerdngaminng.ts3.queryapi;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import com.maerdngaminng.ts3.queryapi.filter.ClientQueryFilter;
import com.maerdngaminng.ts3.queryapi.rmo.ClientInfo;
import com.maerdngaminng.ts3.queryapi.rmo.SimpleClientInfo;
import com.maerdngaminng.ts3.queryapi.rmo.HostinfoSnapshot;
import com.maerdngaminng.ts3.queryapi.rmo.InstanceInfoSnapshot;
import com.maerdngaminng.ts3.queryapi.rmo.RMObject;
import com.maerdngaminng.ts3.queryapi.rmo.ServerVersionInfo;
import com.maerdngaminng.ts3.queryapi.rmo.VirtualServerSnapshot;
import com.maerdngaminng.ts3.queryapi.rmo.mapper.DataMappingManager;

@Data
@RequiredArgsConstructor(access=AccessLevel.PACKAGE)
public class Ts3QueryApiImpl implements Ts3QueryApi {

	private final Ts3ConnectionInfo ts3ConnectionInfo;
	private Socket socket = null;
	private Scanner scanner = null;
	private PrintStream printStream = null;

	private boolean isAllAvalible() {
		return this.socket != null && this.socket.isConnected() && !this.socket.isClosed() && this.scanner != null && this.printStream != null;
	}

	private boolean initConnection() throws Ts3ApiConnectException {
		String line1;
		if (this.scanner.hasNextLine()) {
			line1 = this.scanner.nextLine();
			if (!line1.equals("TS3"))
				throw new Ts3ApiConnectException("Faild to connect to ts3 server: invalid server promt (no ts3 server)");
			this.scanner.hasNextLine();
			this.scanner.nextLine();
			return true;
		}
		return false;
	}

	private QueryCommandResult sendCommandAndCheckResultWithData(String command) throws Ts3ApiException {
		QueryCommandResult result = this.sendCommand(command);
		if (result.getId() != 0)
			throw new Ts3QueryCommandInvalidResultException(result);
		if (result.getResultList().size() < 1)
			throw new Ts3QueryCommandInvalidResultException(result);
		return result;
	}

	private QueryCommandResult sendCommand(String command) throws Ts3ApiException {
		if (!this.isAllAvalible())
			throw new Ts3ApiException("Connection is not available");
		this.printStream.println(command);
		List<String> resultLines = new LinkedList<>();
		String result;
		int id = 0;
		String msg = null;
		int failed_permid = 0;
		while (this.scanner.hasNextLine()) {
			result = this.scanner.nextLine();
			if (!result.startsWith("error id=")) {
				resultLines.add(result);
				continue;
			}
			result = result.substring(6);
			String[] c = result.split(" ");
			for (String x : c) {
				if (x.startsWith("id")) {
					String[] d = x.split("=");
					id = new Integer(d[1]);
				} else if (x.startsWith("msg")) {
					String[] d = x.split("=");
					msg = this.unescapeString(d[1]);
				} else if (x.startsWith("failed_permid")) {
					String[] d = x.split("=");
					failed_permid = new Integer(d[1]);
				}
			}
			break;
		}
		return new QueryCommandResult(id, msg, failed_permid, resultLines);
	}

	private boolean login() throws Ts3ApiException {
		QueryCommandResult result = this.sendCommand("login " + this.escapeString(this.getTs3ConnectionInfo().getUsername()) + " "
				+ this.escapeString(this.getTs3ConnectionInfo().getPassword()));
		return result.getId() == 0 && result.getMsg().equals("ok");
	}

	private void openConnection() throws UnknownHostException, IOException {
		this.socket = new Socket(this.getTs3ConnectionInfo().getHostname(), this.getTs3ConnectionInfo().getPort());
		this.scanner = new Scanner(this.socket.getInputStream());
		this.printStream = new PrintStream(this.socket.getOutputStream());
	}

	protected String buildCommandString(RMObject newObj, String command) throws Ts3ApiException {
		StringBuilder execStringBuilder = new StringBuilder();
		try {
			for (Field f : newObj.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (f.isAnnotationPresent(Ts3ApiRMObjectParameter.class)) {
					Ts3ApiRMObjectParameter ts3ApiRMOParameter = f.getAnnotation(Ts3ApiRMObjectParameter.class);
					if(!ts3ApiRMOParameter.editable()) continue;
					
					Object newData = f.get(newObj);

					if (newData == null)
						continue;

					execStringBuilder.append(' ');
					execStringBuilder.append(ts3ApiRMOParameter.value() + "=" + this.escapeString(newData.toString()));
				}
			}
			return command + execStringBuilder.toString();
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new Ts3ApiException(e);
		}
	}

	@Override
	public void connect() throws Ts3ApiException {
		try {
			this.openConnection();
		} catch (IOException e) {
			throw new Ts3ApiConnectException("Failed to connect to ts3 server", e);
		}
		if (!this.initConnection())
			throw new Ts3ApiConnectException("Faild to connect to ts3 server");
		if (this.getTs3ConnectionInfo().getUsername() != null && this.getTs3ConnectionInfo().getPassword() != null) {
			try {
				if (!this.login()) {
					throw new Ts3ApiConnectException("Faild to login to ts3 server");
				}
			} catch (Ts3ApiException e) {
				throw new Ts3ApiLoginException("Failed to login to ts3 server", e);
			}
		}
		if(this.getTs3ConnectionInfo().getTs3ServerId() != 0) {
			this.setServer(this.getTs3ConnectionInfo().getTs3ServerId());
		}
	}

	@Override
	public void disconnect() {
		try {
			this.printStream.println("quit");
			this.socket.close();
		} catch (IOException e) {
		}
		this.socket = null;
	}

	@Override
	public void reconnect() throws Ts3ApiException {
		this.disconnect();
		this.connect();
	}

	@Override
	public void setServer(int serverId) throws Ts3ApiException {
		QueryCommandResult result = this.sendCommand("use " + serverId);
		if (result.getId() != 0)
			throw new Ts3QueryCommandInvalidResultException(result);
	}

	@Override
	public List<VirtualServerSnapshot> getServerList() throws Ts3ApiException {
		QueryCommandResult result = this.sendCommandAndCheckResultWithData("serverlist");
		try {
			return DataMappingManager.mapListToObjectList(VirtualServerSnapshot.class, result.getFirstResultLine(), this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new Ts3ApiException(e);
		}
	}

	@Override
	public String escapeString(String str) {
		return str.replace(new String(new char[] { 92 }), new String(new char[] { 92, 92 })).replace(new String(new char[] { 47 }), new String(new char[] { 92, 47 }))
				.replace(new String(new char[] { 32 }), new String(new char[] { 92, 115 })).replace(new String(new char[] { 124 }), new String(new char[] { 92, 112 }))
				.replace(new String(new char[] { 8 }), new String(new char[] { 92, 98 })).replace(new String(new char[] { 12 }), new String(new char[] { 92, 102 }))
				.replace(new String(new char[] { 10 }), new String(new char[] { 92, 110 })).replace(new String(new char[] { 13 }), new String(new char[] { 92, 114 }))
				.replace(new String(new char[] { 9 }), new String(new char[] { 92, 116 })).replace(new String(new char[] { 11 }), new String(new char[] { 92, 118 }));
	}

	@Override
	public String unescapeString(String str) {
		return str.replace(new String(new char[] { 92, 92 }), new String(new char[] { 92 })).replace(new String(new char[] { 92, 47 }), new String(new char[] { 47 }))
				.replace(new String(new char[] { 92, 115 }), new String(new char[] { 32 })).replace(new String(new char[] { 92, 112 }), new String(new char[] { 124 }))
				.replace(new String(new char[] { 92, 98 }), new String(new char[] { 8 })).replace(new String(new char[] { 92, 102 }), new String(new char[] { 12 }))
				.replace(new String(new char[] { 92, 110 }), new String(new char[] { 10 })).replace(new String(new char[] { 92, 114 }), new String(new char[] { 13 }))
				.replace(new String(new char[] { 92, 116 }), new String(new char[] { 9 })).replace(new String(new char[] { 92, 118 }), new String(new char[] { 11 }));
	}

	@Override
	public ServerVersionInfo getServerVersion() throws Ts3ApiException {
		QueryCommandResult result = this.sendCommandAndCheckResultWithData("serverlist");
		try {
			return DataMappingManager.mapToObject(ServerVersionInfo.class, result.getFirstResultLine(), this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new Ts3ApiException(e);
		}
	}

	@Override
	public HostinfoSnapshot getHostinfo() throws Ts3ApiException {
		QueryCommandResult result = this.sendCommandAndCheckResultWithData("hostinfo");
		try {
			return DataMappingManager.mapToObject(HostinfoSnapshot.class, result.getFirstResultLine(), this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new Ts3ApiException(e);
		}
	}

	@Override
	public InstanceInfoSnapshot getInstanceInfo() throws Ts3ApiException {
		QueryCommandResult result = this.sendCommandAndCheckResultWithData("instanceinfo");
		try {
			return DataMappingManager.mapToObject(InstanceInfoSnapshot.class, result.getFirstResultLine(), this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new Ts3ApiException(e);
		}
	}

	@Override
	public void saveChanges(RMObject newObj, String command) throws Ts3ApiException {
		QueryCommandResult result = this.sendCommand(this.buildCommandString(newObj, command));
		if (result.getId() != 0)
			throw new Ts3QueryCommandInvalidResultException(result);
	}

	@Override
	public List<SimpleClientInfo> getOnlineClients() throws Ts3ApiException {
		QueryCommandResult result = this.sendCommandAndCheckResultWithData("clientlist");
		try {
			return DataMappingManager.mapListToObjectList(SimpleClientInfo.class, result.getFirstResultLine(), this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new Ts3ApiException(e);
		}
	}

	@Override
	public List<ClientInfo> getOnlineClientsExtended(ClientQueryFilter filter) throws Ts3ApiException {
		List<ClientInfo> resultList = new LinkedList<>();
		for(SimpleClientInfo sci : this.getOnlineClients()) {
			ClientInfo info = this.getClientInfo(sci.getClid());
			if(filter == null || filter.accept(info))
				resultList.add(info);
		}
		return resultList;
	}
	
	@Override
	public ClientInfo getClientInfo(int clid) throws Ts3ApiException {
		QueryCommandResult result = this.sendCommandAndCheckResultWithData("clientinfo clid="+clid);
		try {
			return DataMappingManager.mapToObject(ClientInfo.class, result.getFirstResultLine(), this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new Ts3ApiException(e);
		}
	}

	@Override
	public void close() throws Exception {
		this.disconnect();
	}
}
