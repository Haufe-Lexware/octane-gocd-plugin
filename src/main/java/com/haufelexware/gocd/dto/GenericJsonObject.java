package com.haufelexware.gocd.dto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This generic JSON object can be used when no specific DTO class does exist so far.
 */
public class GenericJsonObject extends HashMap<String,Object> {

	public Object getValue(String... path) {
		return getValueFromMap(this, path);
	}

	public static Object getValueFromMap(Map<String,Object> map, String... path) {
		if (map == null || path == null || path.length < 1) {
			return null;
		}
		Object value = map.get(path[0]);
		if (path.length == 1) {
			return value;
		} else if (value instanceof Map) {
			return getValueFromMap((Map)value, Arrays.copyOfRange(path, 1, path.length));
		}
		return null;
	}
}
