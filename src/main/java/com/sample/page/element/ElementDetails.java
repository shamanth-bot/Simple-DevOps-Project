package com.sample.page.element;

public class ElementDetails {

	

	private String locator_Type;
	private String locator_Value;
	
	
	public ElementDetails(String locator_Type, String locator_Value) {
		super();
		this.locator_Type = locator_Type;
		this.locator_Value = locator_Value;
	}

	public String getLocator_Type() {
		return locator_Type;
	}
	
	public void setLocator_Type(String locator_Type) {
		this.locator_Type = locator_Type;
	}
	
	public String getLocator_Value() {
		return locator_Value;
	}
	
	public void setLocator_Value(String locator_Value) {
		this.locator_Value = locator_Value;
	}
	

	
	

	
}
