package com.afs.jigsaw.fda.food.api;

import com.google.common.base.Preconditions;

/**
 * Represents one of the 50 U.S. and Washington DC.
 */
public enum State {
    ALABAMA("Alabama", "AL"),
    ALASKA("Alaska", "AK"),
    ARIZONA("Arizona", "AZ"),
    ARKANSAS("Arkansas", "AR"),
    CALIFORNIA("California", "CA"),
    COLORADO("Colorado", "CO"),
    CONNECTICUT("Connecticut", "CT"),
    DELAWARE("Delaware", "DE"),
    DISTRICT_OF_COLUMBIA("District of Columbia", "DC"),
    FLORIDA("Florida", "FL"),
    GEORGIA("Georgia", "GA"),
    HAWAII("Hawaii", "HI"),
    IDAHO("Idaho", "ID"),
    ILLINOIS("Illinois", "IL"),
    INDIANA("Indiana", "IN"),
    IOWA("Iowa", "IA"),
    KANSAS("Kansas", "KS"),
    KENTUCKY("Kentucky", "KY"),
    LOUISIANA("Louisiana", "LA"),
    MAINE("Maine", "ME"),
    MARYLAND("Maryland", "MD"),
    MASSACHUSETTS("Massachusetts", "MA"),
    MICHIGAN("Michigan", "MI"),
    MINNESOTA("Minnesota", "MN"),
    MISSISSIPPI("Mississippi", "MS"),
    MISSOURI("Missouri", "MO"),
    MONTANA("Montana", "MT"),
    NEBRASKA("Nebraska", "NE"),
    NEVADA("Nevada", "NV"),
    NEW_HAMPSHIRE("New Hampshire", "NH"),
    NEW_JERSEY("New Jersey", "NJ"),
    NEW_MEXICO("New Mexico", "NM"),
    NEW_YORK("New York", "NY"),
    NORTH_CAROLINA("North Carolina", "NC"),
    NORTH_DAKOTA("North Dakota", "ND"),
    OHIO("Ohio", "OH"),
    OKLAHOMA("Oklahoma", "OK"),
    OREGON("Oregon", "OR"),
    PENNSYLVANIA("Pennsylvania", "PA"),
    RHODE_ISLAND("Rhode Island", "RI"),
    SOUTH_CAROLINA("South Carolina", "SC"),
    SOUTH_DAKOTA("South Dakota", "SD"),
    TENNESSEE("Tennessee", "TN"),
    TEXAS("Texas", "TX"),
    UTAH("Utah", "UT"),
    VERMONT("Vermont", "VT"),
    VIRGINIA("Virginia", "VA"),
    WASHINGTON("Washington", "WA"),
    WEST_VIRGINIA("West Virginia", "WV"),
    WISCONSIN("Wisconsin", "WI"),
    WYOMING("Wyoming", "WY");

    /**
     * The state's name.
     */
    private String name;

    /**
     * The state's abbreviation.
     */
    private String abbreviation;

    /**
     * Constructs a new state.
     *
     * @param name
     *            the state's name. Must not be null.
     * @param abbreviation
     *            the state's abbreviation. Must not be null.
     */
    private State(final String name, final String abbreviation) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(abbreviation);
        this.name = name;
        this.abbreviation = abbreviation;
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
     * Gets the state's full name in a display-friendly format.
     *
     * @return The state's display name. Never null.
     */
    public String getFullName() {
        return name;
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
            if (s.abbreviation.equalsIgnoreCase(stateString)) {
                return s;
            } else if (s.name.equalsIgnoreCase(stateString)) {
                return s;
            }
        }

        // no match found
        return null;
    }

}