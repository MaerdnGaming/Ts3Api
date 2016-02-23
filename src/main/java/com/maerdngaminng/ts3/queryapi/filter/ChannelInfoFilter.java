package com.maerdngaminng.ts3.queryapi.filter;

import com.maerdngaminng.ts3.queryapi.rmo.ChannelInfo;

public interface ChannelInfoFilter {

	/**
	 * Checks if the given {@link ChannelInfo} meets the requirements
	 * 
	 * @param channel the {@link ChannelInfo} to check
	 * @return true if the client meets
	 */
	public boolean accept(ChannelInfo channel);
}
