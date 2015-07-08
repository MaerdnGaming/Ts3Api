package com.maerdngaminng.ts3.queryapi.rmo;

import java.math.BigInteger;

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
public class InstanceInfoSnapshot implements RMObject {

	@Setter
	private Ts3QueryApi ts3QueryApi;
	
	@Ts3ApiRMObjectParameter("serverinstance_database_version")
	private int serverDatabaseVersion;
	
	@Setter
	@Ts3ApiRMObjectParameter("serverinstance_filetransfer_port")
	private int filetransferPort;
	
	@Setter
	@Ts3ApiRMObjectParameter(value="serverinstance_max_download_total_bandwidth",editable=true)
	private BigInteger maxDownloadBandwith;
	
	@Setter
	@Ts3ApiRMObjectParameter(value="serverinstance_max_upload_total_bandwidth",editable=true)
	private BigInteger maxUploadBandwith;
	
	@Setter
	@Ts3ApiRMObjectParameter(value="serverinstance_guest_serverquery_group",editable=true)
	private int guestServerQueryGroup;
	
	@Ts3ApiRMObjectParameter("serverinstance_serverquery_flood_commands")
	private int serverQueryFloodCommands;
	
	@Ts3ApiRMObjectParameter("serverinstance_serverquery_flood_time")
	private int serverQueryFloodTime;
	
	@Ts3ApiRMObjectParameter("serverinstance_serverquery_ban_time")
	private int serverQueryBanTime;
	
	@Ts3ApiRMObjectParameter("serverinstance_template_serveradmin_group")
	private int templateServerAdminGroup;
	
	@Ts3ApiRMObjectParameter("serverinstance_template_serverdefault_group")
	private int templateServerDefaultGroup;
	
	@Ts3ApiRMObjectParameter("serverinstance_template_channeladmin_group")
	private int templateChannelAdminGroup;
	
	@Ts3ApiRMObjectParameter("serverinstance_template_channeldefault_group")
	private int templateChannelDefaultGroup;
	
	@Ts3ApiRMObjectParameter("serverinstance_permissions_version")
	private int serverPermissionsVersion;
	
	@Ts3ApiRMObjectParameter("serverinstance_pending_connections_per_ip")
	private int serverPendungConnectionsPerIp;

	public void saveChanges() throws Ts3ApiException {
		this.getTs3QueryApi().saveChanges(this, "instanceedit");
	}
}
