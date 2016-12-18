package server.response;

import model.Account;

/**
 * Author:   Brian Lovelace
 * File:     LogoffResponse.java
 * Purpose:  The LogoffResponse class creates a LogoffResponse object that represents a response to the log off command.
 */

public class LogoffResponse extends ServerResponse
{
	public boolean logOffSuccess;
	public Account account;
	
	public LogoffResponse(Account acc, boolean loggedOff)
	{
		super("Logoff Response", false);
		this.account = acc;
		this.logOffSuccess = loggedOff;
		
	}

	/**
	 * Method:  toString() 
	 * Purpose: It returns the String indicating the result of the command.
	 */
	
	@Override
	public String toString()
	{
		if (logOffSuccess)
			return account.getUsername() + " was logged off";
		else
			return "The user was unable to be logged off";
	}		
}
