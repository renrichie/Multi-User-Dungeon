package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Richie Ren
 * File:     InteractCommand.java
 * Purpose:  The InteractCommand object is used to tell the server that the specified user has interacted with a door.
 */

public class InteractCommand extends Command {

	private String target;
	
	public InteractCommand(String username, int uv, String entity) {
		super(username, uv);
		this.target = entity;
	}

	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model) { 		
		return model.interact(username, this.USER_VERIFICATION, target);
	}
}
