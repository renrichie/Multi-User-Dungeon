package server.response;

import server.command.Command;

/**
 * Author:   Brian Lovelace
 * File:     TestGlobalResponse.java
 * Purpose:  The TestGlobalResponse class creates a TestGlobalResponse object that tests out responses to global commands.
 */

public class TestGlobalResponse extends ServerResponse
{

	public TestGlobalResponse(Command com)
	{
		super(com, "THIS IS A GLOBAL TEST", true);

	}

	/**
	 * Method:  toString() 
	 * Purpose: It returns the String indicating the result of the command.
	 */
	
	@Override
	public String toString()
	{
		return this.MESSAGE;
	}

}
