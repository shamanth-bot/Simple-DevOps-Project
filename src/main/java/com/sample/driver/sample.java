package com.sample.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.sample.Exceptions.FatalFrameWorkException;
import com.sample.device.factory.DeviceWebDriver;
import com.sample.device.factory.DriverManager;

public class sample {
	
	public static void main(String[] args) throws FatalFrameWorkException {
		DeviceWebDriver driver =DriverManager.instance().getDriverFactory("WEB").createDriver("chrome", "http://www.facebook.com");
		WebElement element = driver.findElement(By.xpath(".//input[@name='email']"));
		
		element.sendKeys("sample");
	}

}
