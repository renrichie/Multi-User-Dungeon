package model.item;

/**
 * Author:   Richie Ren
 * File:     Sword.java
 * Purpose:  The Sword class is used to represent a sword. It is the weakest (actual) weapon in the game.
 */

public class Sword extends Weapon {

	private final static double ATTACK_MULTIPLIER = 0.7;

	public Sword() {
		super("Sword", "A sword painstakingly crafted by the local blacksmith.", ATTACK_MULTIPLIER);
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "s";
	}
}
