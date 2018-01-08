package com.haufelexware.report.nunit.v30;

import com.haufelexware.report.nunit.v30.dom.NUnitTestRun;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * This parser will parse a given stream into a NUnit-DOM.
 */
public class NUnit30ReportParser {

	private final Unmarshaller unmarshaller;

	public NUnit30ReportParser() {
		try {
			unmarshaller = JAXBContext.newInstance(NUnitTestRun.class).createUnmarshaller();
		} catch (JAXBException e) {
			throw new IllegalArgumentException("Could not initialize Unmarshaller", e);
		}
	}

	public NUnitTestRun parseFrom(InputStream stream) throws JAXBException {
		return (NUnitTestRun)unmarshaller.unmarshal(stream);
	}
}
