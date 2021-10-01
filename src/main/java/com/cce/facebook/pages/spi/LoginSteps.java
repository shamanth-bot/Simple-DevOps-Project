package com.cce.facebook.pages.spi;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;
import com.cce.facebook.pages.LoginPF;
import com.sample.ng.ReportingManager;


public class LoginSteps {

	
	WebDriver driver;

    LoginPF loginPF;

   

    public LoginSteps(WebDriver driver) {

           super();

           this.driver = driver;

           loginPF = new LoginPF(driver);

    }
    
    //@Step("Start the facbeook  login")

    public void selectLogin(String userName, String passWord) {

    	    loginPF.enterUsername(userName);
    	    loginPF.enterPassword(passWord);

           //ScreenShotsHelper.takeScreenShot(driver, "Login into facebook account by entering username and password");

           loginPF.clickLoginBtn();

    }

 

    public boolean validateLogin() {

    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
           //ScreenShotsHelper.takeScreenShot(driver, "Login into facebook account by entering username and password");

          return loginPF.ValidateText();

    }

   
}
