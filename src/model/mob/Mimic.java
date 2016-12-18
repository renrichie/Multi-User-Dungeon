package model.mob;

import java.util.Random;

import model.item.GlassCannon;
import model.item.Item;
import model.item.MaxPotion;
import model.item.MysteryPotion;
import model.item.Protein;

/**
 * Author:   Richie Ren
 * File:     Mimic.java
 * Purpose:  The Mimic class is used to represent a mimic monster in the game.
 *           A mimic has 60 HP and 5 attack and defence.
 */

public class Mimic extends Mob {
	
	private Random rng;
	private final double updateChance = 0.5;
	
	public Mimic() {
		super("Mimic", "A monster that disguises itself as a treasure chest.", 60, 5, 5);
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
			return new GlassCannon();
		}
		else if (result == 1) {
			return new MysteryPotion();
		}
		else if (result == 2) {
			return new MaxPotion();
		}
		else if (result == 3) {
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
		return "M";
	}
}
