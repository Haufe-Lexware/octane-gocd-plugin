package com.haufelexware.util.checker;

import java.util.Collection;

/**
 * This a generic checker to convert DAOs to TOs. It will be used by the REST services to convert the response objects.
 */
public class ListChecker<Instance> implements Checker<Collection<Instance>> {

	private final Checker<Instance> checker;

	/**
	 * Use this constructor to use a checker of your choice to check all items.
	 */
	public ListChecker(Checker<Instance> checker) {
		this.checker = checker;
	}

	/**
	 * This method will check all items in the collection whether they match the given checker.
	 *
	 * @return true if all item in the list did comply with the given checker.
	 */
	@Override
	public boolean check(Collection<Instance> items) {
		if (items == null) {
			return true;
		}
		for (Instance item : items) {
			if (!checker.check(item)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This is a convenience method to allow a more compact code when using this class.
	 */
	public static <Instance> boolean check(Collection<Instance> items, Checker<Instance> checker) {
		return new ListChecker<>(checker).check(items);
	}
}
