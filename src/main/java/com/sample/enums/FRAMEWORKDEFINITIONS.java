package com.sample.enums;

public enum FRAMEWORKDEFINITIONS {
	
	FIREFOX("firefox"),
	CHROME("chrome"),
	IE("internetexplorer");
    
	private final String value;
	
	FRAMEWORKDEFINITIONS(String value){
		this.value= value;
	}

	public String getValue() {
		return value;
	}
	
	

}
