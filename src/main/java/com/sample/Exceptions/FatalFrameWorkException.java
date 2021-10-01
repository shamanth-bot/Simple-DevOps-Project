package com.sample.Exceptions;

import com.sample.enums.ERRORDETAILS;

public class FatalFrameWorkException extends Exception {
	
    private static final long serialVersionUID = 7718828512143293558L;
    
    
    private final String  errordescription;

    public FatalFrameWorkException(ERRORDETAILS details){
    	super();
    	this.errordescription= details.getDetails();
    }

}
