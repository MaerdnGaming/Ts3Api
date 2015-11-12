package com.maerdngaminng.ts3.queryapi;

public class Ts3ApiException extends Exception {

	private static final long serialVersionUID = 1L;

	public Ts3ApiException() {
		super();
	}
	
	public Ts3ApiException(String s) {
		super(s);
	}
	
	public Ts3ApiException(Throwable t) {
		super(t);
	}
	
	public Ts3ApiException(String s, Throwable t) {
		super(s, t);
	}
}
