package com.sample.device.factory;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Node;

public class CachedWebElement implements WebElement

{
	
	/** The element node. */
	private Node elementNode;
	
	/** The web driver. */
	private WebDriver webDriver;
	
	/** The by. */
	private By by;
	
	private DeviceWebDriver deviceDriver;
	
	/**
	 * Instantiates a new cached web element.
	 *
	 * @param webDriver the web driver
	 * @param by the by
	 * @param elementNode the element node
	 */
	public CachedWebElement( DeviceWebDriver deviceDriver, WebDriver webDriver, By by, Node elementNode )
	{
		this.elementNode = elementNode;
		this.webDriver = webDriver;
		this.deviceDriver = deviceDriver;
		this.by = by;
	}
	
	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#click()
	 */
	@Override
	public void click()
	{
		webDriver.findElement( by ).click();
		deviceDriver.clearCache();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#submit()
	 */
	@Override
	public void submit()
	{
		webDriver.findElement( by ).submit();
		deviceDriver.clearCache();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#sendKeys(java.lang.CharSequence[])
	 */
	@Override
	public void sendKeys( CharSequence... keysToSend )
	{
		webDriver.findElement( by ).sendKeys( keysToSend );
		
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#clear()
	 */
	@Override
	public void clear()
	{
		webDriver.findElement( by ).clear();
		
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getTagName()
	 */
	@Override
	public String getTagName()
	{
		return elementNode.getNodeName();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getAttribute(java.lang.String)
	 */
	@Override
	public String getAttribute( String name )
	{
		Node attributeNode = elementNode.getAttributes().getNamedItem( name );
		if ( attributeNode != null )
			return attributeNode.getNodeValue();
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#isSelected()
	 */
	@Override
	public boolean isSelected()
	{
		return webDriver.findElement( by ).isSelected();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#isEnabled()
	 */
	@Override
	public boolean isEnabled()
	{
		try
		{
			
			return Boolean.parseBoolean( getAttribute( "enabled" ) );
		}
		catch( Exception e )
		{
			return webDriver.findElement( by ).isEnabled();
		}
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getText()
	 */
	@Override
	public String getText()
	{
		return elementNode.getTextContent();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#findElements(org.openqa.selenium.By)
	 */
	@Override
	public List<WebElement> findElements( By by )
	{
		return webDriver.findElement( this.by ).findElements( by );
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#findElement(org.openqa.selenium.By)
	 */
	@Override
	public WebElement findElement( By by )
	{
		return webDriver.findElement( this.by ).findElement( by );
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#isDisplayed()
	 */
	@Override
	public boolean isDisplayed()
	{
		return webDriver.findElement( by ).isDisplayed();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getLocation()
	 */
	@Override
	public Point getLocation()
	{
		try
		{
			String x = null, y = null;
			
			x=getAttribute( "x" );
			y=getAttribute( "y" );
			if ( x != null && y != null )
			    return new Point( Integer.parseInt( x ), Integer.parseInt( y ) );
			else
			    return new Point( 0, 0 );
		}
		catch( Exception e )
		{
			return webDriver.findElement( by ).getLocation();
		}
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getSize()
	 */
	@Override
	public Dimension getSize()
	{
		try
		{
			String height = null, width = null;
			
			height=getAttribute( "height" );
			width=getAttribute( "width" );
			if ( height != null && width != null )
			    return new Dimension( Integer.parseInt( width ), Integer.parseInt( height ) );
			else
			    return new Dimension( 0, 0 );
		}
		catch( Exception e )
		{
			return webDriver.findElement( by ).getSize();
		}
		
		
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebElement#getCssValue(java.lang.String)
	 */
	@Override
	public String getCssValue( String propertyName )
	{
		return webDriver.findElement( by ).getCssValue( propertyName );
	}

    public <X> X getScreenshotAs( OutputType<X> arg0 ) throws WebDriverException
    {
        return null;
    }

    public Rectangle getRect()
    {
        return new Rectangle( getLocation(), getSize() );
    }

}