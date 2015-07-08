
package com.maerdngaminng.ts3.queryapi.rmo;

import lombok.Getter;

public enum ClientType {

	NORMAL_CLIENT(0),
	QUERY_CLIENT(1);
	
	@Getter
	private int id;
	
	private ClientType(int id) {
		this.id = id;
	}
	
	public static ClientType getClientTypeById(int id) {
		switch (id) {
			case 0: return NORMAL_CLIENT;
			case 1: return QUERY_CLIENT;
		}
		return null;
	}
}
