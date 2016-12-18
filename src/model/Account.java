package model;

import java.io.Serializable;

/**
 * Author:   Richie Ren
 * File:     Account.java
 * Purpose:  The Account class creates a Account object that represents a user account for the MUD game.
 *           If an account does not have a Player object, then it means it is the user's first time playing.
 */

public class Account implements Serializable {

	public static final int NO_VERIFY = -1;
	
	private Player character;
	private String username, password;
	private transient int user_verification = NO_VERIFY; 
	
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		this.character = new Player(username);
	}
	
	/**
	 * Method:  getUsername() 
	 * Purpose: It returns the username of the account.
	 */
	
	public String getUsername() {
		return username;
	}

	/**
	 * Method:  verifyPassword() 
	 * Purpose: It checks if the parameter is the same as the password for the account.
	 */
	
	public boolean verifyPassword(String password) {
		return this.password.equals(password);
	}
	
	/**
	 * Method:  getCharacter() 
	 * Purpose: It returns the character associated with the account.
	 */
	
	public Player getCharacter() {
		return character;
	}
	
	/**
	 * Method:  setCharacter(Player newCharacter) 
	 * Purpose: It changes the character associated with the account. It is meant to be used if we add in the functionality to create a new character.
	 */
	
	public void setCharacter(Player newCharacter) {
		this.character = newCharacter;
	}
	
	/**
	 * Method:  resetUserVerification() 
	 * Purpose: It generates another user verification number and returns it.
	 */
	
	public int resetUserVerification()
	{
		this.user_verification = 0 + (int)(Math.random() * Integer.MAX_VALUE);
		return this.user_verification;
	}
	
	/**
	 * Method:  clearUserVerification() 
	 * Purpose: It sets the user verification to -1 and returns it.
	 */
	
	public int clearUserVerification()
	{
		this.user_verification = -1;
		return this.user_verification;
	}
	
	/**
	 * Method:  isVerified() 
	 * Purpose: It checks if the UV number is the same and returns a boolean.
	 */
	
	public boolean isVerified(int uv)
	{
		if (this.user_verification == -1)
			return false;
		else if (this.user_verification != uv)
			return false;
		else return true;
	}
}
