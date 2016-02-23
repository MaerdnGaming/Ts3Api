package com.maerdngaminng.ts3.queryapi.filter;

import lombok.Data;

import com.maerdngaminng.ts3.queryapi.rmo.ChannelInfo;

@Data
public class ChannelEmptyTimeChannelInfoFilter implements ChannelInfoFilter {

	private final int emptySeconds;
	
	@Override
	public boolean accept(ChannelInfo channel) {
		return channel.getSecondsEmpty() >= this.emptySeconds;
	}

}
