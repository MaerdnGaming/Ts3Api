package com.maerdngaminng.ts3.queryapi.rmo.mapper;

import java.math.BigInteger;

import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;

public class BigIntegerDataMapper implements DataMapper<BigInteger> {

	@Override
	public BigInteger mapData(String data, Ts3QueryApi api) {
		return new BigInteger(data);
	}

}
