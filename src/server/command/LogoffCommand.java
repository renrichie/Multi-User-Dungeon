package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Brian Lovelace
 * File:     LogoffCommand.java
 * Purpose:  The LogoffCommand object is used to tell the server that the specified user has logged off.
 */

public class LogoffCommand extends Command
{	
	
	public LogoffCommand(String username, int uv)
	{
		super(username, uv);
	}

	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model)
	{
		return model.clientLogoff(username, this.USER_VERIFICATION);
	}

}
