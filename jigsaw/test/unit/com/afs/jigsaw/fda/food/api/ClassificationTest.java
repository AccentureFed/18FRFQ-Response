package com.afs.jigsaw.fda.food.api;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClassificationTest {

	@Test
	public void noneFound() {
		assertEquals(null, Classification.getByFdaValue(""));
	}

	@Test
	public void testNull() {
		assertEquals(null, Classification.getByFdaValue(null));
	}

	@Test
	public void testMatchFound() {
		assertEquals(Classification.HIGH, Classification.getByFdaValue("Class I"));
	}

}
