package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Richie Ren
 * File:     UseItemCommand.java
 * Purpose:  The UseItemCommand object is used to tell the server that the specified user used the specified item.
 */

public class UseItemCommand extends Command {

	private String itemName;
	
	public UseItemCommand(String username, int uv, String itemName) {
		super(username, uv);
		this.itemName = itemName;
	}

	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model) {
		return model.useItem(username, this.USER_VERIFICATION, itemName);
	}
}
