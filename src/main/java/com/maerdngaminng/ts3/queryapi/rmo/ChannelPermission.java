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
	
	public ChannelPermission(int permissionId, String permissionValue, boolean negate, boolean skip) {
		super();
		this.permissionId = permissionId;
		this.permissionValue = permissionValue;
		this.negate = negate;
		this.skip = skip;
	}

	@Setter
	private Ts3QueryApi ts3QueryApi;
	
	@Ts3ApiRMObjectParameter(editable=true,value="permid")
	private int permissionId;
	
	@Ts3ApiRMObjectParameter(editable=true,value="permvalue")
	private String permissionValue;
	
	@Ts3ApiRMObjectParameter(editable=true,value="permnegated")
	private boolean negate;
	
	@Ts3ApiRMObjectParameter(editable=true,value="permskip")
	private boolean skip;

	@Override
	public void saveChanges() throws Ts3ApiException {
		throw new NotEditableObjectException();
	}
}
