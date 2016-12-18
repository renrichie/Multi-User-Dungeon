package model.item;

/**
 * Author:   Richie Ren
 * File:     Kiteshield.java
 * Purpose:  The Kiteshield class is used to represent a shield. It is the strongest shield in the game.
 */

public class Kiteshield extends Shield {

	private final static double DEFENCE_MULTIPLIER = 1.2;

	public Kiteshield() {
		super("Kite-Shield", "A heavy metal shield mainly used by cavalry units.", DEFENCE_MULTIPLIER);
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "k";
	}
}
