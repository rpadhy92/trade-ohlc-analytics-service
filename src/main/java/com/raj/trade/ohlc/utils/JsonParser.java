package com.raj.trade.ohlc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static <T> T fromJson(String jsonString, Class<T> clazz) throws JsonProcessingException {
		return objectMapper.readValue(jsonString, clazz);
	}
}
