package com.afs.jigsaw.fda.food.api;

/**
 * Represents the set of classifications for a recall.  The meaning of these
 * is documented on the FDA web site here:<br /><br />
 * https://open.fda.gov/food/enforcement/reference/
 * 
 * @author peterdailey
 *
 */
public enum Classification {

	LOW("Class III"),
	MEDIUM("Class II"),
	HIGH("Class I");
	
	private String fdaValue;

	private Classification(String fdaValue) {
		this.fdaValue = fdaValue;
	}
	
	public String getFdaValue() {
		return fdaValue;
	}
	
	/**
	 * Finds the enum value based on the string provided in the FDA data set
	 * as described in their API documentation.
	 * 
	 * This will return null if there is no matching value found.
	 * 
	 * @param fda
	 */
	public static Classification getByFdaValue(String fda) {
		if (fda == null) {
			return null;
		}
		for (Classification cl : Classification.values()) {
			if (fda.equals(cl.getFdaValue())) {
				return cl;
			}
		}
		return null;
	}
}
