package com.sample.page;



import java.awt.image.BufferedImage;

import java.io.File;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;

import org.apache.commons.logging.LogFactory;

import org.openqa.selenium.Capabilities;

import org.openqa.selenium.HasCapabilities;

import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.sample.page.element.provider.ElementProvider;

import com.sample.page.factory.DefaultPageFactory;

import com.sample.page.factory.PageFactory;

//import com.sample.page.listener.ExecutionListener;



/*import org.xframium.spi.PropertyProvider;

import org.xframium.spi.RunDetails;

import org.xframium.spi.driver.DeviceProvider;

import org.xframium.spi.driver.NativeDriverProvider;

import org.xframium.utility.SeleniumSessionManager;*/



// TODO: Auto-generated Javadoc

/**

 * The Class PageManager.

 *

 * @author ageary

 */

public class PageManager

{

	public  static enum DEVICE_TYPE

	{

		MOBILE,TABLET,DESKTOP

	}

	/** The log. */

	private Log log = LogFactory.getLog( PageManager.class );



	/** The Constant EXECUTION_ID. */

	private static final String EXECUTION_ID = "EXECUTION_ID";



	/** The Constant DEVICE_NAME. */

	private static final String DEVICE_NAME = "DEVICE_NAME";



	/** The singleton. */

	private static PageManager singleton = new PageManager();

	private static final ThreadLocal<Page> Page= new ThreadLocal<Page>();


	/** The site name. */

	private String siteName;

	private String[] siteNames;



	private Map<DEVICE_TYPE,String> siteNameMap=new HashMap<DEVICE_TYPE,String>();



	/** The element provider. */

	private ElementProvider elementProvider;



	/** The execution listeners. */

	//private List<ExecutionListener> executionListeners = new ArrayList<ExecutionListener>( 20 );



	/** The execution log. */



	private ThreadLocal<List<ExecutionRecord>> executionLog = new ThreadLocal<List<ExecutionRecord>>();



	/** The local exception. */

	private ThreadLocal<Throwable> localException = new ThreadLocal<Throwable>();



	/** The page factory. */

	private PageFactory pageFactory = new DefaultPageFactory();



	/** The wind tunnel enabled. */

	private boolean windTunnelEnabled = false;



	/** The store images. */

	private boolean storeImages = false;



	/** The image location. */

	private String imageLocation;



	/** The page cache. */

	private ThreadLocal<Map<Class, Page>> pageCache = new ThreadLocal<Map<Class, Page>>();

	private static final ThreadLocal<PageManager> pageManager = new ThreadLocal<PageManager>()

	{

		protected PageManager initialValue() {

			PageManager pManager=new PageManager();

			pManager.setSiteName(singleton.getSiteName());     

			pManager.setSiteNames(singleton.getSiteNames());                       

			pManager.siteNameMap=singleton.siteNameMap;

			pManager.setElementProvider(singleton.getElementProvider());

			//pManager.setAlternateWebDriverSource(singleton.getAlternateWebDriverSource());    

			pManager.outputFormatter=singleton.outputFormatter;

			return pManager;



		}

	};

	public synchronized  static  PageManager getInstance(){

		return pageManager.get();

	}

	/** The alt driver source. */

	//private SeleniumSessionManager altDriverSource = null;



	private String[] tagNames;



	private Properties outputFormatter = new Properties();



	public  String[] getTagNames()

	{

		return tagNames;

	}



	/*public  void addOutputFormatter( String name, String formatter )

	{

		outputFormatter.put( name, formatter );

	}*/



	public  void setTagNames( String tagNames )

	{

		if ( tagNames != null && !tagNames.isEmpty() )

		{

			this.tagNames = tagNames.split( "," );

		}

	}



	/**

	 * Gets the page cache.

	 *

	 * @return the page cache

	 */

	public  Map<Class, Page> getPageCache()

	{

		if ( pageCache.get() == null )

			pageCache.set( new HashMap<Class, Page>( 10 ) );

		return pageCache.get();

	}

	public   void clearPageCache()

	{

		pageCache.set(null);

	}



	/**

	 * Sets the page cache.

	 *

	 * @param pageCache

	 *            the page cache

	 */

	public  void setPageCache( Map<Class, Page> pageCache )

	{

		this.pageCache.set( pageCache );

	}



	/**

	 * Checks if is wind tunnel enabled.

	 *

	 * @return true, if is wind tunnel enabled

	 */

	public  boolean isWindTunnelEnabled()

	{

		return windTunnelEnabled;

	}



	/**

	 * Sets the wind tunnel enabled.

	 *

	 * @param windTunnelEnabled

	 *            the new wind tunnel enabled

	 */

	public  void setWindTunnelEnabled( boolean windTunnelEnabled )

	{

		this.windTunnelEnabled = windTunnelEnabled;

	}



	/**

	 * Checks if is store images.

	 *

	 * @return true, if is store images

	 */

	public  boolean isStoreImages()

	{

		return storeImages;

	}



	/**

	 * Sets the store images.

	 *

	 * @param storeImages

	 *            the new store images

	 */

	public  void setStoreImages( boolean storeImages )

	{

		this.storeImages = storeImages;

	}



	/**

	 * Gets the image location.

	 *

	 * @return the image location

	 */

	public  String getImageLocation()

	{

		return imageLocation;

	}



	/**

	 * Sets the image location.

	 *

	 * @param imageLocation

	 *            the new image location

	 */

	public  void setImageLocation( String imageLocation )

	{

		this.imageLocation = imageLocation;

	}



	/**

	 * Write image.

	 *

	 * @param image

	 *            the image

	 * @param fileName

	 *            the file name

	 * @return the string

	 */

	public  String writeImage( BufferedImage image, String fileName )

	{

		try

		{

			File outputFile = null;

			if ( imageLocation != null )

				//outputFile = new File( new File( imageLocation, RunDetails.instance().getRootFolder() + System.getProperty( "file.separator" ) + "wcag" ), fileName );



				//outputFile = new File( RunDetails.instance().getRootFolder() + System.getProperty( "file.separator" ) + "wcag", fileName );



				outputFile.getParentFile().mkdirs();

			ImageIO.write( image, "png", outputFile );



			return outputFile.getAbsolutePath();

		}

		catch ( Exception e )

		{

			e.printStackTrace();

			return null;

		}



	}



	/**

	 * Instance to the Page Manager singleton.

	 *

	 * @return the page manager

	 */

	public  static PageManager instance()

	{

		return singleton;

	}



	/* public synchronized static PageManager instance()

    {

                  return pageManager.get();

    }*/

	/**

	 * Instantiates a new page manager.

	 */

	private PageManager()

	{

		try

		{

			outputFormatter.load( getClass().getResourceAsStream( "outputFormatter.properties" ) );

		}

		catch ( Exception e )

		{

			e.printStackTrace();

		}



	}



	/**

	 * Sets the page factory.

	 *

	 * @param pageFactory

	 *            the new page factory

	 */

	public  void setPageFactory( PageFactory pageFactory )

	{

		this.pageFactory = pageFactory;

	}



	/**

	 * Gets the site name.

	 *

	 * @return the site name

	 */

	public  String getSiteName()

	{

		return siteName;

	}

	public  String[] getSiteNames()

	{

		return siteNames;

	}



	/**

	 * Sets the site name.

	 *

	 * @param siteName

	 *            the new site name

	 */

	public   void setSiteName( String siteName )

	{

		DEVICE_TYPE[] deviceTypes=new DEVICE_TYPE[]{DEVICE_TYPE.DESKTOP,DEVICE_TYPE.MOBILE,DEVICE_TYPE.TABLET};

		siteNames=siteName.split(",");

		this.siteName = siteNames[0];

		for(int i=0;i<siteNames.length;i++)

			siteNameMap.put(deviceTypes[i],siteNames[i]);

	}

	public   void setSiteNames( String[] siteNames )

	{

		this.siteNames=siteNames;

	}



	public   void setSiteNameByDeviceType( DEVICE_TYPE deviceType )

	{



		this.siteName = siteNameMap.get(deviceType);

	}

	/**

	 * Gets the element provider.

	 *

	 * @return the element provider

	 */

	public  ElementProvider getElementProvider()

	{

		return elementProvider;

	}



	/**

	 * Sets the element provider.

	 *

	 * @param elementProvider

	 *            the new element provider

	 */

	public  void setElementProvider( ElementProvider elementProvider )

	{

		this.elementProvider = elementProvider;

	}



	/**

	 * Register execution listener.

	 *

	 * @param l

	 *            the executionListener

	 */

	/*public  void registerExecutionListener( ExecutionListener l )

	{

		//executionListeners.add( l );

	}*/



	/**

	 * Before execution.

	 *

	 * @param keyName

	 *            the key name

	 */

	/*public  void beforeExecution( String keyName )

	{

		for ( ExecutionListener l : executionListeners )

			l.beforeExecution( keyName );

	}*/



	/**

	 * After execution.

	 *

	 * @param keyName

	 *            the key name

	 * @param runLength

	 *            the run length

	 */

	/**public  void afterExecution( String keyName, long runLength )

	{

		for ( ExecutionListener l : executionListeners )

			l.afterExecution( keyName, runLength );

	}*/



	/**

	 * Creates the page by locating the page from the page factory.

	 *

	 * @param pageInterface

	 *            the page interface

	 * @param webDriver

	 *            the web driver

	 * @return the page

	 */

	public  Page createPage( Class pageInterface, Object webDriver )

	{

		if ( pageFactory == null )

			throw new IllegalArgumentException( "A Service Factory has not been initialized" );



		Page.set(pageFactory.createPage( pageInterface, webDriver ));

		return Page.get();

	}



	/**

	 * Creates the page by locating the page from the page factory.

	 *

	 * @param pageInterface

	 *            the page interface

	 * @param pageFactory

	 *            the page factory

	 * @param webDriver

	 *            the web driver

	 * @return the page

	 */

	public  Page createPage( Class pageInterface, PageFactory pageFactory, Object webDriver )

	{

		if ( pageFactory == null )

			throw new IllegalArgumentException( "This Service Factory has not been initialized" );



		Page.set(pageFactory.createPage( pageInterface, webDriver ));

		return Page.get();

	}



	/**

	 * Adds the execution timing.

	 *

	 * @param executionId

	 *            the execution id

	 * @param deviceName

	 *            the device name

	 * @param methodName

	 *            the method name

	 * @param runLength

	 *            the run length

	 * @param status

	 *            the status

	 * @param description

	 *            the description

	 * @param threshold

	 *            the threshold

	 */

	public  void addExecutionTiming( String executionId, String deviceName, String methodName, long runLength, StepStatus status, String description, int threshold )

	{

		if ( isWindTunnelEnabled() )

		{

			/*PerfectoMobile.instance().windTunnel().addTimerReport( executionId, methodName, (int) runLength, ((status.equals( StepStatus.SUCCESS ) || (status.equals( StepStatus.FAILURE_IGNORED ))) ? Status.success : Status.failure), description,

					threshold );*/

		}

	}



	/**

	 * Adds the execution log.

	 *

	 * @param executionId

	 *            the execution id

	 * @param deviceName

	 *            the device name

	 * @param group

	 *            the group

	 * @param name

	 *            the name

	 * @param type

	 *            the type

	 * @param timestamp

	 *            the timestamp

	 * @param runLength

	 *            the run length

	 * @param status

	 *            the status

	 * @param detail

	 *            the detail

	 * @param t

	 *            the t

	 * @param threshold

	 *            the threshold

	 * @param description

	 *            the description

	 * @param fromCache

	 *            the from cache

	 */

	public  void addExecutionLog( String executionId, String deviceName, String group, String name, String type, long timestamp, long runLength, StepStatus status, String detail, Throwable t, int threshold, String description, boolean fromCache,

			String[] parameterArray )

	{

		List<Object> arrayList = new ArrayList<Object>( 10 );

		arrayList.add( name );

		arrayList.add( group );



		if ( parameterArray != null )

		{

			for ( String param : parameterArray )

				arrayList.add( param );

		}



		String message = null;

		if ( type != null )

		{

			try

			{

				message = outputFormatter.getProperty( type );

				if ( message != null )

				{

					message = String.format( message, arrayList.toArray() );

				}

			}

			catch ( Exception e )

			{

				log.error( "Error formatting message", e );

			}

		}



		//ArtifactManager.instance().notifyArtifactListeners( ArtifactType.EXECUTION_RECORD, new ExecutionRecord( group, name, type, timestamp, runLength, status, detail, t, fromCache, deviceName, message ) );

	}



	public  void addExecutionLog( String executionId, String deviceName, String group, String name, String type, long timestamp, long runLength, StepStatus status, String detail, Throwable t, int threshold, String description, boolean fromCache,

			String[] parameterArray,String screenshot)

	{



		List<Object> arrayList = new ArrayList<Object>( 10 );

		arrayList.add( name );

		arrayList.add( group );



		if ( parameterArray != null )

		{

			for ( String param : parameterArray )

				arrayList.add( param );

		}



		String message = null;

		if ( type != null )

		{

			try

			{

				message = outputFormatter.getProperty( type );

				if ( message != null )

				{

					message = String.format( message, arrayList.toArray() );

				}

			}

			catch ( Exception e )

			{

				log.error( "Error formatting message", e );

			}

		}



		//ArtifactManager.instance().notifyArtifactListeners( ArtifactType.EXECUTION_RECORD, new ExecutionRecord( group, name, type, timestamp, runLength, status, detail, t, fromCache, deviceName, message,screenshot ) );

	}





	/**

	 * Sets the throwable.

	 *

	 * @param t

	 *            the new throwable

	 */

	public void setThrowable( Throwable t )

	{

		localException.set( t );

	}



	/**

	 * Gets the throwable.

	 *

	 * @return the throwable

	 */

	public synchronized Throwable getThrowable()

	{

		return localException.get();

	}



	/**

	 * Gets the execution id.

	 *

	 * @param webDriver

	 *            the web driver

	 * @return the execution id

	 */

	public  String getExecutionId( WebDriver webDriver )

	{

		String executionId = null;



		/*if ( webDriver instanceof PropertyProvider )

		{

			executionId = ((PropertyProvider) webDriver).getProperty( EXECUTION_ID );

		}*/



		if ( executionId == null )

		{

			if ( webDriver instanceof HasCapabilities )

			{

				Capabilities caps = ((HasCapabilities) webDriver).getCapabilities();

				executionId = caps.getCapability( "executionId" ).toString();

			}

		}



		if ( executionId == null )

		{

			/*if ( webDriver instanceof NativeDriverProvider )

			{

				WebDriver nativeDriver = ((NativeDriverProvider) webDriver).getNativeDriver();

				if ( nativeDriver instanceof HasCapabilities )

				{

					Capabilities caps = ((HasCapabilities) webDriver).getCapabilities();

					executionId = caps.getCapability( "executionId" ).toString();

				}

			}*/

		}



		if ( executionId == null )

			log.warn( "No Execution ID could be located" );



		return executionId;

	}



	/**

	 * Gets the execution id.

	 *

	 * @param webDriver

	 *            the web driver

	 * @return the execution id

	 */

	public  String getDeviceOs( WebDriver webDriver )

	{

		String os = null;



		/*if ( webDriver instanceof DeviceProvider )

		{

			os = ((DeviceProvider) webDriver).getDevice().getOs().toUpperCase();

		}*/



		if ( os == null )

		{

			if ( webDriver instanceof HasCapabilities )

			{

				Capabilities caps = ((HasCapabilities) webDriver).getCapabilities();

				os = caps.getCapability( "os" ).toString();

			}

		}



		if ( os == null )

		{

			/*if ( webDriver instanceof NativeDriverProvider )

			{

				WebDriver nativeDriver = ((NativeDriverProvider) webDriver).getNativeDriver();

				if ( nativeDriver instanceof HasCapabilities )

				{

					Capabilities caps = ((HasCapabilities) webDriver).getCapabilities();

					os = caps.getCapability( "os" ).toString();

				}

			}*/

		}



		if ( os == null )

			log.warn( "No OS could be located" );



		return os;

	}



	/**

	 * Gets the device name.

	 *

	 * @param webDriver

	 *            the web driver

	 * @return the device name

	 */

	public   String getDeviceName( WebDriver webDriver )

	{

		String executionId = null;



		/*	if ( webDriver instanceof PropertyProvider )

		{

			executionId = ((PropertyProvider) webDriver).getProperty( DEVICE_NAME );

		}*/



		if ( executionId == null )

		{

			if ( webDriver instanceof HasCapabilities )

			{

				Capabilities caps = ((HasCapabilities) webDriver).getCapabilities();

				executionId = caps.getCapability( "deviceName" ).toString();

			}

		}



		if ( executionId == null )

		{

			//if ( webDriver instanceof NativeDriverProvider )

			{

				//WebDriver nativeDriver = ((NativeDriverProvider) webDriver).getNativeDriver();

				/*if ( nativeDriver instanceof HasCapabilities )

				{

					Capabilities caps = ((HasCapabilities) webDriver).getCapabilities();

					executionId = caps.getCapability( "deviceName" ).toString();

				}*/

			}

		}



		if ( executionId == null )

			log.warn( "No Execution ID could be located" );



		return executionId;

	}



	/**

	 * Gets the alternate web driver source.

	 *

	 * @return the alternate web driver source

	 */

	/*public  SeleniumSessionManager getAlternateWebDriverSource()

	{

		return altDriverSource;

	}*/



	/**

	 * Sets the alternate web driver source.

	 *

	 * @param src

	 *            the new alternate web driver source

	 */

	/*public  void setAlternateWebDriverSource( SeleniumSessionManager src )

	{

		altDriverSource = src;

	}*/



}