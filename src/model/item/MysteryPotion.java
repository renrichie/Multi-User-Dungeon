package model.item;

import model.MudEntity;
import model.Player;

/**
 * Author:   Richie Ren
 * File:     MysteryPotion.java
 * Purpose:  The MysteryPotion class is used to represent a potion item.
 *           A mystery potion either heals for 15 HP or does 15 damage to its user.
 */

public class MysteryPotion extends Item {

	private static int AMOUNT = 15;

	public MysteryPotion() {
		super("Mystery-Potion", "A strange concoction whose effect is unknown. Some tales tell of its wondrous healing abilities. Others tell of its users meeting a terrible fate.");
	}

	/**
	 * Method:  use(MudEntity target) 
	 * Purpose: It performs the potion's effect on the specified target.
	 *          Returns true since the action was successful.
	 */
	
	@Override
	public boolean use(MudEntity target) {
		Player character = (Player) target;
		
		double result = Math.random();
		
		if (result < 0.5) {
			if (character.getCurrentHealth() - AMOUNT <= 0) {
				character.setCurrentHealth(1);
			}
			else {
				character.setCurrentHealth(character.getCurrentHealth() - AMOUNT);
			}
		}
		else {
			if (character.getCurrentHealth() + AMOUNT > character.getMaxHealth()) {
				character.setCurrentHealth(character.getMaxHealth());
			}
			else {
				character.setCurrentHealth(character.getCurrentHealth() + AMOUNT);
			}
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
		return "j";
	}
}
