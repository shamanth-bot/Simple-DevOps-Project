package com.sample.device.factory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.logging.Logs;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sample.spi.driver.NativeDriverProvider;

public class DeviceWebDriver  implements HasCapabilities, WebDriver, JavascriptExecutor,TakesScreenshot,NativeDriverProvider{

	 private static Pattern FORMAT_PATTERN = Pattern.compile( "\\{([\\w\\.]*)\\}" );
	    protected WebDriver webDriver;

		private Log log = LogFactory.getLog( DeviceWebDriver.class );
	    private DeviceOptions deviceOptions = null;

	    private Document cachedDocument = null;

	    private double widthModifier = 1;
	    private double heightModifier = 1;
	    
	    private int deviceHeight;
	    private int deviceWidth;
	    private long implicitWait = 0;
	    private long scriptTimeout = 0;
	    private long pageLoadTimeout = 0;
	    private DesiredCapabilities dC;

	    private boolean cachingEnabled = true;
	    
	    private DeviceTimeouts deviceTimeouts = null;
	@Override
	public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
		  if ( webDriver instanceof TakesScreenshot )
	            return ((TakesScreenshot) webDriver).getScreenshotAs( target );
	        else
	            throw new IllegalArgumentException( "Screenshot functionality not supported" );
	}
	
	 public DeviceWebDriver(WebDriver webDriver, boolean cachingEnabled, DesiredCapabilities dC )
	    {
		    
		        this.webDriver = webDriver;

	        this.cachingEnabled = true;
	     
	        this.dC = dC;
	    }

	@Override
	public Object executeScript(String script, Object... args) {
		// TODO Auto-generated method stub
	      return ((JavascriptExecutor) webDriver).executeScript( script, args );
	}

	@Override
	public Object executeAsyncScript(String script, Object... args) {
		// TODO Auto-generated method stub
	    return ((JavascriptExecutor) webDriver).executeAsyncScript( script, args );
	}

	 public void cacheData()
	    {
	        if ( !cachingEnabled )
	            return;

	        if ( log.isInfoEnabled() )
	            log.info( Thread.currentThread().getName() + ": Caching page data" );
	        readXML( getPageSource() );
	    }
	    
	    public void readXML( String pageSource )
	    {
	        try
	        {
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

	            InputStreamReader streamReader = new InputStreamReader( new ByteArrayInputStream( pageSource.getBytes() ), "UTF-8" );
	            InputSource inputSource = new InputSource( streamReader );
	            inputSource.setEncoding( "UTF-8" );

	            cachedDocument = dBuilder.parse( inputSource );
	            cachingEnabled = true;
	        }
	        catch ( Exception e )
	        {
	            log.warn( "CACHING HAS BEEN DISABLED", e );
	            cachingEnabled = false;
	            cachedDocument = null;
	        }
	    }
	@Override
	public void get(String url) {
		// TODO Auto-generated method stub
	webDriver.get(url);
	
    
    try
    {
        double outerHeight = Integer.parseInt( executeScript( "return window.outerHeight;" ) + "" );
        double outerWidth = Integer.parseInt( executeScript( "return window.outerWidth;" ) + "" );
        
        Dimension windowSize = manage().window().getSize();
        Object f = executeScript( "return window.outerHeight;" );
        heightModifier = (double) windowSize.getHeight() / outerHeight;
        widthModifier = (double) windowSize.getWidth() / outerWidth;
        
    }
    catch( Exception e )
    {
        log.warn( "Could not extract height/width modifiers" );
        heightModifier = 1;
        widthModifier = 1;
    }
	}

	@Override
	public String getCurrentUrl() {
		// TODO Auto-generated method stub
		return webDriver.getCurrentUrl();
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return webDriver.getTitle();
				
	}
	
	  public void clearCache()
	    {
	        cachedDocument = null;
	    }

	@Override
	public List<WebElement> findElements(By by) {
		// TODO Auto-generated method stub
		try {
        List<WebElement> elementList = new ArrayList<WebElement>( 10 );
        List<WebElement> elements = webDriver.findElements(by);
        return elements.stream().collect(Collectors.toList());
		}
		catch(Exception c) {
			return null;
		}
        

	}

	@Override
	public WebElement findElement(By by) {
		// TODO Auto-generated method stub
		try {
	      return webDriver.findElement(by);
			}
			catch(Exception c) {
				return null;
			}
	        
	}

	@Override
	public String getPageSource() {
		// TODO Auto-generated method stub
		return webDriver.getPageSource();
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		webDriver.close();
	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub
		webDriver.quit();
	}

	@Override
	public Set<String> getWindowHandles() {
		// TODO Auto-generated method stub
		return webDriver.getWindowHandles();
	}

	@Override
	public String getWindowHandle() {
		// TODO Auto-generated method stub
		return webDriver.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo() {
		// TODO Auto-generated method stub
		  return webDriver.switchTo();
	}

	@Override
	public Navigation navigate() {
		// TODO Auto-generated method stub
		return webDriver.navigate();
	}

	@Override
	public Options manage() {
		// TODO Auto-generated method stub
		  if ( deviceOptions == null )
	            deviceOptions = new DeviceOptions( webDriver.manage() );
	        return deviceOptions;
	}

	@Override
	public Capabilities getCapabilities() {
		// TODO Auto-generated method stub
	     if ( webDriver instanceof HasCapabilities )
	            return ((HasCapabilities) webDriver).getCapabilities();
	        else
	            return null;
	}

	   @Override

	    public WebDriver getNativeDriver()

	    {

	        return webDriver;

	    }
	
	   public class DeviceOptions implements Options
	    {
	        private Options options;
	        public DeviceOptions( Options options )
	        {
	            this.options = options;
	        }
	        
	        @Override
	        public void addCookie( Cookie cookie )
	        {
	            options.addCookie( cookie );
	            
	        }

	        @Override
	        public void deleteCookieNamed( String name )
	        {
	            options.deleteCookieNamed( name );
	            
	        }

	        @Override
	        public void deleteCookie( Cookie cookie )
	        {
	            options.deleteCookie( cookie );
	            
	        }

	        @Override
	        public void deleteAllCookies()
	        {
	            options.deleteAllCookies();
	            
	        }

	        @Override
	        public Set<Cookie> getCookies()
	        {
	            return options.getCookies();
	        }

	        @Override
	        public Cookie getCookieNamed( String name )
	        {
	            return options.getCookieNamed( name );
	        }

	        @Override
	        public Timeouts timeouts()
	        {
	            if ( deviceTimeouts == null )
	                deviceTimeouts = new DeviceTimeouts( options.timeouts() );
	            return deviceTimeouts;
	        }

	        @Override
	        public ImeHandler ime()
	        {
	            return options.ime();
	        }

	        @Override
	        public Window window()
	        {
	            return options.window();
	        }

	        @Override
	        public Logs logs()
	        {
	            return options.logs();
	        }
	        
	    }
	   
	   public class DeviceTimeouts implements Timeouts
	    {
	        private Timeouts timeouts;
	        public DeviceTimeouts( Timeouts timeouts )
	        {
	             this.timeouts = timeouts;
	        }
	        
	        @Override
	        public Timeouts implicitlyWait( long time, TimeUnit unit )
	        {
	            
	            timeouts.implicitlyWait( time, unit );
	            implicitWait = unit.toMillis( time );
	            if ( log.isInfoEnabled() )
	                log.info( "Setting IMPLICIT WAIT to " + implicitWait );
	            return this;
	        }

	        @Override
	        public Timeouts setScriptTimeout( long time, TimeUnit unit )
	        {
	            timeouts.setScriptTimeout( time, unit );
	            scriptTimeout = unit.toMillis( time );
	            return this;
	        }

	        @Override
	        public Timeouts pageLoadTimeout( long time, TimeUnit unit )
	        {
	            timeouts.pageLoadTimeout( time, unit );
	            pageLoadTimeout = unit.toMillis( time );
	            return this;
	        }
	        
	    }
	    
	   
	    
}
