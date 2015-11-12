package com.maerdngaminng.ts3.queryapi.rmo.mapper;

import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;

public class StringDataMapper implements DataMapper<String> {

	@Override
	public String mapData(String data, Ts3QueryApi api) {
		return api.unescapeString(data);
	}

}
