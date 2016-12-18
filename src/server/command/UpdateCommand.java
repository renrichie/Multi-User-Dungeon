package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Brian Lovelace
 * File:     UpdateCommand.java
 * Purpose:  The UpdateCommand object is used to update the client.
 */

public class UpdateCommand extends Command
{

	public UpdateCommand(String username, int uv)
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
		return model.saveState();
	}
	
	

}
