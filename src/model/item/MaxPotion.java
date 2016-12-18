package model.item;

import model.MudEntity;
import model.Player;

/**
 * Author:   Richie Ren
 * File:     MaxPotion.java
 * Purpose:  The MaxPotion class is used to represent a potion item.
 *           A max potion heals a user to full hp. A potion is weak and will 
 *           break if anything is stacked on top of them
 */

public class MaxPotion extends Item {

	public MaxPotion() {
		super("Max-Potion", "A legendary potion that most people have only read about in tales of lore. It completely heals its user.", true, true, false);
	}

	/**
	 * Method:  use(MudEntity target) 
	 * Purpose: It performs the potion's effect on the specified target.
	 *          Returns true since the action was successful.
	 */
	
	@Override
	public boolean use(MudEntity target) {
		Player character = (Player) target;
		
		character.setCurrentHealth(character.getMaxHealth());
		
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
		return "x";
	}
}
