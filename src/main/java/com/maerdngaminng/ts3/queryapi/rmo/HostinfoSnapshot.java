package com.maerdngaminng.ts3.queryapi.rmo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.maerdngaminng.ts3.queryapi.Ts3ApiException;
import com.maerdngaminng.ts3.queryapi.Ts3QueryApi;

@Getter
@EqualsAndHashCode(exclude={"ts3QueryApi"})
@ToString(exclude={"ts3QueryApi"})
public class HostinfoSnapshot implements RMObject {

	@Setter
	private Ts3QueryApi ts3QueryApi;

	@Override
	public void saveChanges() throws Ts3ApiException {
		// TODO Auto-generated method stub
		
	}
}
