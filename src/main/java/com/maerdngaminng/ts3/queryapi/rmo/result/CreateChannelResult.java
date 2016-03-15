package com.maerdngaminng.ts3.queryapi.rmo.result;

import com.maerdngaminng.ts3.queryapi.NotEditableObjectException;
import com.maerdngaminng.ts3.queryapi.Ts3ApiException;
import com.maerdngaminng.ts3.queryapi.Ts3ApiRMObjectParameter;
import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;
import com.maerdngaminng.ts3.queryapi.rmo.RMObject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(exclude={"ts3QueryApi"})
@ToString(exclude={"ts3QueryApi"})
public class CreateChannelResult implements RMObject {

	@Setter
	private Ts3QueryApi ts3QueryApi;
	
	@Ts3ApiRMObjectParameter("cid")
	private int channelId = -1;

	@Override
	public void saveChanges() throws Ts3ApiException {
		throw new NotEditableObjectException();
	}
	
}
