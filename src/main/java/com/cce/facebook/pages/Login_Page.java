package com.cce.facebook.pages;

import com.sample.page.Page;
import com.sample.page.Page.ElementDefinition;

public interface Login_Page extends Page{
	@ElementDefinition
	public String txtUserName = "txtUserName";
	
	@ElementDefinition
	public String txtPassword = "txtPassword";
	
	@ElementDefinition
	public String btnLogin = "btnLogin";
	
	public void Login(String name,String pwd);
	
	
	public boolean validate(String pwd);

}
