package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Brian Lovelace
 * File:     BadCommand.java
 * Purpose:  The BadCommand object is used to tell the server that the command was a bad one.
 */

public class BadCommand extends Command
{
	private Object badData;
	
	
	public BadCommand(Object data)
	{
		super("testing");
		this.badData = data;
	}
	
	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model)
	{
		return new BadCommandResponse(this);
	}
		
	/**
	 * Method:  getBadData() 
	 * Purpose: It returns an Object containing the bad data.
	 */
	
	public Object getBadData()
	{
		return badData;
	}
}
