package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Brian Lovelace
 * File:     SpeakCommandCommand.java
 * Purpose:  The SpeakCommand object is used to tell the server that the specified user has said something in chat.
 */

public class SpeakCommand extends Command
{

	public final String said, target;
	
	public SpeakCommand(String username, int uv, String said, String  target)
	{
		super(username, uv);
		this.said = said;
		this.target = target;
	}

	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model)
	{
		return model.playerChat(this.username,
		                        this.USER_VERIFICATION, 
		                        this.said,
		                        this.target);
	}

}
