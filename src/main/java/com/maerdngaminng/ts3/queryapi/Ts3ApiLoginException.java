package com.maerdngaminng.ts3.queryapi;

public class Ts3ApiLoginException extends Ts3ApiException {

	private static final long serialVersionUID = -2799871108872880506L;

	public Ts3ApiLoginException() {
		super();
	}
	
	public Ts3ApiLoginException(String s) {
		super(s);
	}
	
	public Ts3ApiLoginException(Throwable t) {
		super(t);
	}
	
	public Ts3ApiLoginException(String s, Throwable t) {
		super(s, t);
	}
}