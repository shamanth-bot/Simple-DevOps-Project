package com.sample.testPackage;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.cce.facebook.pages.spi.LoginSteps;
import com.sample.ng.ReportingManager;
import com.sample.ng.TestNGBase;
import com.sample.ng.TestName;
import com.sample.page.data.PageData;



public class LoginReg extends TestNGBase{


	@Test(description = "Ontario, quote for auto",dataProvider="TestManager")

	public void E2E_AutoNBTransaction(TestName testName,List<PageData> dataList)  {
		

		try {
			WebDriver wedriver = (WebDriver)getDriver();
             PageData data = dataList.get(0);
             
			   LoginSteps homeSteps = new LoginSteps(wedriver);
	           homeSteps.selectLogin(data.getData("username"), data.getData("Password"));
	          Assert.assertTrue(homeSteps.validateLogin());
		}
		catch(Exception e) {
			Assert.assertTrue(false); 

		}
		
		

		// reports.get().flush();

	}

	
	

	@Test(description = "Ontario, quote for auto",dataProvider="TestManager")

	public void E2E_AutoNBTransaction_Negative(TestName testName,List<PageData> dataList) throws Exception {
		try {

	      

			WebDriver wedriver = (WebDriver)getDriver();
            PageData data = dataList.get(0);

			   LoginSteps homeSteps = new LoginSteps(wedriver);
	           homeSteps.selectLogin(data.getData("username"), data.getData("Password"));
	          Assert.assertTrue(homeSteps.validateLogin());
		}
		catch(Exception e) {
			Assert.assertTrue(false); 

		}
	}




}