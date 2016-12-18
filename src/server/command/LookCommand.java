package server.command;

import java.awt.Point;

import model.Player;
import model.Room;
import server.model.ServerModelIntf;
import server.response.ServerResponse;

/**
 * Author:   Chris Derck
 * File:     LookCommand.java
 * Purpose:  The LookCommand object is used to tell the server that the specified user look at their current room.
 *           This is handled locally.
 */

public class LookCommand extends Command{
	private Room room;
	private Point roomCoord;
	private int x, y;
	private Player player;
	
	public LookCommand(String username, int uv, Player player) {
		super(username, uv);
		this.player = player;
		this.roomCoord = this.player.getLocation().getRoom();
		this.x = roomCoord.x;
		this.y = roomCoord.y;
	}

	/**
	 * Method:  executeCommand(ServerModelIntf model) 
	 * Purpose: It executes the command serverside.
	 */
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model) {
		return null;
	}
}

