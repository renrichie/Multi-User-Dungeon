package model.mob;

import java.util.Random;

import model.item.Item;
import model.item.Longsword;
import model.item.WoodenShield;

/**
 * Author:   Richie Ren
 * File:     Goblin.java
 * Purpose:  The Goblin class is used to represent a goblin monster in the game.
 *           A goblin has 25 HP and 3 attack and defence.
 */

public class Goblin extends Mob {
	
	private Random rng;
	private final double updateChance = 0.55;
	
	public Goblin() {
		super("Goblin", "An ugly green monster with rough skin that is covered in boils of some sort.", 25, 3, 3);
		super.setUpdateChance(updateChance);
		rng = new Random();
	}

	/**
	 * Method:  calculateAttack() 
	 * Purpose: It returns the amount of damage that the monster does, which is less than 6.
	 */
	
	@Override
	public int calculateAttack() {
		return rng.nextInt(3) + super.getAttack();
	}

	/**
	 * Method:  calculateDefence() 
	 * Purpose: It returns the amount of damage that the monster blocks, which is less than 6.
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
			return new Longsword();
		}
		else if (result == 1) {
			return new WoodenShield();
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
		return "G";
	}
}
