package com.sample.page.data.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.sample.page.data.PageData;


public class TestDataManager {
	

	public static HashMap<String, HashSet<String>>  testClassScriptList = new HashMap<>();

	private static HashMap<String,List<PageData>> embedDataMap = new HashMap<String,List<PageData>>();



	private boolean isPopulated;
    private boolean alreadyRead=false;
    
    
	private static TestDataManager singleton = new TestDataManager();

    /**
     * Instance.
     *
     * @return the page data manager
     */
    public synchronized static TestDataManager instance(  )
    {
   
            return singleton;
        
    }

    /**
     * Instantiates a new page data manager.
     */
    private TestDataManager() {}
    

	public static HashMap<String, HashSet<String>> getTestClassScriptList() {
		return TestDataManager.instance().testClassScriptList;
	}

	public static void setTestClassScriptList(String scriptName,String testName) {
		
		if(TestDataManager.instance().testClassScriptList.get(scriptName)!=null) {
			HashSet<String> methodName = testClassScriptList.get(scriptName);
			methodName.add(testName);
			TestDataManager.instance().testClassScriptList.put(scriptName, methodName);

		}else {
			HashSet<String> methodName = new HashSet<String>();
			methodName.add(testName);
			TestDataManager.instance().testClassScriptList.put(scriptName, methodName);
		}
	}

	public static HashMap<String, List<PageData>> getEmbedDataMap() {
		return TestDataManager.instance().embedDataMap;
	}

	public static void setEmbedDataMap(String testName,String MethodName,PageData pageData) {
		if(TestDataManager.instance().embedDataMap.get(testName+"_"+MethodName)!=null) {
			List<PageData> pageDataLst = embedDataMap.get(testName+"_"+MethodName);
			pageDataLst.add(pageData);
			TestDataManager.instance().embedDataMap.put(testName+"_"+MethodName, pageDataLst);

		}else {
			List<PageData> pageDataList = new ArrayList<PageData>();
			pageDataList.add(pageData);
			TestDataManager.instance().embedDataMap.put(testName+"_"+MethodName, pageDataList);
		}
	}
    
    
    
   

}
