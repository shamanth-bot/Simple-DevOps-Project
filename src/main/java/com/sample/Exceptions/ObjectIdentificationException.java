package com.sample.Exceptions;

 

import org.openqa.selenium.By;

import com.sample.page.BY;


 

public class ObjectIdentificationException extends XFramiumException

{

    /**

     *

     */

    private static final long serialVersionUID = 479258953143046488L;

    private BY xBy;

    private By by;

   

    public ObjectIdentificationException( BY xBy, By by )

    {

        super( ExceptionType.SCRIPT );

        this.xBy = xBy;

        this.by = by;

    }

    public BY getxBy()

    {

        return xBy;

    }

    public By getBy()

    {

        return by;

    }

   

    @Override

    public String toString()

    {

        return "Could not locate " + by.toString();

    }

   

    @Override

    public String getMessage()

    {

        return "Could not locate " + by.toString();

    }

   

}

 

 