package com.maerdngaminng.ts3.queryapi;

public abstract class Ts3QueryApiFactory {

	private final static Ts3QueryApiFactory DEFAULT = new DefaultTs3QueryApiFactory();
	
	public static Ts3QueryApiFactory getDefaultFactory() {
		return DEFAULT;
	}
	
	public abstract Ts3QueryApi createTs3QueryApi(Ts3ConnectionInfo connectionInfo);

	public Ts3QueryApi createTs3QueryApi(String hostname, int port) {
		return this.createTs3QueryApi(hostname, port, 0);
	}

	public Ts3QueryApi createTs3QueryApi(String hostname, int port, String username, String password) {
		return this.createTs3QueryApi(hostname, port, username, password, 0);
	}

	public Ts3QueryApi createTs3QueryApi(String hostname, int port, String username, String password, int serverId) {
		return this.createTs3QueryApi(new DefaultTs3ConnectionInfo(hostname, port, username, password, serverId));
	}

	public Ts3QueryApi createTs3QueryApi(String hostname, int port, int serverId) {
		return this.createTs3QueryApi(hostname, port, null, null, 0);
	}
}
