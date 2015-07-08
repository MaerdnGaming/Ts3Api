package com.maerdngaminng.ts3.queryapi.rmo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.maerdngaminng.ts3.queryapi.Ts3ApiException;
import com.maerdngaminng.ts3.queryapi.Ts3ApiRMObjectParameter;
import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;

@Getter
@EqualsAndHashCode(exclude={"ts3QueryApi"})
@ToString(exclude={"ts3QueryApi"})
public class VirtualServerSnapshot implements RMObject {
	
	@Setter
	private Ts3QueryApi ts3QueryApi;
	
	@Ts3ApiRMObjectParameter("virtualserver_id")
	private int serverId;
	
	@Ts3ApiRMObjectParameter("virtualserver_port")
	private int serverPort;
	
	@Ts3ApiRMObjectParameter("virtualserver_status")
	private VirtualServerState serverStatus;
	
	@Ts3ApiRMObjectParameter("virtualserver_clientsonline")
	private int clientsOnline;
	
	@Ts3ApiRMObjectParameter("virtualserver_queryclientsonline")
	private int queryClientsOnline;
	
	@Ts3ApiRMObjectParameter("virtualserver_maxclients")
	private int maxClients;
	
	@Ts3ApiRMObjectParameter("virtualserver_uptime")
	private int uptime;
	
	@Ts3ApiRMObjectParameter("virtualserver_name")
	private String name;
	
	@Ts3ApiRMObjectParameter("virtualserver_autostart")
	private boolean autostart;

	public void saveChanges() throws Ts3ApiException {
		// TODO Auto-generated method stub
	}
}
