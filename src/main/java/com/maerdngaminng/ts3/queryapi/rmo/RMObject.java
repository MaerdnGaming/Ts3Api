package com.maerdngaminng.ts3.queryapi.rmo;

import com.maerdngaminng.ts3.queryapi.Ts3ApiException;
import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;

public interface RMObject {

	public Ts3QueryApi getTs3QueryApi();
	public void setTs3QueryApi(Ts3QueryApi api);
	
	public void saveChanges() throws Ts3ApiException;
}
