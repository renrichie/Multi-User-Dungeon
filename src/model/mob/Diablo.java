package model.mob;

import java.util.Random;
import model.item.GlassCannon;
import model.item.Item;
import model.item.Mastersword;
import model.item.MaxPotion;

/**
 * Author:   Richie Ren
 * File:     Diablo.java
 * Purpose:  The Diablo class is used to represent the boss Diablo in the game.
 *           Diablo has 200 HP and 9 attack and 7 defence.
 */

public class Diablo extends Mob {
	
	private Random rng;
	private final double updateChance = 0.35;
	
	public Diablo() {
		super("Diablo", "Evil incarnate on the mortal plain.", 200, 9, 7);
		super.setUpdateChance(updateChance);
		rng = new Random();
	}

	/**
	 * Method:  calculateAttack() 
	 * Purpose: It returns the amount of damage that the monster does, which is less than 14.
	 */
	
	@Override
	public int calculateAttack() {
		return rng.nextInt(5) + super.getAttack();
	}

	/**
	 * Method:  calculateDefence() 
	 * Purpose: It returns the amount of damage that the monster blocks, which is less than 12.
	 */
	
	@Override
	public int calculateDefence() {
		return rng.nextInt(5) + super.getDefence();
	}

	/**
	 * Method:  generateItemDrops() 
	 * Purpose: It returns the item that the monster dropped.
	 */
	
	@Override
	public Item generateItemDrops() {
		int result = rng.nextInt(5);
		
		if (result == 0 || result == 1) {
			return new Mastersword();
		}
		else if (result == 2) {
			return new GlassCannon();
		}
		else {
			return new MaxPotion();
		}
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "D";
	}
}
