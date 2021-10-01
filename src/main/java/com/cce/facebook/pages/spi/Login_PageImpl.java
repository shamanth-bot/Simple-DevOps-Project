package com.cce.facebook.pages.spi;

import java.util.concurrent.TimeUnit;

import com.cce.facebook.pages.Login_Page;
import com.sample.page.AbstractPage;

public class Login_PageImpl extends AbstractPage implements Login_Page{

	@Override
	public void initializePage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Login(String name, String pwd) {
		 getElement(Login_Page.txtUserName).waitForPresent(10, TimeUnit.SECONDS);
     getElement(Login_Page.txtUserName).setValue(name);
     getElement(Login_Page.txtPassword).setValue(pwd);
     getElement(Login_Page.btnLogin).click();
		
	}

	@Override
	public boolean validate(String pwd) {
	
		if(getElement(Login_Page.txtPassword).isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	


}
