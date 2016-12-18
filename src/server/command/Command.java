package server.command;

import java.io.Serializable;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Used to encapsulate actions from the players and possibly monsters
 * @author Brian Lovelace
 *
 */

public abstract class Command implements Serializable
{
	public final int USER_VERIFICATION;
	
	protected String username;
	
	public Command (String username)
	{
		this(username, -1);
	}
	
	public Command(String username, int uv)
	{
		this.username = username;
		this.USER_VERIFICATION = uv;
	}
	
	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	public abstract ServerResponse executeCommand(ServerModelIntf model);
	
	/**
	 * Method:  getUsername() 
	 * Purpose: It returns the username attached to the command.
	 */
	
	public String getUsername()
	{
		return this.username;
	}
}
