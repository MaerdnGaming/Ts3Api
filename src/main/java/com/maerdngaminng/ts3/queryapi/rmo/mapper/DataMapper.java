package com.maerdngaminng.ts3.queryapi.rmo.mapper;

import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;

public interface DataMapper<X> {

	public X mapData(String data, Ts3QueryApi api);
}
