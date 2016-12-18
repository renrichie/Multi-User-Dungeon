package server.response;

import model.Account;

/**
 * Author:   Brian Lovelace
 * File:     PlayerStateResponse.java
 * Purpose:  The PlayerStateResponse class creates a PlayerStateResponse object that represents a response to the player's state being updated.
 */

public class PlayerStateResponse extends ServerResponse
{

	public Account account;
	
	public PlayerStateResponse(Account user, String msg)
	{
		super(msg, false);
		this.account = user;
	}
}
