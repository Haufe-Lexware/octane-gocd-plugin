package com.haufelexware.util.checker;

/**
 * This interface define the method check which should return true, when the check on the given instance did succeed.
 */
public interface Checker<Instance> {

	boolean check(Instance instance);
}
