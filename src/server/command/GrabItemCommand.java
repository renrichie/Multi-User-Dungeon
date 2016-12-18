package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Richie Ren
 * File:     GrabItemCommand.java
 * Purpose:  The GrabItemCommand object is used to tell the server that the specified user grabbed the specified item.
 */

public class GrabItemCommand extends Command {

	private String itemName; 
	
	public GrabItemCommand(String username, int uv, String itemName) {
		super(username, uv);
		this.itemName = itemName;
	}

	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model) {
		
		return model.grabItem(username, this.USER_VERIFICATION, itemName);
	}
}

