package com.afs.jigsaw.fda.food.api;

import com.google.common.base.Preconditions;

/**
 * Represents one of the 50 U.S. and Washington DC.
 */
public enum State {
    ALABAMA("AL", "Alabama"),
    ALASKA("AK", "Alaska"),
    ARIZONA("AZ", "Arizona"),
    ARKANSAS("AR", "Arkansas"),
    CALIFORNIA("CA", "California"),
    COLORADO("CO", "Colorado"),
    CONNECTICUT("CT", "Connecticut"),
    DELAWARE("DE", "Delaware"),
    DISTRICT_OF_COLUMBIA("DC", "District of Columbia", "Washington DC", "Washington D.C."),
    FLORIDA("FL", "Florida"),
    GEORGIA("GA", "Georgia"),
    HAWAII("HI", false, "Hawaii"), // 'hi' should not match to Hawaii
    IDAHO("ID", "Idaho"),
    ILLINOIS("IL", "Illinois"),
    INDIANA("IN", false, "Indiana"), // 'in' should not match to Indiana
    IOWA("IA", "Iowa"),
    KANSAS("KS", "Kansas"),
    KENTUCKY("KY", "Kentucky"),
    LOUISIANA("LA", "Louisiana"),
    MAINE("ME", "Maine"),
    MARYLAND("MD", "Maryland"),
    MASSACHUSETTS("MA", "Massachusetts"),
    MICHIGAN("MI", "Michigan"),
    MINNESOTA("MN", "Minnesota"),
    MISSISSIPPI("MS", "Mississippi"),
    MISSOURI("MO", "Missouri"),
    MONTANA("MT", "Montana"),
    NEBRASKA("NE", "Nebraska"),
    NEVADA("NV", "Nevada"),
    NEW_HAMPSHIRE("NH", "New Hampshire"),
    NEW_JERSEY("NJ", "New Jersey"),
    NEW_MEXICO("NM", "New Mexico"),
    NEW_YORK("NY", "New York"),
    NORTH_CAROLINA("NC", "North Carolina"),
    NORTH_DAKOTA("ND", "North Dakota"),
    OHIO("OH", "Ohio"),
    OKLAHOMA("OK", false, "Oklahoma"), // 'ok' should not match to Oklahoma
    OREGON("OR", false, "Oregon"), // 'or' should not match to Oregan
    PENNSYLVANIA("PA", "Pennsylvania"),
    RHODE_ISLAND("RI", "Rhode Island"),
    SOUTH_CAROLINA("SC", "South Carolina"),
    SOUTH_DAKOTA("SD", "South Dakota"),
    TENNESSEE("TN", "Tennessee"),
    TEXAS("TX", "Texas"),
    UTAH("UT", "Utah"),
    VERMONT("VT", "Vermont"),
    VIRGINIA("VA", "Virginia"),
    WASHINGTON("WA", "Washington"),
    WEST_VIRGINIA("WV", "West Virginia"),
    WISCONSIN("WI", "Wisconsin"),
    WYOMING("WY", "Wyoming");

    private String[] names;
    private String abbreviation;
    private boolean allowsCaseInsensitiveAbbreviationMatches;

    /**
     * Constructs a new state. This state will be matched by abbreviation
     * regardless of string case.
     *
     * @param abbreviation
     *            the state's abbreviation. Must not be null.
     * @param names
     *            A list of other names that may be considered as a much for a
     *            state. Must not be null. Must contain at least 1 valid name.
     */
    private State(final String abbreviation, final String... names) {
        this(abbreviation, true, names);
    }

    /**
     * Constructs a new state.
     *
     * @param abbreviation
     *            the state's abbreviation. Must not be null.
     * @param allowsCaseInsensitiveAbbreviationMatches
     *            True if the abbreviation for this State is case insensitive;
     *            False otherwise.
     * @param names
     *            A list of other names that may be considered as a much for a
     *            state. Must not be null. Must contain at least 1 valid name.
     */
    private State(final String abbreviation, final boolean allowsCaseInsensitiveAbbreviationMatches,
            final String... names) {
        Preconditions.checkNotNull(abbreviation);
        Preconditions.checkNotNull(names);
        Preconditions.checkArgument(names.length > 0, "You must provide at least one name");
        this.abbreviation = abbreviation;
        this.allowsCaseInsensitiveAbbreviationMatches = allowsCaseInsensitiveAbbreviationMatches;
        this.names = names;
    }

    /**
     * Returns the state's 2 character abbreviation.
     *
     * @return The state's abbreviation. Never null.
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * Gets the state's FIRST full name in a display-friendly format.<br />
     * <br />
     *
     * If you want to see all of the state's names, use
     * {@link #getAllFullNames()}.
     *
     * @return The state's display name. Never null.
     */
    public String getFullName() {
        return names[0];
    }

    /**
     * Gets all of the state's names in a display-friendly format.
     *
     * @return All of the state's display name. Never null. Will always contain
     *         at least 1 name.
     */
    public String[] getAllFullNames() {
        return names;
    }

    /**
     * Attempts to convert the given string to a {@link State}. If no State
     * matches the given string, null will be returned. The given string is case
     * insensitive when matching it to a state.<br />
     * <br />
     *
     * The given string can be in 1 of 3 formats:<br />
     * 1) The string serialized version of the enum value. (ex: 'NEW_YORK')<br />
     * 2) The state's abbreviation. (ex: 'NY')<br />
     * 3) The state's full name. (ex: 'New York')
     *
     * @param stateString
     *            The string representation of the state to get. Must not be
     *            null.
     * @return A {@link State} item that matches the given string or null if no
     *         {@link State} exists for the given string.
     */
    public static State fromString(final String stateString) {
        Preconditions.checkNotNull(stateString);

        /*
         * see if the string is enum string serialized value..valueOf() throws
         * an exception if no State matched
         */
        try {
            return valueOf(stateString.toUpperCase());
        } catch (final IllegalArgumentException e) {
            // ignore, continue on looking for the requested state
        }

        // check the abbreviation/name for a match
        for (final State s : values()) {
            if (s.allowsCaseInsensitiveAbbreviationMatches && s.abbreviation.equalsIgnoreCase(stateString)) {
                return s;
            } else if (s.abbreviation.equals(stateString)) {
                return s;
            } else {
                for (final String fullName : s.names) {
                    if (fullName.equalsIgnoreCase(stateString)) {
                        return s;
                    }
                }
            }
        }

        // no match found
        return null;
    }

}