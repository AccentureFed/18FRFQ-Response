package com.afs.jigsaw.fda.food.api

import static org.junit.Assert.assertEquals

import org.junit.Test

public class StateTest {

    @Test
    public void testGetStateByEnumStringValue() {
        final String stateEnumString = State.NEW_YORK.toString()
        assertEquals(State.NEW_YORK, State.fromString(stateEnumString))
    }

    @Test
    public void testGetStateByFullName() {
        assertEquals(State.NEW_YORK, State.fromString("New York"))
    }

    @Test
    public void testGetStateByAbbreviation() {
        assertEquals(State.NEW_YORK, State.fromString("NY"))
    }

    @Test
    public void testGetStateByAbbreviationLowercase() {
        assertEquals(State.NEW_YORK, State.fromString("ny"))
    }

    @Test
    public void testNonAbbreviateAbbreviationLowercase() {
        assertEquals(null, State.fromString("in"))
    }

    @Test
    public void testMultipleNames() {
        assertEquals(State.DISTRICT_OF_COLUMBIA, State.fromString("District of Columbia"))
        assertEquals(State.DISTRICT_OF_COLUMBIA, State.fromString("Washington DC"))
        assertEquals(State.DISTRICT_OF_COLUMBIA, State.fromString("Washington D.C."))
    }

    @Test
    public void testInvalidStateString() {
        assertEquals(null, State.fromString("not a state"))
    }
}
