package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Richie Ren
 * File:     TakeCommand.java
 * Purpose:  The TakeCommand object is used to tell the server that the specified user is trying to take the item from the target.
 */

public class TakeCommand extends Command {

	private String itemName;
	private String target;

	public TakeCommand(String username, int uv, String itemName, String target) {
		super(username, uv);
		this.itemName = itemName;
		this.target = target;
	}

	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model) {
		return model.takeItem(username, this.USER_VERIFICATION, itemName, target);
	}
}
