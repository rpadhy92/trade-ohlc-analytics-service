package com.raj.trade.ohlc.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Class JsonParser.
 * Utility class having implementations for serialization/deserialization of java bean to json or viceversa
 */
public class JsonParser {

	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * fromJson - convert json string to java object 
	 *
	 * @param <T> the generic type
	 * @param jsonString the json string
	 * @param clazz the clazz
	 * @return the t
	 * @throws JsonProcessingException
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz) throws JsonProcessingException {
		return objectMapper.readValue(jsonString, clazz);
	}
	
	/**
	 * toJson - convert object to json string.
	 *
	 * @param obj the obj
	 * @return the string
	 * @throws JsonProcessingException
	 */
	public static String toJson(Object obj) throws JsonProcessingException {
		return objectMapper.writeValueAsString(obj);
	}
}
