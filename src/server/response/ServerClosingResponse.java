package server.response;

/**
 * Author:   Brian Lovelace
 * File:     ServerClosingResponse.java
 * Purpose:  The ServerClosingResponse class creates a ServerClosingResponse object that represents a response to the server being shutdown.
 */

public class ServerClosingResponse extends ServerResponse
{

	public ServerClosingResponse(String msg)
	{
		super(msg, true);
	}
	
	public ServerClosingResponse()
	{
		super("The server has been shut down.",
		      true);
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
