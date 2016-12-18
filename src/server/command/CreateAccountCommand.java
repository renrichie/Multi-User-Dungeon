package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Brian Lovelace
 * File:     CreateAccountCommand.java
 * Purpose:  The CreateAccountCommand object is used to tell the server to create an account with the specified details.
 */

public class CreateAccountCommand extends Command
{
	private String password;
	
	public CreateAccountCommand(String username, String password)
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
		return model.addAccount(this.username, password);
	}

}
