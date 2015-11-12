package com.maerdngaminng.ts3.queryapi.rmo.mapper;

import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;
import com.maerdngaminng.ts3.queryapi.rmo.VirtualServerState;

public class VirtualServerStateDataMapper implements DataMapper<VirtualServerState> {

	@Override
	public VirtualServerState mapData(String data, Ts3QueryApi api) {
		return data.equalsIgnoreCase("online") ? VirtualServerState.ONLINE : data.equalsIgnoreCase("offline") ? VirtualServerState.OFFLINE : null;
	}

}
