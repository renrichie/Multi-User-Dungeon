package server.command;

import model.MoveDirection;
import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Chris Derck
 * File:     MoveCommand.java
 * Purpose:  The MoveCommand object is used to tell the server that the specified user moved in the specified direction.
 */

public class MoveCommand extends Command{
	private MoveDirection dir;
	
	public MoveCommand(String username, int uv, MoveDirection dir) {
		super(username, uv);
		this.dir = dir;
	}

	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model) { 		
		return model.movePlayer(username, this.USER_VERIFICATION, dir);
	}
}
