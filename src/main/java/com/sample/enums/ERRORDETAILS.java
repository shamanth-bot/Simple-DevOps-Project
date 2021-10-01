package com.sample.enums;

public enum ERRORDETAILS {
	
	DRIVERERROR("Unable to create driver due to null driver type"),
	CONFIGPROPS("mandatory config file properties cant be null or empty"),
    SAUCELABPROPS("username,accessky and domain name for the saucelab execution type cant be empty and are mandatory");
	
	private String details;
	
	ERRORDETAILS(String details) {
		this.details= details;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	
	

}
