package com.maerdngaminng.ts3.queryapi;

import java.util.List;

import com.maerdngaminng.ts3.queryapi.filter.ClientQueryFilter;
import com.maerdngaminng.ts3.queryapi.rmo.ClientInfo;
import com.maerdngaminng.ts3.queryapi.rmo.SimpleClientInfo;
import com.maerdngaminng.ts3.queryapi.rmo.HostinfoSnapshot;
import com.maerdngaminng.ts3.queryapi.rmo.InstanceInfoSnapshot;
import com.maerdngaminng.ts3.queryapi.rmo.RMObject;
import com.maerdngaminng.ts3.queryapi.rmo.ServerVersionInfo;
import com.maerdngaminng.ts3.queryapi.rmo.VirtualServerSnapshot;

/**
 * The Ts3QueryApi.
 * 
 * @author dkatz
 * @version 1.0
 */
public interface Ts3QueryApi extends AutoCloseable {
	
	/**
	 * Gets the configuration of this api object
	 * 
	 * @return the configuration
	 */
	public Ts3ConnectionInfo getTs3ConnectionInfo();
	
	/**
	 * Establishes a connection to the query port of the Ts3 server.
	 * 
	 * @throws Ts3ApiConnectException thrown if the connection fails (e.g. server is down)
	 * @throws Ts3ApiLoginException thrown if the login fails (e.g. wrong credentials)
	 * @throws Ts3ApiParameterException thrown if a server with the configured id don't exist
	 * @throws Ts3ApiException Ts3ApiException thrown if an other exception occurs
	 */
	public void connect() throws Ts3ApiException;
	
	/**
	 * Closes the connection to the Ts3 server
	 */
	public void disconnect();
	
	/**
	 * Closes the connection to the Ts3 server and establishes a connection to the query port of the Ts3 server.
	 * 
	 * @throws Ts3ApiConnectException thrown if the connection fails (e.g. server is down)
	 * @throws Ts3ApiLoginException thrown if the login fails (e.g. wrong credentials)
	 * @throws Ts3ApiParameterException thrown if a server with the configured id don't exist
	 * @throws Ts3ApiException Ts3ApiException thrown if an other exception occurs
	 */
	public void reconnect() throws Ts3ApiException;
	
	/**
	 * Switches to the virtual server with the given id.
	 * 
	 * @param serverId the id of the server to switch to
	 */
	public void setServer(int serverId) throws Ts3ApiException;
	
	/**
	 * Gets a list of all available virtual servers 
	 * 
	 * @return the list of servers
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public List<VirtualServerSnapshot> getServerList() throws Ts3ApiException;
	
	/**
	 * Escapes a string for ts3 query
	 * 
	 * @param str data to escape
	 * @return the escaped string
	 */
	public String escapeString(String str);
	
	/**
	 * Unescapes a string from ts3 query
	 * 
	 * @param str the string to unescape
	 * @return the unescaped string
	 */
	public String unescapeString(String str);
	
	/**
	 * Gets the version info for the current ts3 server
	 * 
	 * @return the {@link ServerVersionInfo} representing the version details
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public ServerVersionInfo getServerVersion() throws Ts3ApiException;
	
	/**
	 * Gets the hostinfo of the current server
	 * 
	 * @return the {@link HostinfoSnapshot} with the result data
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public HostinfoSnapshot getHostinfo() throws Ts3ApiException;
	
	/**
	 * Gets the instanceinfo of the current server
	 * 
	 * @return the {@link InstanceInfoSnapshot} with the result data
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public InstanceInfoSnapshot getInstanceInfo() throws Ts3ApiException;
	
	/**
	 * Lists all clients online on the current server
	 * 
	 * @return a {@link List} of {@link SimpleClientInfo}s
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public List<SimpleClientInfo> getOnlineClients() throws Ts3ApiException;
	
	/**
	 * Lists all clients online on the current server filtered with the given filter
	 * 
	 * <b>Warning: </b>Executes n+1 query. n is the number of online clients.
	 * 
	 * @param filter a filter to filter the query or null for no filter
	 * @return a filtered {@link List} of {@link ClientInfo}s
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public List<ClientInfo> getOnlineClientsExtended(ClientQueryFilter filter) throws Ts3ApiException;
	
	/**
	 * Gets a {@link ClientInfo} representing the client with the given clid
	 * 
	 * @return the {@link ClientInfo} for the clid
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public ClientInfo getClientInfo(int clid) throws Ts3ApiException;
	
	/**
	 * Writes the changes of an {@link RMObject}-Object to the ts3 server
	 * 
	 * @param newObj the object which has been changed
	 * @param command the command to use to save the changes
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public void saveChanges(RMObject newObj, String command) throws Ts3ApiException;
}