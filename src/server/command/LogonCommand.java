package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Brian Lovelace
 * File:     LogonCommand.java
 * Purpose:  The LogonCommand object is used to tell the server that the specified user has logged on.
 */

public class LogonCommand extends Command
{
	private String password;
	
	public LogonCommand(String username, String password)
	{
		super(username);
		this.password = password;
	}

	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model)
	{
		return model.clientLogon(username, password);
	}
	
	

}
