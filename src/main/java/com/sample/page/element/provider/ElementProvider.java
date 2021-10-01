package com.sample.page.element.provider;

import com.sample.page.ElementDescriptor;
import com.sample.page.element.Element;

public interface ElementProvider {

	Element getElement(ElementDescriptor elemeDescriptor);
	
	boolean isInitialized();
	
	
}

