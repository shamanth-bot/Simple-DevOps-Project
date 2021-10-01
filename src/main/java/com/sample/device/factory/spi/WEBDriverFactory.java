package com.sample.device.factory.spi;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.sample.Exceptions.FatalFrameWorkException;
import com.sample.capabilities.BuildCapabilities;
import com.sample.capabilities.WebCapabilities;
import com.sample.device.factory.DeviceWebDriver;
import com.sample.device.factory.DriverFactory;
import com.sample.device.factory.DriverManager;
import com.sample.enums.ERRORDETAILS;
import com.sample.enums.FRAMEWORKDEFINITIONS;

public class WEBDriverFactory implements DriverFactory{

	private static final String firefox = "firefox";
	private static final String chrome = "chrome";
	private static final String internetExplorer = "internetexplorer";

	private Log log = LogFactory.getLog( DeviceWebDriver.class );

	@Override
	public DeviceWebDriver createDriver(String BrowserName,String Url) {
		
        DeviceWebDriver webDriver = null;

		try {
			WebCapabilities cap = new BuildCapabilities();
			DesiredCapabilities dc = new DesiredCapabilities();
			
			
	        File driverFile;

	 if(BrowserName==null) {
		 throw new FatalFrameWorkException(ERRORDETAILS.DRIVERERROR);
	 }
	 if(BrowserName.isEmpty()) {
		 throw new FatalFrameWorkException(ERRORDETAILS.DRIVERERROR);
 
	 }
		
	switch(FRAMEWORKDEFINITIONS.valueOf(BrowserName.toUpperCase()).getValue()) {
		
	case firefox:
		if(System.getProperty("webdriver.gecko.driver")==null) {
			driverFile = new File("./resources/drivers/geckodriver.exe");
			if(driverFile.exists()) {
				log.info("adding gecko driver to the System");
				System.setProperty("webdriver.gecko.driver", "./resources/drivers/geckodriver.exe");
			}
			
		}
		dc=cap.getfireFoxCapabilities();
		
		break;
		
	case internetExplorer:	
		if(System.getProperty("webdriver.gecko.driver")==null) {
			driverFile = new File("./resources/drivers/IEDriverServer.exe");
			if(driverFile.exists()) {
				log.info("adding internet explorer driver to the System");
				System.setProperty("webdriver.gecko.driver", "./resources/drivers/IEDriverServer.exe");
			}
			
		}
		dc=cap.getInternetExplorerCapabilities();
		break;
		
	case chrome:	
		if(System.getProperty("webdriver.chrome.driver")==null) {
			driverFile = new File("./resources/drivers/chromedriver.exe");
			if(driverFile.exists()) {
				log.info("adding chrome driver to the System");
				System.setProperty("webdriver.chrome.driver", "./resources/drivers/chromedriver.exe");
			}
			
		}
		dc=cap.getChromeCapabilities();
		break;	
		
	}
	//URL	hubUrl = new URL("http://127.0.0.1:4444/wd/hub");
	String build_Url = DriverManager.instance().getIsSauceLabIntegartion()?
			           "https://"+DriverManager.instance().getSauceLabIntegration().getUserName()+":"+DriverManager.instance().getSauceLabIntegration().getPassWord()
			           +"@"+DriverManager.instance().getSauceLabIntegration().getDomainName()+":443/wd/hub":"http://127.0.0.1:4444/wd/hub";
	URL	hubUrl = new URL(build_Url);
    webDriver = new DeviceWebDriver( new RemoteWebDriver( hubUrl, dc ),false, dc );
    log.info("Succesfully created the driver object for the browsserr Type"+BrowserName);
    webDriver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
    webDriver.get(Url);
    log.info("Succesfully launched");

     return webDriver;
	}
	catch(Exception c) {
		 log.fatal( "Could not connect to " + "driver" + " (" + c.getMessage() + ")", c );
         log.debug( c);
         if ( webDriver != null )
         {
             try
             {
                 webDriver.close();
             }
             catch ( Exception e2 )
             {
             }
             try
             {
                 webDriver.quit();
             }
             catch ( Exception e2 )
             {
             }
         }
	}
		
		
	return null;
	}
	
	

}
