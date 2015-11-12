package com.maerdngaminng.ts3.queryapi.rmo.mapper;

import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;
import com.maerdngaminng.ts3.queryapi.rmo.ClientType;

public class ClientTypeDataMapper implements DataMapper<ClientType> {

	@Override
	public ClientType mapData(String data, Ts3QueryApi api) {
		return ClientType.getClientTypeById(new Integer(data));
	}

}
