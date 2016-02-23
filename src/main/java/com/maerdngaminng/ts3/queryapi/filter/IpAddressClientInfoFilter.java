package com.maerdngaminng.ts3.queryapi.filter;

import lombok.Data;

import com.maerdngaminng.ts3.queryapi.rmo.ClientInfo;

@Data
public class IpAddressClientInfoFilter implements ClientInfoFilter {

	private final String ipAddress;
	private final boolean regex;
	
	public IpAddressClientInfoFilter(String ipAddress) {
		this(ipAddress, false);
	}
	
	public IpAddressClientInfoFilter(String ipAddress, boolean regex) {
		this.ipAddress = ipAddress.toLowerCase();
		this.regex = regex;
	}

	@Override
	public boolean accept(ClientInfo client) {
		if(client.getClientIp() == null)
			return false;
		return this.regex ? client.getClientIp().toLowerCase().matches(getIpAddress()) : client.getClientIp().toLowerCase().equals(getIpAddress());
	}
}
