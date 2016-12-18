package model.item;

/**
 * Author:   Richie Ren
 * File:     SquareShield.java
 * Purpose:  The SquareShield class is used to represent a shield. It is the mid-tier shield in the game.
 */

public class SquareShield extends Shield {

	private final static double DEFENCE_MULTIPLIER = 0.75;

	public SquareShield() {
		super("Square-Shield", "A metal shield that was crafted in the shape of a square. It seems to barely offer any defence.", DEFENCE_MULTIPLIER);
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "q";
	}
}
