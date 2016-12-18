package model.item;

/**
 * Author:   Richie Ren
 * File:     WoodenShield.java
 * Purpose:  The WoodenShield class is used to represent a wooden shield. It is the weakest shield in the game.
 */

public class WoodenShield extends Shield {

	private final static double DEFENCE_MULTIPLIER = 0.5;

	public WoodenShield() {
		super("Wooden-Shield", "A shield crudely made from a leftover piece of timber.", DEFENCE_MULTIPLIER);
	}

	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "w";
	}
}
