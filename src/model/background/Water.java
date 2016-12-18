package model.background;

/**
 * Author:   Brian Lovelace
 * File:     Water.java
 * Purpose:  The Water class is an object used to represent water on the board.
 */

public class Water extends Background
{

	public Water()
	{
		//non-traversable, non-stackable
		super(false, false);
	}

	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "~";
	}

	/**
	 * Method:  getDescription() 
	 * Purpose: It returns the description of the entity.
	 */
	
	@Override
	public String getDescription() {
		return "Is a body of water that acts to impede the movement of mobs and players alike";
	}
}
