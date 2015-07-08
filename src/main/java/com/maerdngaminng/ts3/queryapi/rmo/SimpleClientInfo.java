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
public class SimpleClientInfo implements RMObject {

	@Setter
	private Ts3QueryApi ts3QueryApi;
	
	@Ts3ApiRMObjectParameter("clid")
	private int clid;
	
	@Ts3ApiRMObjectParameter("cid")
	private int clientId;
	
	@Ts3ApiRMObjectParameter("client_database_id")
	private int clientDatabaseId;
	
	@Ts3ApiRMObjectParameter("client_nickname")
	private String clientName;
	
	@Ts3ApiRMObjectParameter("client_type")
	private ClientType clientType;

	@Override
	public void saveChanges() throws Ts3ApiException {
		throw new NotEditableObjectException();
	}

}
