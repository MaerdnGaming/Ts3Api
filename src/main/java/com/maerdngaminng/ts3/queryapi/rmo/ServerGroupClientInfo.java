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
public class ServerGroupClientInfo implements RMObject {

	@Setter
	private Ts3QueryApi ts3QueryApi;
	
	@Ts3ApiRMObjectParameter("cldbid")
	private int clientDatabaseId;
	
	@Ts3ApiRMObjectParameter("sgid")
	private int serverGroupId;
	
	@Ts3ApiRMObjectParameter("name")
	private String serverGroupName;

	@Override
	public void saveChanges() {
		throw new NotEditableObjectException();
	}
}
