package com.sample.ng;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.sample.Exceptions.FatalFrameWorkException;
import com.sample.device.factory.DeviceWebDriver;
import com.sample.device.factory.DriverManager;
import com.sample.device.factory.SauceLabIntegration;
import com.sample.enums.ERRORDETAILS;
import com.sample.page.PageManager;
import com.sample.page.data.PageData;
import com.sample.page.data.PageDataManager;
import com.sample.page.data.provider.ExcelPageDataProvider;
import com.sample.page.data.provider.PageDataProvider;
import com.sample.page.data.provider.TestDataManager;
import com.sample.page.element.provider.MongoElementProvider;
import com.sample.properties.ConfigurationManager;


public class TestNGBase {
	
	protected ThreadLocal<String> methodName = new ThreadLocal<>();
	protected ThreadLocal<ExtentReports> reports = new ThreadLocal<ExtentReports>();
	protected ThreadLocal<TestName> testName;

    private static final String [] driverInfo =  new String [] {"driver.driverType","driver.BrowserType"};
    private static final String [] appInfo =  new String [] {"application.appUrl"};
    private static final String [] pageElementInfo =  new String [] {"pageManagement.siteName",	"pageManagement.fileName","pageManagement.ConnectionURL","pageManagement.DatabaseName","pageManagement.CollectionName"};
    private static final String[] DATA = new String[] { "pageManagement.pageData.provider", "pageManagement.pageData.fileName","pageManagement.pageData.customFile" };
    private static final String [] SAUCELABS = new String [] {"driver.SauceLabIntegration","driver.SauceLabIntegration.UserName","driver.SauceLabIntegration.AccessKey","driver.SauceLabIntegration.Domain"};

	protected ThreadLocal<DeviceWebDriver> driver;

	protected static Properties prop;

	protected static Boolean runAllTCBase;

	protected static String browserBase;

	protected String driverCurrentWindow = null;

	protected String driverOldWindow = null;

	private Log log = LogFactory.getLog( TestNGBase.class );


	//@Parameters( {"environment","runAllTC","browser"} )

	@BeforeSuite

	public void BeforeSuite()  throws IOException {
		
	    ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/testReport.html");

	    ExtentReports   extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
         
        //To add system or environment info by using the setSystemInfo method.
        extent.setSystemInfo("OS","Windows" );
        extent.setSystemInfo("Browser", "Chrome");
        
        //configuration items to change the look and feel
        //add content, manage tests etc
        htmlReporter.config().setDocumentTitle("Extent Report Demo");
        htmlReporter.config().setReportName("Test Report");
        //htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
        ReportingManager.instance().setExtent(extent);
        
		ConfigurationManager.instance().readFile(new File("./resources/config.properties"));
	
		
		PageManager.instance().setSiteName( ConfigurationManager.instance().getProperties().get(pageElementInfo[0]) );
		String[] fileNames = ConfigurationManager.instance().getProperties().get( pageElementInfo[1] ).split( "," );

			
		File[] files = new File[fileNames.length];

		/*for ( int i = 0; i < fileNames.length; i++ )
        files[i] = findFile( new File("./resources/config.properties"), new File( fileNames[i] ) );
		
		PageManager.instance().setElementProvider( new ExcelElementProvider( files,ConfigurationManager.instance().getProperties().get( pageElementInfo[0] )  ) );*/
        
		PageManager.instance().setElementProvider( new MongoElementProvider(ConfigurationManager.instance().getProperties().get( pageElementInfo[2] ),ConfigurationManager.instance().getProperties().get( pageElementInfo[3]) ,ConfigurationManager.instance().getProperties().get( pageElementInfo[4]  )));
		
		 //validateProperties( ConfigurationManager.instance().getProperties(), DATA );

         String[] fileNames_data = ConfigurationManager.instance().getProperties().get( DATA[1] ).split( "," );



         File[] files_data = new File[fileNames_data.length];

         for ( int i = 0; i < fileNames_data.length; i++ )

        	 files_data[i] = findFile( new File("./resources/config.properties"), new File( fileNames_data[i] ) );

        

         //validateProperties( ConfigurationManager.instance().getProperties(), new String[] { "pageManagement.pageData.tabNames" } );

         PageDataProvider dataProvider = new ExcelPageDataProvider( files_data, ConfigurationManager.instance().getProperties().get( "pageManagement.pageData.tabNames" ) ) ;
         PageDataManager.instance().setPageDataProvider( dataProvider );
         sauceLabInetegrationInitialization();

	}
	
	protected  DeviceWebDriver getDriver(){
		return this.driver.get();
		
	}

	private  void sauceLabInetegrationInitialization(){
		try {
			if(null!=ConfigurationManager.instance().getProperties().get(SAUCELABS[0])&&!ConfigurationManager.instance().getProperties().get(SAUCELABS[0]).isEmpty()) {
				DriverManager.instance().setIsSauceLabIntegartion(Boolean.valueOf(ConfigurationManager.instance().getProperties().get(SAUCELABS[0])));
				if(null!=ConfigurationManager.instance().getProperties().get(SAUCELABS[1])&&null!=ConfigurationManager.instance().getProperties().get(SAUCELABS[2])
						&& null!=ConfigurationManager.instance().getProperties().get(SAUCELABS[3])) {
					DriverManager.instance().setSauceLabIntegration(new SauceLabIntegration(ConfigurationManager.instance().getProperties().get(SAUCELABS[1]),
							ConfigurationManager.instance().getProperties().get(SAUCELABS[2]),ConfigurationManager.instance().getProperties().get(SAUCELABS[3])));
					
				}else {
					throw new FatalFrameWorkException(ERRORDETAILS.SAUCELABPROPS);
				}
					
			}
		}
		catch(Exception ex) {
         log.error("Exception while trying to integarte saucelabs"+ex.getMessage());
		}
	}
    @Parameters({ "browser" })
	@BeforeMethod

	public void BeforeMethod(Method method,@Optional("") String browser) throws Exception {
		try {
			this.driver = new ThreadLocal<DeviceWebDriver>();
			//testName.get().setTestName(method.);
       HashMap<String,String> configProps = ConfigurationManager.instance().getProperties();
       validateProperties();
		DeviceWebDriver driver_local=DriverManager.instance().getDriverFactory(configProps.get(driverInfo[0]))
		.createDriver(browser, configProps.get(appInfo[0]));
		if(driver_local!=null) this.driver.set(driver_local);
	}
	catch(Exception c) {
		log.error("Exception while driver creation "+c.toString());

	}
		
	}



	@AfterMethod(alwaysRun = true)

	public void AfterMethod(ITestResult result) {

		if( this.driver.get() != null) {

			this.driver.get().quit();


		} 
		
        ReportingManager.instance().getExtent().flush();

	}


	private void validateProperties() {
		try {
		      HashMap<String,String> configProps = ConfigurationManager.instance().getProperties();
		      if(configProps.get(driverInfo[0])==null||configProps.get(driverInfo[0]).isEmpty()) {
		    	  throw new FatalFrameWorkException(ERRORDETAILS.CONFIGPROPS);
		      }
		      if(configProps.get(driverInfo[1])==null||configProps.get(driverInfo[1]).isEmpty()) {
		    	  throw new FatalFrameWorkException(ERRORDETAILS.CONFIGPROPS);
		      }   
		      if(configProps.get(appInfo[0])==null||configProps.get(appInfo[0]).isEmpty()) {
		    	  throw new FatalFrameWorkException(ERRORDETAILS.CONFIGPROPS);
		      }  
		}
		catch(Exception ex) {
			log.error(ex.getMessage());
		}
	}

	
	  protected File findFile( File rootFolder, File useFile )

	    {

	        if ( useFile.exists() || useFile.isAbsolute() )

	            return useFile;

	       

	        File myFile = new File( rootFolder, useFile.getPath() );

	        if ( myFile.exists() )

	            return myFile;

	       

	        throw new IllegalArgumentException( "Could not find " + useFile.getName() + " at " + useFile.getPath() + " or " + myFile.getAbsolutePath() );

	       

	    }
	  
	  
	  
	  
	  @DataProvider ( name = "TestManager",parallel=true)

		public Object[][] getTestData(Method currentMethod)  

		{             



			String sMethodName=currentMethod.getName();

			

			HashSet<String> scriptInfo = TestDataManager.instance().getTestClassScriptList().get(sMethodName);
			
			Object[][] data = new Object[scriptInfo.size()][2];   
             int i =0;
			for(String scriptName:scriptInfo) {
				List<PageData> dataList = TestDataManager.instance().getEmbedDataMap().get(sMethodName+"_"+scriptName);
				data[i][0]= new TestName(scriptName+ "[" + sMethodName +"]");    
				data[i][1] = dataList;

				i++;
			}

			

			return data;

		}


	  @DataProvider ( name = "DataManager",parallel=true)

		public Object[][] getTestData_sample(Method currentMethod)  

		{             



			String sMethodName=currentMethod.getName();

			

			HashSet<String> scriptInfo = TestDataManager.instance().getTestClassScriptList().get(sMethodName);
			
			Object[][] data = new Object[scriptInfo.size()][1];   
           int i =0;
			for(String scriptName:scriptInfo) {
				List<PageData> dataList = TestDataManager.instance().getEmbedDataMap().get(sMethodName+"_"+scriptName);
			    
				data[i][0] = dataList;

				i++;
			}

			

			return data;

		}



}
