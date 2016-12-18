package model.item;

/**
 * Author:   Richie Ren
 * File:     Longsword.java
 * Purpose:  The Longsword class is used to represent a longsword. It is the second strongest weapon in the game.
 */

public class Longsword extends Weapon {

	private final static double ATTACK_MULTIPLIER = 1.1;

	public Longsword() {
		super("Longsword", "A longsword smithed in the great forges of the royal blacksmiths.", ATTACK_MULTIPLIER);
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "l";
	}
}
