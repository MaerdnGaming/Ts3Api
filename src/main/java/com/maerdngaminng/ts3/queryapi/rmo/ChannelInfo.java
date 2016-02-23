package com.maerdngaminng.ts3.queryapi.rmo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.maerdngaminng.ts3.queryapi.NotEditableObjectException;
import com.maerdngaminng.ts3.queryapi.Ts3ApiException;
import com.maerdngaminng.ts3.queryapi.Ts3ApiRMObjectParameter;
import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;

@Getter
@EqualsAndHashCode(exclude={"ts3QueryApi"})
@ToString(exclude={"ts3QueryApi"})
public class ChannelInfo implements RMObject {

	@Setter
	private Ts3QueryApi ts3QueryApi;
	
	private int channelId = -1;
	
	@Ts3ApiRMObjectParameter("pid")
	private int parentId;

	@Ts3ApiRMObjectParameter("channel_name")
	private String channelName;
	
	@Ts3ApiRMObjectParameter("channel_topic")
	private String topic;
	
	@Ts3ApiRMObjectParameter("channel_description")
	private String description;
	
	@Ts3ApiRMObjectParameter("channel_password")
	private String password;
	
	@Ts3ApiRMObjectParameter("channel_codec")
	private Codec codec;
	
	@Ts3ApiRMObjectParameter("channel_codec_quality")
	private int codecQuality;
	
	@Ts3ApiRMObjectParameter("channel_maxclients")
	private int maxClients;
	
	@Ts3ApiRMObjectParameter("channel_maxfamilyclients")
	private int maxFamilyClients;
	
	@Ts3ApiRMObjectParameter("channel_order")
	private int ordner;
	
	@Ts3ApiRMObjectParameter("channel_flag_permanent")
	private boolean flagPermanent;
	
	@Ts3ApiRMObjectParameter("channel_flag_semi_permanent")
	private boolean flagSemiPermanent;
	
	@Ts3ApiRMObjectParameter("channel_flag_default")
	private boolean flagDefault;
	
	@Ts3ApiRMObjectParameter("channel_flag_password")
	private boolean flagPassword;
	
	@Ts3ApiRMObjectParameter("channel_codec_latency_factor")
	private int codecLatencyFactor;
	
	@Ts3ApiRMObjectParameter("channel_codec_is_unencrypted")
	private boolean unencrypted;
	
	@Ts3ApiRMObjectParameter("channel_security_salt")
	private String securitySalt;
	
	@Ts3ApiRMObjectParameter("channel_flag_maxclients_unlimited")
	private boolean flagMaxClientsUnlimited;
	
	@Ts3ApiRMObjectParameter("channel_flag_maxfamilyclients_unlimited")
	private boolean flagMaxFamiliyClientsUnlimited;
	
	@Ts3ApiRMObjectParameter("channel_flag_maxfamilyclients_inherited")
	private boolean flagMaxFamiliyClientsInherited;
	
	@Ts3ApiRMObjectParameter("channel_filepath")
	private String filePath;
	
	@Ts3ApiRMObjectParameter("channel_needed_talk_power")
	private String neededTalkPower;
	
	@Ts3ApiRMObjectParameter("channel_forced_silence")
	private String forcedSilence;
	
	@Ts3ApiRMObjectParameter("channel_name_phonetic")
	private String phoneticName;
	
	@Ts3ApiRMObjectParameter("channel_icon_id")
	private int iconId;
	
	@Ts3ApiRMObjectParameter("channel_flag_private")
	private boolean flagPrivate;
	
	@Ts3ApiRMObjectParameter("seconds_empty")
	private int secondsEmpty;
	
	@Override
	public void saveChanges() throws Ts3ApiException {
		throw new NotEditableObjectException();
	}

	public void setChannelId(int channelId) {
		if(this.channelId != -1)
			throw new IllegalArgumentException("can't update singleton channelId");
		this.channelId = channelId;
	}
}
