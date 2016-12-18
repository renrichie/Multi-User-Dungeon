package model.item;

import model.MudEntity;
import model.Player;

/**
 * Author:   Richie Ren
 * File:     TreasureChest.java
 * Purpose:  The TreasureChest class is used to represent an item that can either give a player a random item or spawn a mimic.
 */

public class TreasureChest extends Item {
	
	public TreasureChest() {
		super("Treasure-Chest", "A slightly worn-out treasure chest possibly containing untold riches.");
	}

	/**
	 * Method:  use(MudEntity target) 
	 * Purpose: It randomly gives the user an item.
	 *          Returns true if the action was successful.
	 *          Returns false if the chest is a mimic.
	 */
	
	@Override
	public boolean use(MudEntity target) {
		if (Math.random() <= 0.7) {
			Item item = null;
			
			double result= Math.random();
			
			if (result <= 0.1) {
				item = new HiPotion();
			}
			else if (result > 0.1 && result <= 0.3) {
				item = new MysteryPotion();
			}
			else if (result > 0.3 && result <= 0.4) {
				item = new Kiteshield();
			}
			else if (result > 0.4 && result <= 0.45) {
				item = new GlassCannon();
			}
			else if (result > 0.45 && result <= 0.65) {
				item = new MaxPotion();
			}
			else if (result > 0.65 && result <= 0.66) {
				item = new Mastersword();
			}
			else if (result > 0.66 && result <= 0.75) {
				item = new Polymorph();
			}
			else if (result > 0.75 && result <= 0.9) {
				item = new Teleporter();
			}
			else {
				item = new Iron();
			}
			
			Player player = (Player) target;
			player.addToInventory(item);
			return true;
		}
		else {
			return false;
		}
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
		return "t";
	}
}
