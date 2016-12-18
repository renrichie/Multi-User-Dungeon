package model.item;

import model.MudEntity;
import model.Player;

/**
 * Author:   Richie Ren
 * File:     Shield.java
 * Purpose:  The Shield abstract class is used to represent a shield.
 */

public abstract class Shield extends Item {

	private double defenceMultiplier;

	public Shield(String itemName, String description, double defenceMultiplier) {
		super(itemName, description);
		this.defenceMultiplier = defenceMultiplier;
	}

	/**
	 * Method:  getDefenceMultiplier() 
	 * Purpose: It returns a double that is used to change the amount of damage that a player blocks.
	 */

	public double getDefenceMultiplier() {
		return this.defenceMultiplier;
	}

	/**
	 * Method:  use(MudEntity target) 
	 * Purpose: It equips the specified target with a shield.
	 *          Returns true since the action was successful.
	 */

	@Override
	public boolean use(MudEntity target) {
		Player character = (Player) target;

		character.setShield(this);

		return true;
	}
}
