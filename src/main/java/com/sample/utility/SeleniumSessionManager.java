package com.sample.utility;

 

import org.openqa.selenium.WebDriver;


 

public interface SeleniumSessionManager

{

    public WebDriver getAltWebDriver( String name );

 

    public void registerAltWebDriver( String name, String deviceId );

   

    public void registerInactiveWebDriver( String name );

}

 