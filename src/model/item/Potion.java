package model.item;

import model.MudEntity;
import model.Player;

/**
 * Author:   Richie Ren
 * File:     Potion.java
 * Purpose:  The Potion class is used to represent a potion item.
 *           A potion heals for 10 HP currently.
 */

public class Potion extends Item {

	private static int HEAL_AMOUNT = 10;
	
	public Potion() {
		super("Potion", "This potion was brewed by the kingdom's finest apothecary. It is capable of healing the user by " + HEAL_AMOUNT + " HP.");
	}
	

	/**
	 * Method:  use(MudEntity target) 
	 * Purpose: It performs the potion's effect on the specified target.
	 *          Returns true since the action was successful.
	 */
	
	@Override
	public boolean use(MudEntity target) {
		Player character = (Player) target;
		
		if (character.getCurrentHealth() + HEAL_AMOUNT <= character.getMaxHealth()) {
			character.setCurrentHealth(character.getCurrentHealth() + HEAL_AMOUNT);
		}
		else {
			character.setCurrentHealth(character.getMaxHealth());
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
		return "o";
	}
}
