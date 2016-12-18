package model.mob;

import java.util.Random;

import model.item.HiPotion;
import model.item.Item;
import model.item.Kiteshield;
import model.item.Longsword;
import model.item.SquareShield;

/**
 * Author:   Richie Ren
 * File:     UndeadWarrior.java
 * Purpose:  The UndeadWarrior class is used to represent an undead warrior monster in the game.
 *           An undead warrior has 40 HP and 5 attack and 2 defence.
 */

public class UndeadWarrior extends Mob {
	
	private Random rng;
	private final double updateChance = 0.65;
	
	public UndeadWarrior() {
		super("Undead-Warrior", "A reanimated skeleton whose strong grudges bind it to the mortal realm.", 40, 5, 2);
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
	 * Purpose: It returns the amount of damage that the monster blocks, which is less than 5.
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
			return new SquareShield();
		}
		else if (result == 2) {
			return new Kiteshield();
		}
		else if (result == 3) {
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
		return "W";
	}
}
