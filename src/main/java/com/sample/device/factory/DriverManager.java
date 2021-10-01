package com.sample.device.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sample.Exceptions.FatalFrameWorkException;
import com.sample.enums.ERRORDETAILS;

public class DriverManager {

	/** The singleton. */
	private static DriverManager singleton = new DriverManager();
	private   Boolean isSauceLabIntegartion= false;
	private SauceLabIntegration sauceLabIntegration;


	/**
	 * Instance.
	 *
	 * @return the driver manager
	 */
	public static DriverManager instance()
	{
		return singleton;
	}

	/**
	 * Instantiates a new driver manager.
	 */
	private DriverManager()
	{

	}
	
	
	public SauceLabIntegration getSauceLabIntegration() {
		return sauceLabIntegration;
	}

	public void setSauceLabIntegration(SauceLabIntegration sauceLabIntegration) {
		this.sauceLabIntegration = sauceLabIntegration;
	}

	public  Boolean getIsSauceLabIntegartion() {
		return isSauceLabIntegartion;
	}

	public  void setIsSauceLabIntegartion(Boolean isSauceLabIntegartion) {
		this.isSauceLabIntegartion = isSauceLabIntegartion;
	}
	
	private Log log = LogFactory.getLog( DriverManager.class );

	public synchronized DriverFactory getDriverFactory( String driverName ) throws FatalFrameWorkException
	{
		if ( log.isDebugEnabled() )
			log.debug( "Getting Driver Factory for " + driverName );
		
		//DriverFactory driverFactory = driverMap.get( driverName );
		DriverFactory driverFactory = null;
		
		if ( driverName != null )
		{
			String className = DriverFactory.class.getPackage().getName() + ".spi." + driverName + "DriverFactory";
			
			if ( log.isInfoEnabled() )
				log.info( "Creating Driver Factory as " + className );
			
			try
			{
				driverFactory = (DriverFactory)Class.forName( className ).newInstance();
			}
			catch( Exception e )
			{
				log.fatal( "Could not create DriverFactory for " + className, e );
			}
		}
		else {
			throw new FatalFrameWorkException(ERRORDETAILS.DRIVERERROR);
		}
		
		return driverFactory;
	}

	@Override
	public String toString() {
		return "DriverManager [isSauceLabIntegartion=" + isSauceLabIntegartion + ", sauceLabIntegration="
				+ sauceLabIntegration + ", log=" + log + "]";
	}
	
	
	
	
	
	
}
