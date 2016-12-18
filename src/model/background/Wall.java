package model.background;

/**
 * Author:   Brian Lovelace
 * File:     Wall.java
 * Purpose:  The Wall class is an object used to represent a wall on the board.
 */

public class Wall extends Background
{
	public Wall()
	{
		// non-traversable, and non-stackable
		super(false, false);
	}

	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "+";
	}

	/**
	 * Method:  getDescription() 
	 * Purpose: It returns the description of the entity.
	 */
	
	@Override
	public String getDescription() {
		return "Is a wall that acts as a border to contain mobs and players within";
	}
	
}
