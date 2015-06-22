package com.afs.jigsaw.fda.food.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StateSearchCriteriaUtilsTest {

	private StateSearchCriteriaUtils utils = new StateSearchCriteriaUtils();

    @Test
    public void testSimpleState() {
        assertEquals("distribution_pattern:nationwide+distribution_pattern:\"all+50+u.s.+states\"+distribution_pattern:AL+distribution_pattern:\"Alabama\"", utils.generateCriteria(State.ALABAMA));
    }

    @Test
    public void testSpacesEscaped() {
    
    	assertEquals("distribution_pattern:nationwide+distribution_pattern:\"all+50+u.s.+states\"+distribution_pattern:NY+distribution_pattern:\"New+York\"", utils.generateCriteria(State.NEW_YORK));
    }

    @Test
    public void testStateWithExclusions() {
        assertEquals("distribution_pattern:nationwide+distribution_pattern:\"all+50+u.s.+states\"+distribution_pattern:VA+(NOT+distribution_pattern:\"West+Virginia\"+AND+distribution_pattern:\"Virginia\")", utils.generateCriteria(State.VIRGINIA));
    }

    
    @Test
    public void testMultiTermState() {
        assertEquals("distribution_pattern:nationwide+distribution_pattern:\"all+50+u.s.+states\"+distribution_pattern:DC+distribution_pattern:\"District+of+Columbia\"+distribution_pattern:\"Washington+DC\"+distribution_pattern:\"Washington+D.C.\"", utils.generateCriteria(State.DISTRICT_OF_COLUMBIA));
    }

    @Test
    public void testNull() {
        assertEquals("distribution_pattern:nationwide+distribution_pattern:\"all+50+u.s.+states\"", utils.generateCriteria(null));
    }

}
