package com.afs.jigsaw.fda.food.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SeverityTest {

    @Test
    public void noneFound() {
        assertEquals(null, Severity.getByFdaValue(""));
    }

    @Test
    public void testNull() {
        assertEquals(null, Severity.getByFdaValue(null));
    }

    @Test
    public void testMatchFound() {
        assertEquals(Severity.HIGH, Severity.getByFdaValue("Class I"));
    }

}
