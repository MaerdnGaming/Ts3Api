package com.maerdngaminng.ts3.queryapi.filter;

import lombok.Data;

import com.maerdngaminng.ts3.queryapi.rmo.ClientInfo;

@Data
public class IpAddressClientQueryFilter implements ClientQueryFilter {

	private final String ipAddress;
	private final boolean regex;
	
	public IpAddressClientQueryFilter(String ipAddress) {
		this(ipAddress, false);
	}
	
	public IpAddressClientQueryFilter(String ipAddress, boolean regex) {
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
