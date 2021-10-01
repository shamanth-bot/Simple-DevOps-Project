package com.sample.testPackage;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.cce.facebook.pages.Login_Page;
import com.sample.ng.TestNGBase;
import com.sample.ng.TestName;
import com.sample.page.PageManager;
import com.sample.page.data.PageData;

public class LoginRegTest extends TestNGBase{
	public Login_Page page;

	@Test(description = "Ontario, quote for auto",dataProvider="TestManager")

	public void E2E_AutoNBTransaction(TestName testName,List<PageData> dataList)  {


		try {
			WebDriver wedriver = (WebDriver)getDriver();
			page = (Login_Page)PageManager.getInstance().createPage(Login_Page.class, wedriver);

             PageData data = dataList.get(0);
             page.Login(data.getData("username"), data.getData("Password"));
	          Assert.assertTrue(page.validate(data.getData("Password")));
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
			page = (Login_Page)PageManager.getInstance().createPage(Login_Page.class, wedriver);


            PageData data = dataList.get(0);
            page.Login(data.getData("username"), data.getData("Password"));
	          Assert.assertTrue(page.validate(data.getData("Password")));
		}
		catch(Exception e) {
			Assert.assertTrue(false); 

		}
	}

}
