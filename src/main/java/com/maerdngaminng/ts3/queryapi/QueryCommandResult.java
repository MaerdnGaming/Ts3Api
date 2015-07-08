package com.maerdngaminng.ts3.queryapi;

import java.util.List;

import lombok.Data;

@Data
class QueryCommandResult {

	private final int id;
	private final String msg;
	private final int failedPermid;
	private final List<String> resultList;
	
	public String getFirstResultLine() {
		if(this.resultList.size() < 1) return null;
		for(String x : this.resultList) {
			if(!x.equals(""))
				return x;
		}
		return null;
	}
}
