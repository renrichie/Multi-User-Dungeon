package model.item;

/**
 * Author:   Richie Ren
 * File:     NoShield.java
 * Purpose:  The NoShield class is used to represent that the player has no shield equipped or the same as being unarmed.
 */

public class NoShield extends Shield {

	private final static double DEFENCE_MULTIPLIER = 0.3;

	public NoShield() {
		super("No Shield", "You have no shield equipped.", DEFENCE_MULTIPLIER);
	}
}
