package server.response;

/**
 * Author:   Brian Lovelace
 * File:     LogonResponse.java
 * Purpose:  The LogonResponse class creates a LogonResponse object that represents a response to the log on command.
 */

public class LogonResponse extends ServerResponse
{
	public final boolean success;
	public final int USER_VERIFICATION;
	public final String username;
	
	public LogonResponse(String username, boolean loggedOn, int user_verification)
	{
		super("Logon Response", false);
		this.username = username;
		this.success = loggedOn;
		this.USER_VERIFICATION = user_verification;
	}

	/**
	 * Method:  toString() 
	 * Purpose: It returns the String indicating the result of the command.
	 */
	
	@Override
	public String toString()
	{
		if (success)
			return username + " has logged on.";
		else
			return "The user was unable to log on.";
	}
}
