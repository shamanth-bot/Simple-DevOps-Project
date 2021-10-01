package com.sample.capabilities;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BuildCapabilities implements WebCapabilities{
	
	
	public DesiredCapabilities getInternetExplorerCapabilities() {
		DesiredCapabilities options = new DesiredCapabilities();

		options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		
		return options;
	}

	@Override
	public DesiredCapabilities getfireFoxCapabilities() {
	

		FirefoxOptions options = new FirefoxOptions();

		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);

		return capabilities;
	}

	@Override
	public DesiredCapabilities getChromeCapabilities() {
		// TODO Auto-generated method stub
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--ignore-certifcate-errors");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setCapability("version", "latest");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);	
	     return capabilities;
	
	}
	
	

}
