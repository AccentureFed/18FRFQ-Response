package com.afs.jigsaw.fda.food.api;

import org.apache.commons.lang.StringUtils;

/**
 * Represents a simple upc 12-digit barcode as scanned from a 
 * food product in a store.  the assumption is that the barcode is provided as 12-digits
 * and has no spaces.  
 * 
 * @author peterdailey
 *
 */
public class UpcBarcode {
	
	/**
	 * Builds barcode from a 12-digit (no space) barcode
	 * return null if not valid barcode string (i.e. not numeric)
	 * @return
	 */
	public static UpcBarcode buildBarcode(String s){
		if (s == null || s.length() != 12 || !StringUtils.isNumeric(s)) {
			return null;
		}
		
		UpcBarcode bcd = new UpcBarcode();
		bcd.setEncoding(s.substring(0, 1));
		bcd.setManufacturer(s.substring(1, 6));
		bcd.setProduct(s.substring(6, 11));
		bcd.setCheckDigit(s.substring(11, 12));
		return bcd;
	}
	
	private String encoding;
	private String manufacturer;
	private String product;
	private String checkDigit;
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getCheckDigit() {
		return checkDigit;
	}
	public void setCheckDigit(String checkDigit) {
		this.checkDigit = checkDigit;
	}
	@Override
	public String toString() {
		return encoding + manufacturer+product + checkDigit;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((checkDigit == null) ? 0 : checkDigit.hashCode());
		result = prime * result
				+ ((encoding == null) ? 0 : encoding.hashCode());
		result = prime * result
				+ ((manufacturer == null) ? 0 : manufacturer.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpcBarcode other = (UpcBarcode) obj;
		if (checkDigit == null) {
			if (other.checkDigit != null)
				return false;
		} else if (!checkDigit.equals(other.checkDigit))
			return false;
		if (encoding == null) {
			if (other.encoding != null)
				return false;
		} else if (!encoding.equals(other.encoding))
			return false;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.equals(other.manufacturer))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}
	
	
}
