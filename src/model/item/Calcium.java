package model.item;

import model.MudEntity;
import model.Player;

/**
 * Author:   Richie Ren
 * File:     Calcium.java
 * Purpose:  The Calcium class is used to represent an item that increases the player's hp.
 */

public class Calcium extends Item {
	
	private static int HP_INCREASE = 5;
	private static int MAX_ALLOWED = 150;

	public Calcium() {
		super("Calcium", "This mysterious powder is said to make its user healthier by strengthening their bones.");
	}

	/**
	 * Method:  use(MudEntity target) 
	 * Purpose: It increases the HP of the specified target.
	 *          Returns true since the action was successful.
	 */

	@Override
	public boolean use(MudEntity target) {
		Player character = (Player) target;

		if (character.getMaxHealth() + HP_INCREASE < MAX_ALLOWED) {
			character.setMaxHealth(character.getMaxHealth() + HP_INCREASE);
		}

		return true;
	}
	
	/**
	 * Method:  isConsumable() 
	 * Purpose: It returns a boolean indicating if the item is a consumable.
	 */
	
	@Override
	public boolean isConsumable()
	{
		return true;
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "c";
	}
}
