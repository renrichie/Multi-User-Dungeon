package model.item;

/**
 * Author:   Richie Ren
 * File:     Unarmed.java
 * Purpose:  The Unarmed class is used to represent hands.
 */

public class Unarmed extends Weapon {
	
	private final static double ATTACK_MULTIPLIER = 0.3;
	
	public Unarmed() {
		super("Unarmed", "These are your hands.", ATTACK_MULTIPLIER);
	}
}
