package com.sample.page.element;

import org.openqa.selenium.WebElement;

public class ElementDetailsFactory {
	
	private static final String LOCATOR_REF= "->";
	private static final String LOCATOR_VAL= ":";
	
	
	public ElementDetails getDetails(WebElement element) {
		String nam = element.toString();
		ElementDetails details = null;
		String []  elementdetails = nam.split("->")[1].split(":");
		
		if(elementdetails.length>0) {
			details= new ElementDetails(elementdetails[0],elementdetails[1]);
		}
		
		return details;
		
	}

}
