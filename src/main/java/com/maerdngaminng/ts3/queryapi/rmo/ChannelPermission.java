package com.maerdngaminng.ts3.queryapi.rmo;

import com.maerdngaminng.ts3.queryapi.NotEditableObjectException;
import com.maerdngaminng.ts3.queryapi.Ts3ApiException;
import com.maerdngaminng.ts3.queryapi.Ts3ApiRMObjectParameter;
import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(exclude={"ts3QueryApi"})
@ToString(exclude={"ts3QueryApi"})
public class ChannelPermission implements RMObject {

	@Setter
	private Ts3QueryApi ts3QueryApi;
	
	@Ts3ApiRMObjectParameter("permid")
	private int permissionId;
	
	@Ts3ApiRMObjectParameter("permvalue")
	private String permissionValue;
	
	@Ts3ApiRMObjectParameter("permnegated")
	private boolean negate;
	
	@Ts3ApiRMObjectParameter("permskip")
	private boolean skip;

	@Override
	public void saveChanges() throws Ts3ApiException {
		throw new NotEditableObjectException();
	}
}
