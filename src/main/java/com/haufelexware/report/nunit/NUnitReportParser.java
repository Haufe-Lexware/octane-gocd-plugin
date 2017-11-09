package com.haufelexware.report.nunit;

import com.haufelexware.report.nunit.dom.NUnitTestResults;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * This parser will parse a given stream into a NUnit-DOM.
 */
public class NUnitReportParser {

	private final Unmarshaller unmarshaller;

	public NUnitReportParser() {
		try {
			unmarshaller = JAXBContext.newInstance(NUnitTestResults.class).createUnmarshaller();
		} catch (JAXBException e) {
			throw new IllegalArgumentException("Could not initialize Unmarshaller", e);
		}
	}

	public NUnitTestResults parseFrom(InputStream stream) throws JAXBException {
		return (NUnitTestResults)unmarshaller.unmarshal(stream);
	}
}
