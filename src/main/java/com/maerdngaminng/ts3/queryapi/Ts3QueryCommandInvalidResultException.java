package com.maerdngaminng.ts3.queryapi;


public class Ts3QueryCommandInvalidResultException extends Ts3ApiException {

	private static final long serialVersionUID = -2799871108872880506L;
	
	public Ts3QueryCommandInvalidResultException(QueryCommandResult qcr) {
		this(qcr.getId(), qcr.getMsg(), qcr.getFailedPermid());
	}
	
	public Ts3QueryCommandInvalidResultException(int id, String msg, int permId) {
		super("ResultId: "+id+" - Message: "+msg+""+(permId != 0 ? "PermId: "+permId : ""));
	}
}