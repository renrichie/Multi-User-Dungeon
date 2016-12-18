package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Richie Ren
 * File:     RespawnCommand.java
 * Purpose:  The RespawnCommand object is used to tell the server that the specified user is being respawned.
 */

public class RespawnCommand extends Command {


	public RespawnCommand(String username, int uv) {
		super(username, uv);
	}

	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model) {
		return model.respawnPlayer(username, this.USER_VERIFICATION);
	}
}
