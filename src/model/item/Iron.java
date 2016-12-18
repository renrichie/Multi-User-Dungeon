package model.item;


import model.MudEntity;
import model.Player;

/**
 * Author:   Richie Ren
 * File:     Iron.java
 * Purpose:  The Iron class is used to represent an item that increases the player's defence.
 */

public class Iron extends Item {

	private static int DEF_INCREASE = 1;
	private static int MAX_ALLOWED = 20;

	public Iron() {
		super("Iron", "This mysterious powder is said to raise the pain tolerance of whoever uses it.");
	}

	/**
	 * Method:  use(MudEntity target) 
	 * Purpose: It increases the defence stat of the specified target.
	 *          Returns true since the action was successful.
	 */

	@Override
	public boolean use(MudEntity target) {
		Player character = (Player) target;

		if (character.getDefence() + DEF_INCREASE < MAX_ALLOWED) {
			character.setDefence(character.getDefence() + DEF_INCREASE);
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
		return "i";
	}
}
