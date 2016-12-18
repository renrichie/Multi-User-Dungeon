package model.mob;

import java.util.Random;

import model.item.GlassCannon;
import model.item.Item;
import model.item.Mastersword;

/**
 * Author:   Richie Ren
 * File:     MUDProject.java
 * Purpose:  The MUDProject class is used to represent a MUDProject monster in the game.
 *           It has 999 HP and 1 attack and defence.
 */

public class MUDProject extends Mob {
	
	private Random rng;
	private final double updateChance = 0.37;
	
	public MUDProject() {
		super("MUD-Project", "A project that is deceivingly difficult to code. It baits people to choose it by appearing to be the lesser of the evils.", 999, 1, 1);
		super.setUpdateChance(updateChance);
		rng = new Random();
	}

	/**
	 * Method:  calculateAttack() 
	 * Purpose: It returns the amount of damage that the monster does, which is less than 7.
	 */
	
	@Override
	public int calculateAttack() {
		return rng.nextInt(6) + super.getAttack();
	}

	/**
	 * Method:  calculateDefence() 
	 * Purpose: It returns the amount of damage that the monster blocks, which is less than 7.
	 */
	
	@Override
	public int calculateDefence() {
		return rng.nextInt(6) + super.getDefence();
	}

	/**
	 * Method:  generateItemDrops() 
	 * Purpose: It returns the item that the monster dropped.
	 */
	
	@Override
	public Item generateItemDrops() {
		int result = rng.nextInt(2);

		if (result == 0) {
			return new Mastersword();
		}
		else {
			return new GlassCannon();
		}
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "B";
	}
}