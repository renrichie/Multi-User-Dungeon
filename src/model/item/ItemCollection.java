package model.item;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author:   Richie Ren
 * File:     ItemCollection.java
 * Purpose:  The ItemCollection class simply contains a list of all items in the game and can be used to check if the string is the same as an item's name.
 */

public class ItemCollection {

	public static ArrayList<Item> allItems = new ArrayList<>(Arrays.asList(new Calcium(), new GlassCannon(), new HiPotion(), new Iron(), new Kiteshield(), 
											 new Longsword(), new Mastersword(), new MaxPotion(), new MysteryPotion(), new Potion(), new Protein(), 
											 new SquareShield(), new Sword(), new WetNoodle(), new WoodenShield(), new TreasureChest(), new Polymorph(),
											 new Teleporter()));
	
	/**
	 * Method:  isAnItem() 
	 * Purpose: It returns a boolean indicating if the String is the same as an item name.
	 */
	
	public static boolean isAnItem(String item) {
		for (Item i : allItems) {
			if (i.getItemName().toLowerCase().trim().equals(item.toLowerCase().trim())) {
				return true;
			}
		}
		
		return false;
	}
}
