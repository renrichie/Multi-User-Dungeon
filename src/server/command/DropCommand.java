package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Richie Ren
 * File:     DropCommand.java
 * Purpose:  The DropCommand object is used to tell the server that the specified user dropped the specified item.
 */

public class DropCommand extends Command {

	private String itemName;

	public DropCommand(String username, int uv, String itemName) {
		super(username, uv);
		this.itemName = itemName;
	}

	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model) {
		return model.dropItem(username, this.USER_VERIFICATION, itemName);
	}
}
