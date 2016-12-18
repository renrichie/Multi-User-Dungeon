package model.mob;

import java.util.Random;

import model.item.Item;
import model.item.Potion;
import model.item.Sword;

/**
 * Author:   Richie Ren
 * File:     Rabbit.java
 * Purpose:  The Rabbit class is used to represent a rabbit monster in the game.
 *           A rabbit has 10 HP and 1 attack and defence.
 */

public class Rabbit extends Mob {
	
	private Random rng;
	private final double updateChance = 0.75;
	
	public Rabbit() {
		super("Rabbit", "A cute fluffy rabbit.", 10, 1, 1);
		super.setUpdateChance(updateChance);
		rng = new Random();
	}

	/**
	 * Method:  calculateAttack() 
	 * Purpose: It returns the amount of damage that the rabbit does, which is less than 4.
	 */
	
	@Override
	public int calculateAttack() {
		return rng.nextInt(3) + super.getAttack();
	}

	/**
	 * Method:  calculateDefence() 
	 * Purpose: It returns the amount of damage that the rabbit blocks, which is less than 4.
	 */
	
	@Override
	public int calculateDefence() {
		return rng.nextInt(3) + super.getDefence();
	}

	/**
	 * Method:  generateItemDrops() 
	 * Purpose: It returns the item that the rabbit dropped.
	 */
	
	@Override
	public Item generateItemDrops() {
		int result = rng.nextInt(4);
		
		if (result == 0) {
			return new Potion();
		}
		else if (result == 1) {
			return new Sword();
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
		return "R";
	}
}
