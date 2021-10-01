package com.sample.utility;

import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.sample.ng.ReportingManager;
import com.sample.page.element.ElementDetails;
import com.sample.page.element.ElementDetailsFactory;

public class SeleniumElement implements Element{

	@Override
	public void setValue(WebElement element, String name) {
       
		try {
			ElementDetails elemntDetails = new ElementDetailsFactory().getDetails(element);
			
			element.sendKeys(name);
    	    ReportingManager.instance().getExtenttest().log(Status.INFO, MarkupHelper.createLabel("Set value field with locator "+elemntDetails.getLocator_Type()
    	                                                      +"With locator value "+elemntDetails.getLocator_Value()+" the value" +"<span style=\"color:red\">"+name+"</span>", ExtentColor.BLUE));

			
		}catch(Exception c) {
    	    ReportingManager.instance().getExtenttest().log(Status.ERROR,MarkupHelper.createLabel("unable to Set value field with locator and locator value as"+name, ExtentColor.RED));

		}
		
	}

	@Override
	public void click(WebElement element) {
		// TODO Auto-generated method stub
		try {
			ElementDetails elemntDetails = new ElementDetailsFactory().getDetails(element);

			element.click();

    	    ReportingManager.instance().getExtenttest().log(Status.INFO, "clicked on the button "+elemntDetails.getLocator_Type()+" locator value "+elemntDetails.getLocator_Value());

		
		}catch(Exception c) {
    	    ReportingManager.instance().getExtenttest().log(Status.ERROR, "failed to click on the  button");

		}
	}

	@Override
	public boolean IsDisplayed(WebElement element) {
		// TODO Auto-generated method stub
		if(element.isDisplayed()) {
			return true;
		}else {
			return false;
		}
	}

}
