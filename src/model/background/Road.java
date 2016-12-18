package model.background;

/**
 * Author:   Brian Lovelace
 * File:     Road.java
 * Purpose:  The Road class is an object used to represent a road on the board.
 */

public class Road extends Background
{

	public Road()
	{
		//traversable and stackable
		super(true, true);
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "=";
	}

	/**
	 * Method:  getDescription() 
	 * Purpose: It returns the description of the entity.
	 */
	
	@Override
	public String getDescription() {
		return "Is a road that can be traversed by both mobs and players";
	}

}
