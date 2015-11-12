package com.maerdngaminng.ts3.queryapi.rmo.mapper;

import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;

public class BooleanDataMapper implements DataMapper<Boolean> {

	@Override
	public Boolean mapData(String data, Ts3QueryApi api) {
		return data.equals("1") || data.equalsIgnoreCase("true");
	}

}
