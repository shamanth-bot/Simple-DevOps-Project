package com.sample.driver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.sample.properties.ConfigurationManager;

public class TestDriver {
	
	public static void main(String[] args) {
		ConfigurationManager.instance().readFile(new File("./resources/config.properties"));

		XmlSuite mySuite = new XmlSuite();

		// Add Suite Parameters

		mySuite.setName("AI Driven Automation");

		mySuite.setParallel(XmlSuite.ParallelMode.TESTS);

		mySuite.setVerbose(2);

		mySuite.setThreadCount(2);

		// Create Test tag


		// Create Class that need to run

		List<XmlClass> classes = new ArrayList<>();
		
		String [] classNames =new String [] {"com.sample.testPackage.LoginReg"};
		String [] methods =new String [] {"LoginQuote","LoginQuote_1"};

		String [] Browsertype =ConfigurationManager.instance().getProperties().get("driver.BrowserType").split(",");
		
			for(String methodName:methods) {
		for(String browser:Browsertype) {
	    		XmlTest myTest = new XmlTest(mySuite);

	    		myTest.setName("Auto"+"_"+methodName+"_"+browser);
	    		   HashMap<String, String> parametersMap = new HashMap<>();
	               parametersMap.put("browser",browser);
			XmlClass cls = new XmlClass(classNames[0]);
			ArrayList<XmlInclude> includedMethods = new ArrayList<>();
			includedMethods.add(new XmlInclude(methodName));
			cls.setIncludedMethods(includedMethods);
			classes.add(cls);
			myTest.setXmlClasses(classes);
			myTest.setParameters(parametersMap);

	        }
			
		}
			/*XmlTest myTest1 = new XmlTest(mySuite);

			myTest1.setName("Auto1");
	        
			
			List<XmlClass> classes1 = new ArrayList<>();

			XmlClass cls1 = new XmlClass(classNames[1]);
			ArrayList<XmlInclude> includedMethods1 = new ArrayList<>();
			includedMethods1.add(new XmlInclude("LoginQuote_1"));
			cls1.setIncludedMethods(includedMethods1);
			classes1.add(cls1);
			myTest1.setXmlClasses(classes1);*/
			
			mySuite.setFileName("tempSuite.xml");
			TestNG myTestNG = new TestNG();
			

			// Assign Suite to Test NG

			List<XmlSuite> suites = new ArrayList<>();

			suites.add(mySuite);

			myTestNG.setXmlSuites(suites);

			myTestNG.run();

		
	}

}
