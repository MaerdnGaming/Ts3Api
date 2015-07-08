package com.maerdngaminng.ts3.queryapi.rmo.mapper;

import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;

public class IntDataMapper implements DataMapper<Integer> {

	@Override
	public Integer mapData(String data, Ts3QueryApi api) {
		return new Integer(data);
	}

}
