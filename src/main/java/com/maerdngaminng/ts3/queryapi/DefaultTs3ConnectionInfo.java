package com.maerdngaminng.ts3.queryapi;

import lombok.Data;

@Data
public class DefaultTs3ConnectionInfo implements Ts3ConnectionInfo {

	private final String hostname;
	private final int port;
	private final String username;
	private final String password;
	private final int ts3ServerId;
}
