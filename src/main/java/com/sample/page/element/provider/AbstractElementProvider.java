package com.sample.page.element.provider;



import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sample.page.ElementDescriptor;
import com.sample.page.element.Element;





// TODO: Auto-generated Javadoc

/**

 * The Class AbstractElementProvider.

 */

public abstract class AbstractElementProvider implements ElementProvider

{

	private static XPathFactory xPathFactory = XPathFactory.newInstance();

	/** The log. */

	protected Log log = LogFactory.getLog(ElementProvider.class);




	@Override

	public Element getElement( ElementDescriptor elementDescriptor )

	{

		return _getElement( elementDescriptor );

	}



	private boolean initialized = false;







	public boolean isInitialized()

	{

		return initialized;

	}



	public void setInitialized( boolean initialized )

	{

		this.initialized = initialized;

	}



	/**

	 * _get element.

	 *

	 * @param elementDescriptor the element descriptor

	 * @return the element

	 */

	protected abstract Element _getElement( ElementDescriptor elementDescriptor );



	protected boolean validateElement( ElementDescriptor elementDescriptor, Element currentElement ) throws Exception

	{

		try

		{

			switch ( currentElement.getBy() )

			{

			case CLASS:

			case ID:

			case LINK_TEXT:

			case NAME:

			case TAG_NAME:

			case V_TEXT:

				break;



			case CSS:

			case PROP:   

			case HTML:

			case V_IMAGE:

				break;



			case XPATH:

				xPathFactory.newXPath().compile( currentElement.getKey().replace( "{", "" ).replace( "}", "" ) );



			}

		}

		catch( Exception e )

		{

			log.fatal( "Could not process page element identified by [" + elementDescriptor.toString() + "] as [" + currentElement.getKey() + "]" );

			return false;

		}



		return true;

	}



}