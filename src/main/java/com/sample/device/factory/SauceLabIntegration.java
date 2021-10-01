package com.sample.device.factory;

public class SauceLabIntegration {
	
	private  String userName;
	private  String passWord;
	private  String domainName;
	
	public SauceLabIntegration(String userName, String passWord, String domainName) {
		super();
		this.userName = userName;
		this.passWord = passWord;
		this.domainName = domainName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	
	
  

}
