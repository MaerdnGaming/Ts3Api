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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.Data;

import com.maerdngaminng.ts3.queryapi.filter.ClientInfoFilter;
import com.maerdngaminng.ts3.queryapi.rmo.ChannelInfo;
import com.maerdngaminng.ts3.queryapi.rmo.ClientDBInfo;
import com.maerdngaminng.ts3.queryapi.rmo.ClientInfo;
import com.maerdngaminng.ts3.queryapi.rmo.HostinfoSnapshot;
import com.maerdngaminng.ts3.queryapi.rmo.InstanceInfoSnapshot;
import com.maerdngaminng.ts3.queryapi.rmo.RMObject;
import com.maerdngaminng.ts3.queryapi.rmo.ServerGroupClientInfo;
import com.maerdngaminng.ts3.queryapi.rmo.ServerVersionInfo;
import com.maerdngaminng.ts3.queryapi.rmo.SimpleClientInfo;
import com.maerdngaminng.ts3.queryapi.rmo.VirtualServerSnapshot;
import com.maerdngaminng.ts3.queryapi.rmo.mapper.DataMappingManager;

@Data
public class Ts3QueryApiImpl implements Ts3QueryApi {

	private final Ts3ConnectionInfo ts3ConnectionInfo;
	private Socket socket = null;
	private Scanner scanner = null;
	private Thread messageReaderThread;
	private AtomicBoolean stop = new AtomicBoolean(false);
	private PrintStream printStream = null;
	private BlockingQueue<QueryMessage> resultMessageQueue = new LinkedBlockingQueue<>();

	protected Ts3QueryApiImpl(Ts3ConnectionInfo ts3ConnectionInfo) {
		this.ts3ConnectionInfo = ts3ConnectionInfo;
	}
	
	private boolean isAllAvalible() {
		return this.socket != null && this.socket.isConnected() && !this.socket.isClosed() && this.scanner != null && this.printStream != null;
	}
	
	private void messageReader() {
		try {
			String result = "";
			List<String> dataLines = new LinkedList<>();
			while (!this.stop.get() && this.scanner.hasNextLine()) {
				result = this.scanner.nextLine();
				if(result.trim().equals("")) {
					continue;
				}
				boolean data = false;
				if (result.startsWith("error")) {
					QueryMessage msg = new QueryMessage(result, dataLines);
					this.resultMessageQueue.put(msg);
				} else if (result.startsWith("TS3")) {
					//Handle ready
				} else if (result.startsWith("Welcome")) {
					//Handle greething
				} else if (result.startsWith("notify")) {
					//Handle notify
				} else if (result.startsWith("selected")) {
					//Handle selected
				} else {
					data = true;
					dataLines.add(result);
				}
				if(!data)
					dataLines = new LinkedList<>();
			}
		}catch(Throwable t) {
			t.printStackTrace();
		}
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
		String result;
		int id = 0;
		String msg = null;
		int failed_permid = 0;
		
		try {
			QueryMessage qmsg = this.resultMessageQueue.take();
			result = qmsg.getMainMessage().substring(6);
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
			return new QueryCommandResult(id, msg, failed_permid, qmsg.getDataMessages());
		} catch (InterruptedException e) {
			throw new Ts3ApiException(e);
		}
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
		messageReaderThread = new Thread(this::messageReader, "Ts3ApiMessageReaderThread");
		messageReaderThread.start();
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
	public synchronized void connect() throws Ts3ApiException {
		try {
			this.openConnection();
		} catch (IOException e) {
			throw new Ts3ApiConnectException("Failed to connect to ts3 server", e);
		}
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
	public synchronized void disconnect() {
		try {
			this.stop.set(true);
			this.printStream.println("quit");
			this.messageReaderThread.join();
			this.socket.close();
		} catch (IOException | InterruptedException e) {
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
	public List<ClientInfo> getOnlineClientsExtended(ClientInfoFilter filter) throws Ts3ApiException {
		List<ClientInfo> resultList = new LinkedList<>();
		for(SimpleClientInfo sci : this.getOnlineClients()) {
			ClientInfo info = this.getClientInfo(sci.getClientId());
			if(filter == null || filter.accept(info))
				resultList.add(info);
		}
		return resultList;
	}
	
	@Override
	public ClientInfo getClientInfo(int clid) throws Ts3ApiException {
		QueryCommandResult result = this.sendCommandAndCheckResultWithData("clientinfo clid="+clid);
		try {
			ClientInfo ci = DataMappingManager.mapToObject(ClientInfo.class, result.getFirstResultLine(), this);
			ci.setClientId(clid);
			return ci;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new Ts3ApiException(e);
		}
	}
	
	@Override
	public ClientDBInfo getDbClientInfo(int cldbid) throws Ts3ApiException {
		QueryCommandResult result = this.sendCommandAndCheckResultWithData("clientdbinfo cldbid="+cldbid);
		try {
			ClientDBInfo ci = DataMappingManager.mapToObject(ClientDBInfo.class, result.getFirstResultLine(), this);
			return ci;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new Ts3ApiException(e);
		}
	}

	@Override
	public void close() throws Exception {
		this.disconnect();
	}

	@Override
	public List<SimpleClientInfo> getSimpleClientsInChannel(int channelId) throws Ts3ApiException {
		QueryCommandResult result = this.sendCommandAndCheckResultWithData("clientlist");
		try {
			List<SimpleClientInfo> scil = DataMappingManager.mapListToObjectList(SimpleClientInfo.class, result.getFirstResultLine(), this);
			List<SimpleClientInfo> resList = new LinkedList<>();
			for(SimpleClientInfo sci : scil) {
				if(sci.getChannelId() == channelId)
					resList.add(sci);
			}
			return resList;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new Ts3ApiException(e);
		}
	}

	@Override
	public List<ClientInfo> getClientsInChannel(int channelId) throws Ts3ApiException {
		return this.getClientsInChannel(channelId, null);
	}
	
	@Override
	public List<ClientInfo> getClientsInChannel(int channelId, ClientInfoFilter filter) throws Ts3ApiException {
		List<ClientInfo> resultList = new LinkedList<>();
		for(SimpleClientInfo sci : this.getSimpleClientsInChannel(channelId)) {
			ClientInfo info = this.getClientInfo(sci.getClientId());
			if(filter == null || filter.accept(info))
				resultList.add(info);
		}
		return resultList;
	}

	@Override
	public ChannelInfo getChannelInfo(int channelId) throws Ts3ApiException {
		QueryCommandResult result = this.sendCommandAndCheckResultWithData("channelinfo cid="+channelId);
		try {
			ChannelInfo ci = DataMappingManager.mapToObject(ChannelInfo.class, result.getFirstResultLine(), this);
			ci.setChannelId(channelId);
			return ci;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new Ts3ApiException(e);
		}
	}

	@Override
	public void removeClientFromGroup(int clientDatabaseId, int serverGroupId) throws Ts3ApiException {
		QueryCommandResult result = this.sendCommand("servergroupdelclient sgid=" + serverGroupId + " cldbid=" + clientDatabaseId);
		if (result.getId() != 0)
			throw new Ts3QueryCommandInvalidResultException(result);
	}

	@Override
	public void addClientToGroup(int clientDatabaseId, int serverGroupId) throws Ts3ApiException {
		QueryCommandResult result = this.sendCommand("servergroupaddclient sgid=" + serverGroupId + " cldbid=" + clientDatabaseId);
		if (result.getId() != 0)
			throw new Ts3QueryCommandInvalidResultException(result);
	}

	@Override
	public List<ServerGroupClientInfo> getClientServerGroups(int clientDatabaseId) throws Ts3ApiException {
		QueryCommandResult result = this.sendCommandAndCheckResultWithData("servergroupsbyclientid cldbid="+clientDatabaseId);
		try {
			return DataMappingManager.mapListToObjectList(ServerGroupClientInfo.class, result.getFirstResultLine(), this);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			throw new Ts3ApiException(e);
		}
	}

	@Override
	public void setClientChannelGroup(int clientDatabaseId, int channelGroupId, int channelId) throws Ts3ApiException {
		QueryCommandResult result = this.sendCommand("setclientchannelgroup cgid="+channelGroupId+" cid="+channelId+" cldbid="+clientDatabaseId);
		if (result.getId() != 0)
			throw new Ts3QueryCommandInvalidResultException(result);
	}
}
