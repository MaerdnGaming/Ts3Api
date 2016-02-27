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
public class ClientDBInfo implements RMObject {

	@Setter
	private Ts3QueryApi ts3QueryApi;
	
	@Ts3ApiRMObjectParameter("cid")
	private int channelId;

	@Ts3ApiRMObjectParameter("client_database_id")
	private int clientDatabaseId;
	
	@Ts3ApiRMObjectParameter("client_idle_time")
	private int clientIdleTime;
	
	@Ts3ApiRMObjectParameter("client_unique_identifier")
	private String clientUniqueId;
	
	@Ts3ApiRMObjectParameter("client_nickname")
	private String clientName;

	@Ts3ApiRMObjectParameter("client_version")
	private String clientVersion;
	
	@Ts3ApiRMObjectParameter("client_platform")
	private String clientPlatform;
	
	@Ts3ApiRMObjectParameter("client_input_muted")
	private boolean inputMuted;
	
	@Ts3ApiRMObjectParameter("client_output_muted")
	private boolean outputMuted;
	
	@Ts3ApiRMObjectParameter("client_outputonly_muted")
	private boolean outputOnlyMuted;
	
	@Ts3ApiRMObjectParameter("client_input_hardware")
	private boolean inputHardware;
	
	@Ts3ApiRMObjectParameter("client_output_hardware")
	private boolean outputHardware;
	
	@Ts3ApiRMObjectParameter("client_default_channel")
	private String clientDefaultChannel;
	
	@Ts3ApiRMObjectParameter("client_is_recording")
	private boolean recording;
	
	@Ts3ApiRMObjectParameter("client_version_sign")
	private String clientVersionSign;
	
	@Ts3ApiRMObjectParameter("client_channel_group_id")
	private int clientChannelGroupId;
	
	@Ts3ApiRMObjectParameter("client_servergroups")
	private CommaSeperatedIntegerList clientServerGroups;
	
	@Ts3ApiRMObjectParameter("client_created")
	private int clientCreated;
	
	@Ts3ApiRMObjectParameter("client_lastconnected")
	private int clientLastConnected;
	
	@Ts3ApiRMObjectParameter("client_totalconnections")
	private int totalConnections;
	
	@Ts3ApiRMObjectParameter("client_meta_data")
	private String metaData;
	
	@Ts3ApiRMObjectParameter("client_security_hash")
	private String securityHash;
	
	@Ts3ApiRMObjectParameter("client_login_name")
	private String loginName;
	
	@Ts3ApiRMObjectParameter("client_away")
	private boolean away;
	
	@Ts3ApiRMObjectParameter("client_away_message")
	private String awayMessage;
	
	@Ts3ApiRMObjectParameter("client_type")
	private ClientType clientType;
	
	@Ts3ApiRMObjectParameter("client_flag_avatar")
	private String clientFlagAvatar;
	
	@Ts3ApiRMObjectParameter("client_talk_power")
	private int talkPower;
	
	@Ts3ApiRMObjectParameter("client_talk_request")
	private boolean talkRequest;
	
	@Ts3ApiRMObjectParameter("client_talk_request_msg")
	private String talkRequestMessage;

	@Setter
	@Ts3ApiRMObjectParameter(value="client_description",editable=true)
	private String description;
	
	@Ts3ApiRMObjectParameter("client_is_talker")
	private boolean talker;
	
	@Ts3ApiRMObjectParameter("client_month_bytes_uploaded")
	private long monthBytesUploaded;
	
	@Ts3ApiRMObjectParameter("client_month_bytes_downloaded")
	private long monthBytesDownloaded;
	
	@Ts3ApiRMObjectParameter("client_total_bytes_uploaded")
	private long totalBytesUploaded;
	
	@Ts3ApiRMObjectParameter("client_total_bytes_downloaded")
	private long totalBytesDownloaded;
	
	@Ts3ApiRMObjectParameter("client_is_priority_speaker")
	private boolean prioritySpeaker;
	
	@Ts3ApiRMObjectParameter("client_nickname_phonetic")
	private String phoneticNickname;
	
	@Ts3ApiRMObjectParameter("client_needed_serverquery_view_power")
	private int clientNeededServerqueryViewPower;
	
	@Ts3ApiRMObjectParameter("client_default_token")
	private String defaultToken;
	
	@Ts3ApiRMObjectParameter("client_icon_id")
	private int iconId;
	
	@Ts3ApiRMObjectParameter("client_is_channel_commander")
	private boolean channelCommander;
	
	@Ts3ApiRMObjectParameter("client_country")
	private String clientCountry;
	
	@Ts3ApiRMObjectParameter("client_channel_group_inherited_channel_id")
	private int channelGroupInheritedChannelId;
	
	@Ts3ApiRMObjectParameter("client_badges")
	private String clientBadges;
	
	@Ts3ApiRMObjectParameter("client_base64HashClientUID")
	private String base64HashClientUID;
	
	@Ts3ApiRMObjectParameter("connection_filetransfer_bandwidth_sent")
	private long filetransferBandwidthSent;
	
	@Ts3ApiRMObjectParameter("connection_filetransfer_bandwidth_received")
	private long filetransferBandwidthReceived;
	
	@Ts3ApiRMObjectParameter("connection_packets_sent_total")
	private long packetsSentTotal;
	
	@Ts3ApiRMObjectParameter("connection_bytes_sent_total")
	private long bytesSentTotal;
	
	@Ts3ApiRMObjectParameter("connection_packets_received_total")
	private long packetsReceivedTotal;
	
	@Ts3ApiRMObjectParameter("connection_bytes_received_total")
	private long bytesReceivedTotal;
	
	@Ts3ApiRMObjectParameter("connection_bandwidth_sent_last_second_total")
	private long bandwidthSentLastSecondTotal;
	
	@Ts3ApiRMObjectParameter("connection_bandwidth_sent_last_minute_total")
	private long bandwidthSentLastMinuteTotal;
	
	@Ts3ApiRMObjectParameter("connection_bandwidth_received_last_second_total")
	private long bandwidthReceivedLastSecondTotal;
	
	@Ts3ApiRMObjectParameter("connection_bandwidth_received_last_minute_total")
	private long bandwidthReceivedLastMinuteTotal;
	
	@Ts3ApiRMObjectParameter("connection_connected_time")
	private int connectionTime;
	
	@Ts3ApiRMObjectParameter("connection_client_ip")
	private String clientIp;

	@Override
	public void saveChanges() throws Ts3ApiException {
		//clientedit clid=10 client_description=Best\sguy\sever!
		this.getTs3QueryApi().saveChanges(this, "clientdbedit cldbid="+this.getClientDatabaseId());
	}

}
