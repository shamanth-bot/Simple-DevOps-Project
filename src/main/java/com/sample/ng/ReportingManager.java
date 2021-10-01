package com.sample.ng;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ReportingManager {
	private static ReportingManager singleton = new ReportingManager();
	private ExtentReports extent;
	private static final ThreadLocal<ExtentTest> ExtentTest= new ThreadLocal<ExtentTest>();

    /**
     * Instance.
     *
     * @return the page data manager
     */
    public synchronized static ReportingManager instance(  )
    {
   
            return singleton;
        
    }

  
    /**
     * Instantiates a new page data manager.
     */
    private ReportingManager() {}

	public static ReportingManager getSingleton() {
		return singleton;
	}

	public static void setSingleton(ReportingManager singleton) {
		ReportingManager.singleton = singleton;
	}

	public ExtentReports getExtent() {
		return extent;
	}

	public void setExtent(ExtentReports extent) {
		this.extent = extent;
	}


	public   ExtentTest getExtenttest() {
		return ExtentTest.get();
	}
    
	public  void setExtentTest(ExtentTest test) {
		ExtentTest.set(test);
	}
	
	
   
    
}
