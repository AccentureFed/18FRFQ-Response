package com.afs.jigsaw.fda.food.api;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UpcBarcodeTest {

	@Test
	public void testNull() {
		assertEquals(null, UpcBarcode.buildBarcode(null));
	}

	@Test
	public void testEmpty() {
		assertEquals(null, UpcBarcode.buildBarcode(""));
	}

	@Test
	public void testWrongLength() {
		assertEquals(null, UpcBarcode.buildBarcode("53454354353"));
	}

	@Test
	public void testNotNumerix() {
		assertEquals(null, UpcBarcode.buildBarcode("123456789oo0"));
	}
	
	@Test
	public void testHasSpaces() {
		assertEquals(null, UpcBarcode.buildBarcode("1 12345 1234"));
	}

	@Test
	public void testValid() {
		UpcBarcode upc = UpcBarcode.buildBarcode("112345234560");
		assertEquals("1", upc.getEncoding());
		assertEquals("12345", upc.getManufacturer());
		assertEquals("23456", upc.getProduct());
		assertEquals("0", upc.getCheckDigit());
	}

}
