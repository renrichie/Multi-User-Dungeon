package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Richie Ren
 * File:     GiveCommand.java
 * Purpose:  The GiveCommand object is used to tell the server that the specified user is trying to give the item to the target.
 */

public class GiveCommand extends Command {

	private String itemName;
	private String target;

	public GiveCommand(String username, int uv, String itemName, String target) {
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
		return model.giveItem(username, this.USER_VERIFICATION, itemName, target);
	}
}
