package com.maerdngaminng.ts3.queryapi;


final class DefaultTs3QueryApiFactory extends Ts3QueryApiFactory {

	@Override
	public Ts3QueryApi createTs3QueryApi(Ts3ConnectionInfo connectionInfo) {
		return new Ts3QueryApiImpl(connectionInfo);
	}

}
