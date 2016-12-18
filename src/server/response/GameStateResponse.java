package server.response;

import model.MudModelIntf;

/**
 * Author:   Brian Lovelace
 * File:     GameStateResponse.java
 * Purpose:  The GameStateResponse class creates a GameStateResponse object that represents a response to the game state being updated.
 */

public class GameStateResponse extends ServerResponse {
	
	public boolean success;
	public MudModelIntf updatedmodel;
	
	public GameStateResponse(MudModelIntf model, boolean success, boolean global)
	{
		super("Game State Reponse", global);
		this.success = success;
		this.updatedmodel = model;
	}
}
