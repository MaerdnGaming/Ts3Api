package com.maerdngaminng.ts3.queryapi.rmo.mapper;

import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;

public class LongDataMapper implements DataMapper<Long> {

	@Override
	public Long mapData(String data, Ts3QueryApi api) {
		return new Long(data);
	}

}
