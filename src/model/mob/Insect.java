package model.mob;

import java.util.Random;

import model.item.Item;
import model.item.Potion;
import model.item.WetNoodle;

/**
 * Author:   Richie Ren
 * File:     Insect.java
 * Purpose:  The Insect class is used to represent an insect monster in the game.
 *           An insect has 5 HP and 1 attack and defence.
 */

public class Insect extends Mob {
	
	private Random rng;
	private final double updateChance = 0.75;
	
	public Insect() {
		super("Insect", "A creepy-crawly that makes your skin shiver merely by looking at it.", 5, 1, 1);
		super.setUpdateChance(updateChance);
		rng = new Random();
	}

	/**
	 * Method:  calculateAttack() 
	 * Purpose: It returns the amount of damage that the monster does, which is less than 4.
	 */
	
	@Override
	public int calculateAttack() {
		return rng.nextInt(3) + super.getAttack();
	}

	/**
	 * Method:  calculateDefence() 
	 * Purpose: It returns the amount of damage that the monster blocks, which is less than 4.
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
		int result = rng.nextInt(4);

		if (result == 0) {
			return new Potion();
		}
		else if (result == 1) {
			return new WetNoodle();
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
		return "N";
	}
}
