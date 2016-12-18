package model.item;


/**
 * Author:   Richie Ren
 * File:     WetNoodle.java
 * Purpose:  The WetNoodle class is used to represent a wet noodle weapon. It is the weakest weapon in the game, meant to be a joke item though.
 */

public class WetNoodle extends Weapon {

	private final static double ATTACK_MULTIPLIER = 0.1;

	public WetNoodle() {
		super("Wet-Noodle", "A single strand of pasta. Truly a terrifying weapon.", ATTACK_MULTIPLIER);
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "n";
	}
}
