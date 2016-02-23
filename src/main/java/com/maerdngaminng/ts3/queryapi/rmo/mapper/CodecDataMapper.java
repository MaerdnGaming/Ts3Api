package com.maerdngaminng.ts3.queryapi.rmo.mapper;

import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;
import com.maerdngaminng.ts3.queryapi.rmo.Codec;

public class CodecDataMapper implements DataMapper<Codec> {

	@Override
	public Codec mapData(String data, Ts3QueryApi api) {
		return Codec.getCodecById(new Integer(data));
	}

}
