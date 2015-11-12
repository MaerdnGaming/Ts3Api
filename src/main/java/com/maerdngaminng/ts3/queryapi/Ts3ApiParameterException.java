package com.maerdngaminng.ts3.queryapi;

public class Ts3ApiParameterException extends Ts3ApiException {

	private static final long serialVersionUID = -2799871108872880506L;

	public Ts3ApiParameterException() {
		super();
	}
	
	public Ts3ApiParameterException(String s) {
		super(s);
	}
	
	public Ts3ApiParameterException(Throwable t) {
		super(t);
	}
	
	public Ts3ApiParameterException(String s, Throwable t) {
		super(s, t);
	}
}