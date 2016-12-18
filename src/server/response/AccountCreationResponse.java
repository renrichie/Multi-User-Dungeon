package server.response;

/**
 * Author:   Brian Lovelace
 * File:     AccountCreationResponse.java
 * Purpose:  The AccountCreationResponse class creates a AccountCreationResponse object that represents a response to the account creation command.
 */

public class AccountCreationResponse extends LogonResponse
{
	
	public AccountCreationResponse(String username, boolean success, int uv)
	{
		super(username, success, uv);
	}

	/**
	 * Method:  toString() 
	 * Purpose: It returns the String indicating the result of the command.
	 */
	
	@Override
	public String toString()
	{
		if(success)
			return "An account for " + username 
			+ " has been created.";
		else
			return "The account was not created";
	}
}
