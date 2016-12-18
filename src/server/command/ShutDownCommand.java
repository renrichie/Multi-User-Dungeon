package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Brian Lovelace
 * File:     ShutDownCommand.java
 * Purpose:  The ShutDownCommand object is used to tell the server to shut down.
 */

public class ShutDownCommand extends Command
{
	public final String password;
	
	public ShutDownCommand(String password)
	{
		super(null);
		this.password = password;
		
	}

	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model)
	{
		return null;
	}

}
