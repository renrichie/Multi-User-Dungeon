package server.model;

import java.io.Serializable;
import java.util.HashMap;

import model.Account;

/**
 * 
 * Author:   Richie Ren
 * File:     AccountCollection.java
 * Purpose:  The AccountCollection class creates a collection of Account objects that represents the account database for the game.
 */

public class AccountCollection implements Serializable{

	private HashMap<String, Account> accounts = 
			new HashMap<String, Account>();

	public AccountCollection()
	{
		accounts = new HashMap<String, Account>();
	}
	
	/**
	 * Method:  verifyAccount(String username, String password) 
	 * Purpose: It verifies the data and returns the appropriate account.
	 */
	
	public Account verifyAccount(String username, String password) 
	{
		if (accounts.containsKey(username))
		{
			Account user = accounts.get(username);
			if (user.verifyPassword(password))
				return user;
		}
		return null;
	}
	
	/**
	 * Method:  addAccount(String username, String password) 
	 * Purpose: Adds a new account with the specified username and password to the database.
	 *          It checks to see if there already exists an account with the username.
	 *          If yes, returns false. If no, returns true;
	 */
	
	public boolean addAccount(String username, String password) {
		//Returns false if an account with that username already exists
		if (!accounts.containsKey(username))
		{
			Account user = new Account(username, password);
			accounts.put(username, user);
			return true;
		}
		return false;
	}

	/**
	 * Method:  resetChatlogs() 
	 * Purpose: It clears out the chat for all the accounts.
	 */
	
	public void resetChatlogs()
	{
		for (Account a : accounts.values())
		{
			a.getCharacter().clearChat();
		}
			
	}
}
