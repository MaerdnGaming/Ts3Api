package com.maerdngaminng.ts3.queryapi.rmo.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.maerdngaminng.ts3.queryapi.Ts3ApiException;
import com.maerdngaminng.ts3.queryapi.Ts3ApiRMObjectParameter;
import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;
import com.maerdngaminng.ts3.queryapi.rmo.ClientType;
import com.maerdngaminng.ts3.queryapi.rmo.Codec;
import com.maerdngaminng.ts3.queryapi.rmo.CommaSeperatedIntegerList;
import com.maerdngaminng.ts3.queryapi.rmo.RMObject;
import com.maerdngaminng.ts3.queryapi.rmo.VirtualServerState;

public final class DataMappingManager {

	private DataMappingManager() {
	}

	private final static Map<Class<?>, DataMapper<?>> dataMappers = new HashMap<>();

	static {
		dataMappers.put(int.class, new IntDataMapper());
		dataMappers.put(Integer.class, new IntDataMapper());
		dataMappers.put(boolean.class, new BooleanDataMapper());
		dataMappers.put(Boolean.class, new BooleanDataMapper());
		dataMappers.put(long.class, new LongDataMapper());
		dataMappers.put(Long.class, new LongDataMapper());
		dataMappers.put(BigInteger.class, new BigIntegerDataMapper());
		dataMappers.put(String.class, new StringDataMapper());
		dataMappers.put(VirtualServerState.class, new VirtualServerStateDataMapper());
		dataMappers.put(CommaSeperatedIntegerList.class, new CommaSeperatedIntegerListDataMapper());
		dataMappers.put(ClientType.class, new ClientTypeDataMapper());
		dataMappers.put(Codec.class, new CodecDataMapper());
	}

	public static <Y> Y mapData(Class<Y> type, String data, Ts3QueryApi api) throws Ts3ApiException {
		@SuppressWarnings("unchecked")
		DataMapper<Y> mapper = (DataMapper<Y>) dataMappers.get(type);
		if (mapper == null)
			throw new Ts3ApiException("No data mapper for " + type.getSimpleName());
		return data == null ? null : mapper.mapData(data, api);
	}

	public static <Y extends RMObject> Y mapToObject(Class<Y> clazz, Map<String, String> data, Ts3QueryApi api) throws InstantiationException, IllegalAccessException,
			Ts3ApiException, IllegalArgumentException, InvocationTargetException, SecurityException {
		Y resultObj = clazz.newInstance();
		resultObj.setTs3QueryApi(api);
		for (Field f : clazz.getDeclaredFields()) {
			f.setAccessible(true);
			if (f.isAnnotationPresent(Ts3ApiRMObjectParameter.class)) {
				Ts3ApiRMObjectParameter ts3ApiRMOParameter = f.getAnnotation(Ts3ApiRMObjectParameter.class);
				String iData = data.get(ts3ApiRMOParameter.value());
				Object o = iData == null ? null : mapData(f.getType(), iData, api);
				if(o == null && f.getType().isPrimitive()) {
					if(!f.getType().isArray()) {
						if(f.getType().equals(int.class) || f.getType().equals(byte.class) || f.getType().equals(short.class) || f.getType().equals(long.class))
							o = 0;
						else if(f.getType().equals(double.class) || f.getType().equals(float.class))
							o = 0.0;
						else
							o = false;
					}
				}
				f.set(resultObj, o);
			}
		}
		return resultObj;
	}

	public static <Y extends RMObject> Y mapToObject(Class<Y> clazz, String data, Ts3QueryApi api) throws InstantiationException, IllegalAccessException, Ts3ApiException,
			IllegalArgumentException, InvocationTargetException, SecurityException {
		String[] split1 = data.split(" ");
		Map<String, String> dataMap = new HashMap<>();
		for (String paramValue : split1) {
			String[] split2 = paramValue.split("=", 2);
			if (split2.length == 2)
				dataMap.put(split2[0], split2[1]);
		}
		return mapToObject(clazz, dataMap, api);
	}

	public static <Y extends RMObject> List<Y> mapListToObjectList(Class<Y> clazz, List<String> dataList, Ts3QueryApi api) throws InstantiationException, IllegalAccessException,
			Ts3ApiException, IllegalArgumentException, InvocationTargetException, SecurityException {
		List<Y> resultData = new LinkedList<>();
		for (String data : dataList) {
			resultData.add(mapToObject(clazz, data, api));
		}
		return resultData;
	}

	public static <Y extends RMObject> List<Y> mapListToObjectList(Class<Y> clazz, String dataList, Ts3QueryApi api) throws InstantiationException, IllegalAccessException,
			Ts3ApiException, IllegalArgumentException, InvocationTargetException, SecurityException {
		List<String> l = Arrays.asList(dataList.split("\\|"));
		return mapListToObjectList(clazz, l, api);
	}
}
