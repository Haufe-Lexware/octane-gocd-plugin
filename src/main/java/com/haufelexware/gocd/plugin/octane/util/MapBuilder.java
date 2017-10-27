package com.haufelexware.gocd.plugin.octane.util;

import java.util.Map;

/**
 * This map builder allows the fluent creation of maps.
 */
public class MapBuilder<MapType extends Map<Key,Value>,Key,Value> {

	private final MapType map;

	public MapBuilder(MapType map) {
		this.map = map;
	}

	public MapBuilder<MapType,Key,Value> put(Key key, Value value) {
		map.put(key, value);
		return this;
	}

	public MapType build() {
		return map;
	}
}
