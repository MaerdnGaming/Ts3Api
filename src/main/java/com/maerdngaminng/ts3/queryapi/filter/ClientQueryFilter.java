package com.maerdngaminng.ts3.queryapi.filter;

import com.maerdngaminng.ts3.queryapi.rmo.ClientInfo;

public interface ClientQueryFilter {

	/**
	 * Checks if the given {@link ClientInfo} meets the requirements
	 * 
	 * @param client the {@link ClientInfo} to check
	 * @return true if the client meets
	 */
	public boolean accept(ClientInfo client);
}
