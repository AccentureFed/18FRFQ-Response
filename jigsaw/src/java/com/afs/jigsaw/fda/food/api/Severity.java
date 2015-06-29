package com.afs.jigsaw.fda.food.api;

/**
 * Represents the set of severity for a recall. The meaning of these is
 * documented on the FDA web site here:<br />
 * <br />
 * https://open.fda.gov/food/enforcement/reference/
 *
 */
public enum Severity {

    LOW("Class III"), MEDIUM("Class II"), HIGH("Class I");

    private String fdaValue;

    private Severity(final String fdaValue) {
        this.fdaValue = fdaValue;
    }

    public String getFdaValue() {
        return fdaValue;
    }

    /**
     * Finds the enum value based on the string provided in the FDA data set as
     * described in their API documentation.
     *
     * This will return null if there is no matching value found.
     *
     * @param fda
     *            The fda classification to get a severity for.
     */
    public static Severity getByFdaValue(final String fda) {
        if (fda == null) {
            return null;
        }
        for (final Severity cl : Severity.values()) {
            if (fda.equals(cl.getFdaValue())) {
                return cl;
            }
        }
        return null;
    }
}
