package com.sample.page.element.provider;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class ElementPOJO {
	private String siteName;
	private String pageName;
	private String name;
	private String descriptor;
	private String value;
	
    public ElementPOJO() {
    	
    }

	public ElementPOJO(String siteName, String pageName, String name, String descriptor, String value) {
		super();
		this.siteName = siteName;
		this.pageName = pageName;
		this.name = name;
		this.descriptor = descriptor;
		this.value = value;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	
	

}
