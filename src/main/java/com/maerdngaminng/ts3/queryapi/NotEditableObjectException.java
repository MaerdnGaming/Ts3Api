package com.maerdngaminng.ts3.queryapi;

public class NotEditableObjectException extends Ts3ApiRuntimeException {

	private static final long serialVersionUID = 9026225405176802332L;

	public NotEditableObjectException() {
		super();
	}
	
	public NotEditableObjectException(String s) {
		super(s);
	}
}
