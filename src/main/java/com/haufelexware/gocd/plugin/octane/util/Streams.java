package com.haufelexware.gocd.plugin.octane.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class helps with reading a {@link InputStream} into a {@link String}.
 */
public class Streams {

	public static String readAsString(InputStream stream) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"))) {
			String line;
			boolean isFirstLine = true;
			while ((line = reader.readLine()) != null) {
				if (!isFirstLine) {
					stringBuilder.append('\n');
				} else {
					isFirstLine = false;
				}
				stringBuilder.append(line);
			}
		}
		return stringBuilder.toString();
	}
}
