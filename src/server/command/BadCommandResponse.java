package server.command;

import server.response.ServerResponse;

/**
 * Author:   Brian Lovelace
 * File:     BadCommandResponse.java
 * Purpose:  The BadCommandResponse object is used to tell the server that the command was bad.
 */

public class BadCommandResponse extends ServerResponse
{
	private final static String msg = "Server recieved a bad command"; 
	public BadCommandResponse(BadCommand com)
	{
		super(com, msg);
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns a String stating that the command was bad.
	 */
	
	@Override
	public String toString()
	{
		return this.MESSAGE;
	}
}
