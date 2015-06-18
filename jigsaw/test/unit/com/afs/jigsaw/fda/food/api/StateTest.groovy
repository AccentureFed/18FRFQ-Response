package com.afs.jigsaw.fda.food.api

import static org.junit.Assert.assertEquals

import org.junit.Test

public class StateTest {

    @Test
    public void testGetStateByEnumStringValue() {
        final String stateEnumString = State.NEW_YORK.toString()
        final State state = State.fromString(stateEnumString)
        assertEquals(state, State.NEW_YORK)
    }

    @Test
    public void testGetStateByFullName() {
        final String stateFullNameString = "New York"
        final State state = State.fromString(stateFullNameString)
        assertEquals(state, State.NEW_YORK)
    }

    @Test
    public void testGetStateByAbbreviation() {
        final String stateAbbrString = "NY"
        final State state = State.fromString(stateAbbrString)
        assertEquals(state, State.NEW_YORK)
    }

    @Test
    public void testGetStateByAbbreviationLowercase() {
        final String stateAbbrString = "ny"
        final State state = State.fromString(stateAbbrString)
        assertEquals(state, State.NEW_YORK)
    }

    @Test
    public void testInvalidStateString() {
        final String stateAbbrString = "not a state"
        final State state = State.fromString(stateAbbrString)
        assertEquals(null, state)
    }
}
