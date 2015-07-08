package com.maerdngaminng.ts3.queryapi;

public class Ts3ApiConnectException extends Ts3ApiException {

	private static final long serialVersionUID = -2799871108872880506L;

	public Ts3ApiConnectException() {
		super();
	}
	
	public Ts3ApiConnectException(String s) {
		super(s);
	}
	
	public Ts3ApiConnectException(Throwable t) {
		super(t);
	}
	
	public Ts3ApiConnectException(String s, Throwable t) {
		super(s, t);
	}
}
