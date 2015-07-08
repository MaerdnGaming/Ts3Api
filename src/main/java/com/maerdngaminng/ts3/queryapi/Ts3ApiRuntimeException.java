package com.maerdngaminng.ts3.queryapi;

public class Ts3ApiRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public Ts3ApiRuntimeException() {
		super();
	}
	
	public Ts3ApiRuntimeException(String s) {
		super(s);
	}
	
	public Ts3ApiRuntimeException(Throwable t) {
		super(t);
	}
	
	public Ts3ApiRuntimeException(String s, Throwable t) {
		super(s, t);
	}
}
