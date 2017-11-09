package com.haufelexware.report.nunit;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * In NUnit xml file boolean values are written as "True" or "False".
 * This adapter helps converting them.
 */
public class BooleanAdapter extends XmlAdapter<String,Boolean> {

	@Override
	public Boolean unmarshal(String value) throws Exception {
		if ("True".equals(value)) {
			return Boolean.TRUE;
		} else if ("False".equals(value)) {
			return Boolean.FALSE;
		}
		return null;
	}

	@Override
	public String marshal(Boolean value) throws Exception {
		if (Boolean.TRUE.equals(value)) {
			return "True";
		} else if (Boolean.FALSE.equals(value)) {
			return "False";
		}
		return null;
	}
}
