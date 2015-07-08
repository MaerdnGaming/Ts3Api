package com.maerdngaminng.ts3.queryapi.rmo;

import com.maerdngaminng.ts3.queryapi.NotEditableObjectException;
import com.maerdngaminng.ts3.queryapi.Ts3ApiRMObjectParameter;
import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(exclude={"ts3QueryApi"})
@ToString(exclude={"ts3QueryApi"})
public class ServerVersionInfo implements RMObject {

	@Setter
	private Ts3QueryApi ts3QueryApi;
	
	@Ts3ApiRMObjectParameter("version")
	private String version;
	
	@Ts3ApiRMObjectParameter("build")
	private String build;
	
	@Ts3ApiRMObjectParameter("platform")
	private String platform;

	@Override
	public void saveChanges() {
		throw new NotEditableObjectException();
	}
}
