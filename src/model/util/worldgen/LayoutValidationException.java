package model.util.worldgen;

/**
 * Author:   Brian Lovelace
 * File:     LayoutValidationException.java
 * Purpose:  The LayoutValidationException class creates an exception if the data in the layout is incorrect.
 */

public class LayoutValidationException extends RuntimeException
{
	public LayoutValidationException(String msg)
	{
		super(msg);
	}
}
