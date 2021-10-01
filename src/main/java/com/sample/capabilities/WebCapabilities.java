package com.sample.capabilities;

import org.openqa.selenium.remote.DesiredCapabilities;

public interface WebCapabilities {
	
	public DesiredCapabilities getInternetExplorerCapabilities();
	
	public DesiredCapabilities getfireFoxCapabilities();

	public DesiredCapabilities getChromeCapabilities();

	

}
