package com.maerdngaminng.ts3.queryapi;

import java.util.List;

import lombok.Data;

@Data
public class QueryMessage {

	private final String mainMessage;
	private final List<String> dataMessages;
}
