package com.sample.page.element;



import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.sample.Exceptions.ObjectIdentificationException;
import com.sample.Exceptions.ScriptConfigurationException;
import com.sample.Exceptions.XFramiumException;
import com.sample.ng.ReportingManager;
import com.sample.page.BY;
import com.sample.page.PageManager;
import com.sample.page.StepStatus;





// TODO: Auto-generated Javadoc

/**

 * The Class AbstractElement.

 */

public abstract class AbstractElement implements Element

{





	/** The log. */

	protected Log log = LogFactory.getLog( Element.class );

	private boolean cacheNative = false;



	public boolean isCacheNative()

	{

		return cacheNative;

	}



	public void setCacheNative( boolean cacheNative )

	{

		this.cacheNative = cacheNative;

	}



	/**

	 * _get native.

	 *

	 * @return the object

	 */

	protected abstract Object _getNative();



	protected abstract Dimension _getSize();

	protected abstract Point _getAt();



	/**

	 * _set value.

	 *

	 * @param currentValue the current value

	 */

	protected abstract void _setValue( String currentValue, SetMethod setMethod );



	/**

	 * _get value.

	 *

	 * @return the string

	 */

	protected abstract String _getValue();



	/**

	 * _get style.

	 *

	 * @param styleProperty the style property

	 * @return the string

	 */

	protected abstract String _getStyle(String styleProperty);



	/**

	 * _get attribute.

	 *

	 * @param attributeName the attribute name

	 * @return the string

	 */

	protected abstract String _getAttribute( String attributeName);



	/**

	 * _is visible.

	 *

	 * @return true, if successful

	 */

	protected abstract boolean _isVisible();



	/**

	 * _is present.

	 *

	 * @return true, if successful

	 */

	protected abstract boolean _isPresent();



	/**

	 * _is present.

	 *

	 * @param res the res

	 * @return true, if successful

	 */

	



	/**

	 * _move to.

	 *

	 * @return true, if successful

	 */

	protected abstract boolean _moveTo();



	/**

	 * _press.

	 *

	 * @return true, if successful

	 */

	protected abstract boolean _press();



	/**

	 * _release.

	 *

	 * @return true, if successful

	 */

	protected abstract boolean _release();



	protected abstract boolean _isFocused();

	/**

	 * _wait for visible.

	 *

	 * @param timeOut the time out

	 * @param timeUnit the time unit

	 * @param waitType the wait type

	 * @param value the value

	 * @return true, if successful

	 */

	protected abstract boolean _waitFor( long timeOut, TimeUnit timeUnit, WAIT_FOR waitType, String value );





	/**

	 * _click.

	 */

	protected abstract void _click();



	/**

	 * _get all.

	 *

	 * @return the element[]

	 */

	protected abstract Element[] _getAll();



	/** The by. */

	private BY by;



	/** The element key. */

	private String elementKey;



	/** The timed. */

	private boolean timed;



	/** The element name. */

	private String elementName;



	/** The page name. */

	private String pageName;



	/** The context element. */

	private String contextElement;



	/** The context. */

	private Element context;



	/** The token map. */

	private Map<String,String> tokenMap = null;



	/** The tokens applied. */

	private boolean tokensApplied = false;



	/**

	 * Instantiates a new abstract element.

	 *

	 * @param by the by

	 * @param elementKey the element key

	 * @param elementName the element name

	 * @param pageName the page name

	 * @param contextElement the context element

	 */

	protected AbstractElement( BY by, String elementKey, String elementName, String pageName, String contextElement )

	{

		this.by = by;

		this.elementKey = elementKey;

		this.elementName = elementName;

		this.pageName = pageName;

		this.contextElement = contextElement;

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#addToken(java.lang.String, java.lang.String)

	 */

	public Element addToken( String tokenName, String tokenValue )

	{

		tokensApplied = false;

		if ( tokenMap == null )

			tokenMap = new HashMap<String,String>( 10 );



		tokenMap.put( tokenName, tokenValue );



		return this;

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#addToken(java.lang.String)

	 */

	public Element addToken( String tokenPairValue )

	{

		tokensApplied = false;

		if ( tokenMap == null )

			tokenMap = new HashMap<String,String>( 10 );



		String[] tokenPair = tokenPairValue.split( "=" );

		if ( tokenPair.length != 2 )

			throw new ScriptConfigurationException( "You must specify a token in the format of name=value" );



		tokenMap.put( tokenPair[ 0 ].trim(), tokenPair[ 1 ].trim() );



		return this;

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#getContext()

	 */

	public Element getContext()

	{

		return context;

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#setContext(com.perfectoMobile.page.element.Element)

	 */

	public void setContext( Element context )

	{

		this.context = context;

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#getAll()

	 */

	@Override

	public Element[] getAll()

	{

		// TODO Auto-generated method stub

		return _getAll();

	}



	/**

	 * Gets the by.

	 *

	 * @return the by

	 */

	public BY getBy()

	{

		return by;

	}



	/**

	 * Gets the key.

	 *

	 * @return the key

	 */

	public String getKey()

	{

		if ( !tokensApplied )

		{

			if ( tokenMap != null && !tokenMap.isEmpty() )

			{

				String newKey = elementKey;

				for ( String tokenName : tokenMap.keySet() )

				{

					if ( tokenMap.get( tokenName ) != null)

						newKey = newKey.replace( "{" + tokenName + "}", tokenMap.get( tokenName ) );

					else

						log.warn( "Token [" + tokenName + " was null" );

				}

				elementKey = newKey;

			}



			tokensApplied = true;

		}



		return elementKey;

	}



	/**

	 * Gets the element name.

	 *

	 * @return the element name

	 */

	protected String getElementName()

	{

		return elementName;

	}



	/**

	 * Gets the page name.

	 *

	 * @return the page name

	 */

	protected String getPageName()

	{

		return pageName;

	}



	/**

	 * Gets the context element.

	 *

	 * @return the context element

	 */

	protected String getContextElement()

	{

		return contextElement;

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#getValue()

	 */

	@Override

	public String getValue()

	{

		String returnValue = null;

		long startTime = System.currentTimeMillis();

		boolean success = false;

		try

		{

			returnValue = _getValue();

			success = true;

		}

		catch( Exception e )

		{

			if ( e instanceof XFramiumException )

				throw e;

			else

				throw new ScriptConfigurationException( e.getMessage() );

		}

		finally

		{

			//if ( timed )

				//PageManager.instance().addExecutionTiming( getExecutionId(), getDeviceName(), pageName + "." + elementName + ".getValue()", System.currentTimeMillis() - startTime, success ? StepStatus.SUCCESS : StepStatus.FAILURE, "", 0 );

		}

		return returnValue;

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#getValue()

	 */

	@Override

	public String getStyle(String styleProperty)

	{

		String returnValue = null;

		long startTime = System.currentTimeMillis();

		boolean success = false;

		try

		{

			returnValue = _getStyle( styleProperty );

			success = true;

		}

		catch( Exception e )

		{

			if ( e instanceof XFramiumException )

				throw e;

			else

				throw new ScriptConfigurationException( e.getMessage() );

		}

		finally

		{

			//if ( timed )

				//PageManager.instance().addExecutionTiming( getExecutionId(), getDeviceName(), pageName + "." + elementName + ".getStyle()", System.currentTimeMillis() - startTime, success ? StepStatus.SUCCESS : StepStatus.FAILURE, "", 0 );

		}

		return returnValue;

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#isVisible()

	 */

	@Override

	public boolean isVisible()

	{

		long startTime = System.currentTimeMillis();

		boolean returnValue = false;

		boolean success = false;

		try

		{

			returnValue = _isVisible();

			success = true;

		}

		catch( Exception e )

		{

			if ( e instanceof XFramiumException )

				throw e;

			else

				throw new ScriptConfigurationException( e.getMessage() );

		}

		finally

		{

			//if ( timed )

				//PageManager.instance().addExecutionTiming( getExecutionId(), getDeviceName(), pageName + "." + elementName + ".isVisible()", System.currentTimeMillis() - startTime, success ? StepStatus.SUCCESS : StepStatus.FAILURE, "", 0 );

		}

		return returnValue;

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#isPresent()

	 */

	@Override

	public boolean isPresent()

	{

		long startTime = System.currentTimeMillis();

		boolean returnValue = false;

		boolean success = false;

		try

		{

			returnValue = _isPresent();

			success = true;
			   ReportingManager.instance().getExtenttest().log(Status.INFO, MarkupHelper.createLabel("Element on page "+"<span style=\"color:yellow\">"+pageName+"</span>"+" with element name "+"<span style=\"color:green\">"+elementName+"</span>"+" exsists", ExtentColor.WHITE));

		}

		catch( Exception e )

		{

			if(e instanceof ObjectIdentificationException)

				return false;

			else if ( e instanceof XFramiumException )

				throw e;



			else

				throw new ScriptConfigurationException( e.getMessage() );

		}

		finally

		{

			//if ( timed )

				//PageManager.instance().addExecutionTiming( getExecutionId(), getDeviceName(), pageName + "." + elementName + ".isPresent()", System.currentTimeMillis() - startTime, success ? StepStatus.SUCCESS : StepStatus.FAILURE, "", 0 );

		}

		return returnValue;

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#waitForVisible(long, java.util.concurrent.TimeUnit)

	 */

	@Override

	public boolean waitFor( long timeOut, TimeUnit timeUnit, WAIT_FOR waitType, String value )

	{

		long startTime = System.currentTimeMillis();

		boolean returnValue = false;

		boolean success = false;

		try

		{



			returnValue = _waitFor( timeOut, timeUnit, waitType, value );

			success = true;



		}

		finally

		{

			//if ( timed )

				//PageManager.instance().addExecutionTiming( getExecutionId(), getDeviceName(), pageName + "." + elementName + ".waitForVisible()", System.currentTimeMillis() - startTime, success ? StepStatus.SUCCESS : StepStatus.FAILURE, "", 0 );

		}

		return returnValue;

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#waitForVisible(long, java.util.concurrent.TimeUnit)

	 */

	@Override

	public boolean waitForVisible( long timeOut, TimeUnit timeUnit )

	{

		return waitFor( timeOut, timeUnit, WAIT_FOR.VISIBLE, null );

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#waitForPresent(long, java.util.concurrent.TimeUnit)

	 */

	@Override

	public boolean waitForPresent( long timeOut, TimeUnit timeUnit )

	{

		return waitFor( timeOut, timeUnit, WAIT_FOR.PRESENT, null );

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#getNative()

	 */

	@Override

	public Object getNative()

	{

		return _getNative( );

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#getAttribute(java.lang.String)

	 */

	@Override

	public String getAttribute( String attributeName )

	{

		long startTime = System.currentTimeMillis();

		String returnValue;

		boolean success = false;

		try

		{

			returnValue = _getAttribute( attributeName );

			success = true;

			//            PageManager.instance().addExecutionLog( getExecutionId(), getDeviceName(), pageName, elementName, "attribute", System.currentTimeMillis(), System.currentTimeMillis() - startTime, success ? StepStatus.SUCCESS : StepStatus.FAILURE, getKey(), null, 0, "", false, new String[] { attributeName, returnValue } );

		}

		catch( Exception e )

		{

			if ( e instanceof XFramiumException )

				throw e;

			else

				throw new ScriptConfigurationException( e.getMessage() );

		}

		finally

		{

			if ( timed )

				PageManager.instance().addExecutionTiming( getExecutionId(), getDeviceName(), pageName + "." + elementName + ".getAttribute()", System.currentTimeMillis() - startTime, success ? StepStatus.SUCCESS : StepStatus.FAILURE, "", 0 );

		}

		return returnValue;

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#getImage(com.morelandLabs.integrations.perfectoMobile.rest.services.Imaging.Resolution)

	 */

	@Override

	


	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#setValue(java.lang.String)

	 */


	public void setValue( String currentValue )

	{

		setValue( currentValue, SetMethod.DEFAULT );

	}



	@Override

	public void setValue( String currentValue, SetMethod setMethod )

	{

		long startTime = System.currentTimeMillis();

		boolean success = false;

		try

		{

			_setValue( currentValue, setMethod );

			success = true;
			   ReportingManager.instance().getExtenttest().log(Status.INFO, MarkupHelper.createLabel("The  element "+"<span style=\"color:yellow\">"+elementName+"</span>"+" is set to "+"<span style=\"color:green\"> "+currentValue+"</span>", ExtentColor.WHITE));


			//PageManager.instance().addExecutionLog( getExecutionId(), getDeviceName(), pageName, elementName, "setValue", System.currentTimeMillis(), System.currentTimeMillis() - startTime, success ? StepStatus.SUCCESS : StepStatus.FAILURE, getKey(), null, 0, "", false, new String[] { currentValue } );

		}

		catch( Exception e )

		{
			   ReportingManager.instance().getExtenttest().log(Status.ERROR, MarkupHelper.createLabel("failed to Set value for element"+"<span style=\"color:yellow\">"+elementName+"<span style=\"color:red\">"+currentValue+"</span>", ExtentColor.RED));

			if ( e instanceof XFramiumException )

				throw e;

			else

				throw new ScriptConfigurationException( e.getMessage() );

		}

		finally

		{


			//if ( timed )

				//PageManager.instance().addExecutionTiming( getExecutionId(), getDeviceName(), pageName + "." + elementName + ".setValue(" + currentValue + ")", System.currentTimeMillis() - startTime, success ? StepStatus.SUCCESS : StepStatus.FAILURE, "", 0 );

		}



	}







	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#click()

	 */

	@Override

	public void click()

	{

		long startTime = System.currentTimeMillis();

		boolean success = false;

		try

		{

			_click();

			success = true;
			   ReportingManager.instance().getExtenttest().log(Status.INFO, MarkupHelper.createLabel("clicked on the element "+"<span style=\"color:yellow\">"+pageName+"</span>"+" on Page "+"<span style=\"color:green\">"+elementName+"</span>", ExtentColor.WHITE));

			//PageManager.instance().addExecutionLog( getExecutionId(), getDeviceName(), pageName, elementName, "click", System.currentTimeMillis(), System.currentTimeMillis() - startTime, success ? StepStatus.SUCCESS : StepStatus.FAILURE, getKey(), null, 0, "", false, null );

		}

		catch( Exception e )

		{

			if ( e instanceof XFramiumException )

				throw e;

			else

				throw new ScriptConfigurationException( e.getMessage() );

		}

		finally

		{                                             

			//if ( timed )

				//PageManager.instance().addExecutionTiming( getExecutionId(), getDeviceName(), pageName + "." + elementName + ".click()", System.currentTimeMillis() - startTime, success ? StepStatus.SUCCESS : StepStatus.FAILURE, "", 0 );

		}



	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#setTimed(boolean)

	 */

	@Override

	public void setTimed( boolean timed )

	{

		this.timed = timed;



	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#isTimed()

	 */

	@Override

	public boolean isTimed()

	{

		return timed;

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#moveTo()

	 */

	@Override

	public boolean moveTo()

	{

		return _moveTo();

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#press()

	 */

	@Override

	public boolean press()

	{

		return _press();

	}



	/* (non-Javadoc)

	 * @see com.perfectoMobile.page.element.Element#release()

	 */

	@Override

	public boolean release()

	{

		return _release();

	}



	@Override

	public boolean isFocused()

	{

		return _isFocused();

	}



	@Override

	public Dimension getSize()

	{

		return _getSize();

	}



	@Override

	public Point getAt()

	{

		return _getAt();

	}





}



