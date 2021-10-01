package com.cce.facebook.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.sample.ng.ReportingManager;
import com.sample.utility.SeleniumElement;

public class LoginPF extends SeleniumElement {
	
	WebDriver driver;

    

    public LoginPF(WebDriver driver) {

           super();

           this.driver = driver;

           PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = ".//input[@id='email']")

    public WebElement txtUserName;



    @FindBy(xpath = ".//input[@id='pass']")

    public WebElement txtPassword;



    @FindBy(xpath = ".//button[@id='u_0_b']")

    public WebElement btnLogin;

    
    @FindBy(xpath = ".//span[contains(text(),'Shamanth')]")
    
    public WebElement validationTxt;
    
    
    public void enterUsername(String userName) {

        setValue(txtUserName,userName);

 }



 public void enterPassword(String password) {

	 setValue( txtPassword,password);

 }



 public void clickLoginBtn() {

	 click( btnLogin);

 }



 public boolean ValidateText() {

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        if(IsDisplayed(validationTxt)) {

               return true;

        }

        else {

               return false;

        }

 }

   




}
