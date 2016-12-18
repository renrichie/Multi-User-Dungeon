package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Chris Derck
 * File:     AttackCommand.java
 * Purpose:  The AttackCommand object is used to tell the server that the specified user attacked the specified mob.
 */

public class AttackCommand extends Command {

	private String mobName; 
	
	public AttackCommand(String username, int uv, String mob) {
		super(username, uv);
		this.mobName = mob;
	}

	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model) {
		return model.attackMob(username, this.USER_VERIFICATION, mobName);
	}
}

