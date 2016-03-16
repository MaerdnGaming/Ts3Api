package com.maerdngaminng.ts3.queryapi;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.maerdngaminng.ts3.queryapi.filter.ClientInfoFilter;
import com.maerdngaminng.ts3.queryapi.rmo.ChannelInfo;
import com.maerdngaminng.ts3.queryapi.rmo.ChannelPermission;
import com.maerdngaminng.ts3.queryapi.rmo.ClientDBInfo;
import com.maerdngaminng.ts3.queryapi.rmo.ClientInfo;
import com.maerdngaminng.ts3.queryapi.rmo.CreateChannelProperty;
import com.maerdngaminng.ts3.queryapi.rmo.HostinfoSnapshot;
import com.maerdngaminng.ts3.queryapi.rmo.InstanceInfoSnapshot;
import com.maerdngaminng.ts3.queryapi.rmo.RMObject;
import com.maerdngaminng.ts3.queryapi.rmo.ServerGroupClientInfo;
import com.maerdngaminng.ts3.queryapi.rmo.ServerVersionInfo;
import com.maerdngaminng.ts3.queryapi.rmo.SimpleClientInfo;
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
	 * Enabled the debug mode
	 * @param enabled true if enabled
	 */
	public void setDebug(boolean enabled);
	
	/**
	 * Returns the current debug state
	 * @return true if debug is enabled
	 */
	public boolean isDebug();
	
	/**
	 * Closes the connection to the Ts3 server and establishes a connection to the query port of the Ts3 server.
	 * 
	 * @throws Ts3ApiConnectException thrown if the connection fails (e.g. server is down)
	 * @throws Ts3ApiLoginException thrown if the login fails (e.g. wrong credentials)
	 * @throws Ts3ApiParameterException thrown if a server with   configured id don't exist
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
	public List<ClientInfo> getOnlineClientsExtended(ClientInfoFilter filter) throws Ts3ApiException;
	
	/**
	 * Gets a {@link ClientInfo} representing the client with the given clientId
	 * 
	 * @return the {@link ClientInfo} for the clientId
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public ClientInfo getClientInfo(int clientId) throws Ts3ApiException;
	
	/**
	 * Gets a {@link ClientDBInfo} representing the client with the given databaseId
	 * 
	 * @param cldbid the databaseId
	 * @return the {@link ClientDBInfo} for the databaseId
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public ClientDBInfo getDbClientInfo(int cldbid) throws Ts3ApiException;
	
	/**
	 * Writes the changes of an {@link RMObject}-Object to the ts3 server
	 * 
	 * @param newObj the object which has been changed
	 * @param command the command to use to save the changes
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public void saveChanges(RMObject newObj, String command) throws Ts3ApiException;
	
	/**
	 * Lists all clients in the given channel as {@link SimpleClientInfo}
	 * 
	 * @param channelId the id of the channel to search
	 * @return a list of {@link SimpleClientInfo}
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public List<SimpleClientInfo> getSimpleClientsInChannel(int channelId) throws Ts3ApiException;
	
	/**
	 * Lists all clients in the given channel as {@link ClientInfo}
	 * 
	 * @param channelId the id of the channel to search
	 * @return a list of {@link ClientInfo}
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public List<ClientInfo> getClientsInChannel(int channelId) throws Ts3ApiException;
	
	/**
	 * Lists all clients in the given channel as {@link ClientInfo} filtered by the given filter
	 * 
	 * @param channelId the id of the channel to search
	 * @param filter the filter to filter the clients
	 * @return a list of {@link ClientInfo}
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public List<ClientInfo> getClientsInChannel(int channelId, ClientInfoFilter filter) throws Ts3ApiException;
	
	/**
	 * Gets a {@link ChannelInfo} representing the channel with the given channelId
	 * 
	 * @return the {@link ChannelInfo} for the channelId
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public ChannelInfo getChannelInfo(int channelId) throws Ts3ApiException;
	
	/**
	 * Removes a server group from a client
	 * 
	 * @param clientDatabaseId the database id of the client to remove the group
	 * @param serverGroupId the id of the ts3 server group to remove
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public void removeClientFromGroup(int clientDatabaseId, int serverGroupId) throws Ts3ApiException;
	
	/**
	 * Adds a server group to a client
	 * 
	 * @param clientDatabaseId the database id of the client to add the group
	 * @param serverGroupId the id of the ts3 server group to add
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public void addClientToGroup(int clientDatabaseId, int serverGroupId) throws Ts3ApiException;
	
	/**
	 * Sets a channel group to a client
	 * 
	 * @param clientDatabaseId the database id of the client to add the group
	 * @param serverGroupId the id of the ts3 server group to add
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public void setClientChannelGroup(int clientDatabaseId, int channelGroupId, int channelId) throws Ts3ApiException;
	
	/**
	 * Lists basic informations of the server groups for the given client as {@link ServerGroupClientInfo}
	 * 
	 * @param clientDatabaseId the database id of the client
	 * @return a list of {@link ServerGroupClientInfo}
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public List<ServerGroupClientInfo> getClientServerGroups(int clientDatabaseId) throws Ts3ApiException;
	
	/**
	 * Creates a new Channel and returns the channel id
	 * 
	 * @param name the name of the channel
	 * @param properties the configuration of the channel
	 * @return the if of the new channel
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public int createChannel(String name, Map<CreateChannelProperty, String> properties) throws Ts3ApiException;
	
	/**
	 * Adds the given permissions to the given channel
	 * 
	 * @param channelId the id of the channel
	 * @param permissions a set of permissions to add
	 * @throws Ts3ApiException thrown if an exception occurs
	 */
	public void channelAddPermission(int channelId, Set<ChannelPermission> permissions) throws Ts3ApiException;
}
