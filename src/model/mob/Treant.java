package model.mob;

import java.util.Random;
import model.item.Item;
import model.item.Protein;
import model.item.Sword;
import model.item.WoodenShield;

/**
 * Author:   Richie Ren
 * File:     Treant.java
 * Purpose:  The Treant class is used to represent a treant monster in the game.
 *           A treant has 30 HP and 4 attack and 2 defence.
 */

public class Treant extends Mob {
	
	private Random rng;
	private final double updateChance = 0.68;
	
	public Treant() {
		super("Treant", "A tree that has gained sentience and is now hostile.", 30, 4, 2, false, true, true);
		super.setUpdateChance(updateChance);
		rng = new Random();
	}

	/**
	 * Method:  calculateAttack() 
	 * Purpose: It returns the amount of damage that the monster does, which is less than 7.
	 */
	
	@Override
	public int calculateAttack() {
		return rng.nextInt(3) + super.getAttack();
	}

	/**
	 * Method:  calculateDefence() 
	 * Purpose: It returns the amount of damage that the monster blocks, which is less than 7.
	 */
	
	@Override
	public int calculateDefence() {
		return rng.nextInt(3) + super.getDefence();
	}

	/**
	 * Method:  generateItemDrops() 
	 * Purpose: It returns the item that the monster dropped.
	 */
	
	@Override
	public Item generateItemDrops() {
		int result = rng.nextInt(5);

		if (result == 0) {
			return new WoodenShield();
		}
		else if (result == 1) {
			return new Sword();
		}
		else if (result == 2) {
			return new Protein();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "T";
	}
}
