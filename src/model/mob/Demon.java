package model.mob;

import java.util.Random;

import model.item.Calcium;
import model.item.HiPotion;
import model.item.Item;
import model.item.SquareShield;

/**
 * Author:   Richie Ren
 * File:     Demon.java
 * Purpose:  The Demon class is used to represent a demon monster in the game.
 *           A demon has 45 HP and 5 attack and 3 defence.
 */

public class Demon extends Mob {
	
	private Random rng;
	private final double updateChance = 0.5;
	
	public Demon() {
		super("Demon", "The spawn of Diablo himself.", 45, 5, 3);
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
			return new Calcium();
		}
		else if (result == 1) {
			return new SquareShield();
		}
		else if (result == 2) {
			return new HiPotion();
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
		return "E";
	}
}
