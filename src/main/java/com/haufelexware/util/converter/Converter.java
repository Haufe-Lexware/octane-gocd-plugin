package com.haufelexware.util.converter;

/**
 * This interface defines what a convert should do.
 */
public interface Converter<Source, Target> {

	Target convert(Source source);
}
