package model.item;

import model.MudEntity;
import model.Player;

/**
 * Author:   Richie Ren
 * File:     Protein.java
 * Purpose:  The Protein class is used to represent an item that increases the player's attack.
 */

public class Protein extends Item {

	private static int ATK_INCREASE = 1;
	private static int MAX_ALLOWED = 20;

	public Protein() {
		super("Protein", "This mysterious powder is said to raise the physical strength of whoever uses it.");
	}

	/**
	 * Method:  use(MudEntity target) 
	 * Purpose: It increases the attack stat of the specified target.
	 *          Returns true since the action was successful.
	 */

	@Override
	public boolean use(MudEntity target) {
		Player character = (Player) target;

		if (character.getAttack() + ATK_INCREASE < MAX_ALLOWED) {
			character.setAttack(character.getAttack() + ATK_INCREASE);
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
		return "b";
	}
}
