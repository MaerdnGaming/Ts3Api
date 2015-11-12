package com.maerdngaminng.ts3.queryapi.rmo.mapper;

import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;
import com.maerdngaminng.ts3.queryapi.rmo.CommaSeperatedIntegerList;

public class CommaSeperatedIntegerListDataMapper implements DataMapper<CommaSeperatedIntegerList> {

	@Override
	public CommaSeperatedIntegerList mapData(String data, Ts3QueryApi api) {
		CommaSeperatedIntegerList list = new CommaSeperatedIntegerList();
		String[] splitCache = data.replaceAll("[^0-9,]+", "").split(",");
		for(String x : splitCache)
			list.add(new Integer(x));
		return list;
	}

}
