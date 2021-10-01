package com.sample.utility;

import org.openqa.selenium.WebElement;

public interface Element {
	
	public void setValue(WebElement element,String name);
	
	public void click(WebElement element);
	
	public boolean IsDisplayed(WebElement element);
	
		
	

}
