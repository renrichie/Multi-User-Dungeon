package model.item;

/**
 * Author:   Richie Ren
 * File:     Mastersword.java
 * Purpose:  The Mastersword class is used to represent a mastersword. It is the strongest weapon in the game.
 */

public class Mastersword extends Weapon {

	private final static double ATTACK_MULTIPLIER = 2.5;

	public Mastersword() {
		super("Mastersword", "A longsword that lets off a strange glow when wielded. It is said to have been the weapon of choice of a particular Hero of Time.", ATTACK_MULTIPLIER);
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "m";
	}
}
