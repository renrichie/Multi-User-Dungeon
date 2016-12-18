package model.mob;

import java.util.Random;

import model.item.HiPotion;
import model.item.Iron;
import model.item.Item;
import model.item.Kiteshield;

/**
 * Author:   Richie Ren
 * File:     Pikachu.java
 * Purpose:  The Pikachu class is used to represent a pikachu monster in the game.
 *           A pikachu has 50 HP and 5 attack and defence.
 */

public class Pikachu extends Mob {
	
	private Random rng;
	private final double updateChance = 0.52;
	
	public Pikachu() {
		super("Pikachu", "A yellow rat-like creature. It only seems to say its name for some reason.", 50, 5, 5);
		super.setUpdateChance(updateChance);
		rng = new Random();
	}

	/**
	 * Method:  calculateAttack() 
	 * Purpose: It returns the amount of damage that the monster does, which is less than 8.
	 */
	
	@Override
	public int calculateAttack() {
		return rng.nextInt(3) + super.getAttack();
	}

	/**
	 * Method:  calculateDefence() 
	 * Purpose: It returns the amount of damage that the monster blocks, which is less than 8.
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
			return new HiPotion();
		}
		else if (result == 1) {
			return new Iron();
		}
		else if (result == 2) {
			return new Kiteshield();
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
		return "U";
	}
}
