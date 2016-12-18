package model.item;

import model.MudEntity;
import model.Player;

/**
 * Author:   Richie Ren
 * File:     GlassCannon.java
 * Purpose:  The GlassCannon class is used to represent an item that increases the player's attack 
 * at the cost of their defence. The glass cannon is so big, that you can not traverse over it.
 */

public class GlassCannon extends Item {

	private static int ATK_INCREASE = 5;
	private static int DEFENCE_DECREASE = 7;
	
	public GlassCannon() {
		super("Glass-Cannon", "This item turns its user into a figurative glass cannon--greatly increasing attack at the cost of defence.", true, false, false);
	}

	/**
	 * Method:  use(MudEntity target) 
	 * Purpose: It increases the attack stat and lowers the defence stat of the specified target.
	 *          Returns true since the action was successful.
	 */

	@Override
	public boolean use(MudEntity target) {
		Player character = (Player) target;

		character.setAttack(character.getAttack() + ATK_INCREASE);
		
		if (character.getDefence() - DEFENCE_DECREASE <= 0) {
			character.setDefence(1);
		}
		else {
			character.setDefence(character.getDefence() - DEFENCE_DECREASE);
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
		return "g";
	}
}
