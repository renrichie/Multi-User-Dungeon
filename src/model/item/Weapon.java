package model.item;

import model.MudEntity;
import model.Player;

/**
 * Author:   Richie Ren
 * File:     Weapon.java
 * Purpose:  The Weapon abstract class is used to represent a weapon.
 */

public abstract class Weapon extends Item {

	private double attackMultiplier;
	
	public Weapon(String itemName, String description, double attackMultiplier) {
		super(itemName, description);
		this.attackMultiplier = attackMultiplier;
	}
	
	/**
	 * Method:  getAttackMultiplier() 
	 * Purpose: It returns a double that is used to change the amount of damage that a player does.
	 */
	
	public double getAttackMultiplier() {
		return this.attackMultiplier;
	}
	
	/**
	 * Method:  use(MudEntity target) 
	 * Purpose: It equips the specified target with a weapon.
	 *          Returns true since the action was successful.
	 */
	
	@Override
	public boolean use(MudEntity target) {
		Player character = (Player) target;
		
		character.setWeapon(this);
		
		return true;
	}
}
