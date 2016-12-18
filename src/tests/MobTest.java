package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.mob.*;

/**
 * Author:   Richie Ren
 * File:     MobTest.java
 * Purpose:  This is a unit test for any class that extends Mob.
 */

public class MobTest {

	private Rabbit rabbit = new Rabbit();
	private Demon demon = new Demon();
	private Diablo diablo = new Diablo();
	private Goblin goblin = new Goblin();
	private Insect insect = new Insect();
	private Mimic mimic = new Mimic();
	private MUDProject mp = new MUDProject();
	private Pikachu pikachu = new Pikachu();
	private Treant treant = new Treant();
	private UndeadWarrior uw = new UndeadWarrior();
	
	/**
	 * Method:  testGetName() 
	 * Purpose: It tests the getMobName() method for mobs.
	 */
	
	@Test
	public void testGetName() {
		assertTrue(rabbit.getMobName().equals("Rabbit"));
		assertTrue(demon.getMobName().equals("Demon"));
		assertTrue(diablo.getMobName().equals("Diablo"));
		assertTrue(goblin.getMobName().equals("Goblin"));
		assertTrue(insect.getMobName().equals("Insect"));
		assertTrue(mimic.getMobName().equals("Mimic"));
		assertTrue(mp.getMobName().equals("MUD-Project"));
		assertTrue(pikachu.getMobName().equals("Pikachu"));
		assertTrue(treant.getMobName().equals("Treant"));
		assertTrue(uw.getMobName().equals("Undead-Warrior"));
	}
	
	/**
	 * Method:  testGetDescription() 
	 * Purpose: It tests the getDescription() method for mobs.
	 */
	
	@Test
	public void testGetDescription() {
		assertTrue(rabbit.getDescription().equals(rabbit.toString() + " - (" + rabbit.getMobName() + ") " + "A cute fluffy rabbit."));
		assertTrue(demon.getDescription().equals(demon.toString() + " - (" + demon.getMobName() + ") " + "The spawn of Diablo himself."));
		assertTrue(diablo.getDescription().equals(diablo.toString() + " - (" + diablo.getMobName() + ") " + "Evil incarnate on the mortal plain."));
		assertTrue(goblin.getDescription().equals(goblin.toString() + " - (" + goblin.getMobName() + ") " + "An ugly green monster with rough skin that is covered in boils of some sort."));
		assertTrue(insect.getDescription().equals(insect.toString() + " - (" + insect.getMobName() + ") " + "A creepy-crawly that makes your skin shiver merely by looking at it."));
		assertTrue(mimic.getDescription().equals(mimic.toString() + " - (" + mimic.getMobName() + ") " + "A monster that disguises itself as a treasure chest."));
		assertTrue(mp.getDescription().equals(mp.toString() + " - (" + mp.getMobName() + ") " + "A project that is deceivingly difficult to code. It baits people to choose it by appearing to be the lesser of the evils."));
		assertTrue(pikachu.getDescription().equals(pikachu.toString() + " - (" + pikachu.getMobName() + ") " + "A yellow rat-like creature. It only seems to say its name for some reason."));
		assertTrue(treant.getDescription().equals(treant.toString() + " - (" + treant.getMobName() + ") " + "A tree that has gained sentience and is now hostile."));
		assertTrue(uw.getDescription().equals(uw.toString() + " - (" + uw.getMobName() + ") " + "A reanimated skeleton whose strong grudges bind it to the mortal realm."));
	}
	
	/**
	 * Method:  testGetHealth() 
	 * Purpose: It tests the getHealth() method for mobs.
	 */
	
	@Test
	public void testGetHealth() {
		assertEquals(10, rabbit.getHealth());
		assertEquals(45, demon.getHealth());
		assertEquals(200, diablo.getHealth());
		assertEquals(25, goblin.getHealth());
		assertEquals(5, insect.getHealth());
		assertEquals(60, mimic.getHealth());
		assertEquals(999, mp.getHealth());
		assertEquals(50, pikachu.getHealth());
		assertEquals(30, treant.getHealth());
		assertEquals(40, uw.getHealth());
	}

	/**
	 * Method:  testGetAttack() 
	 * Purpose: It tests the getAttack() method for mobs.
	 */
	
	@Test
	public void testGetAttack() {
		assertEquals(1, rabbit.getAttack());
		assertEquals(5, demon.getAttack());
		assertEquals(9, diablo.getAttack());
		assertEquals(3, goblin.getAttack());
		assertEquals(1, insect.getAttack());
		assertEquals(5, mimic.getAttack());
		assertEquals(1, mp.getAttack());
		assertEquals(5, pikachu.getAttack());
		assertEquals(4, treant.getAttack());
		assertEquals(5, uw.getAttack());
	}

	/**
	 * Method:  testGetDefence() 
	 * Purpose: It tests the getDefence() method for mobs.
	 */
	
	@Test
	public void testGetDefence() {
		assertEquals(1, rabbit.getDefence());
		assertEquals(3, demon.getDefence());
		assertEquals(7, diablo.getDefence());
		assertEquals(3, goblin.getDefence());
		assertEquals(1, insect.getDefence());
		assertEquals(5, mimic.getDefence());
		assertEquals(1, mp.getDefence());
		assertEquals(5, pikachu.getDefence());
		assertEquals(2, treant.getDefence());
		assertEquals(2, uw.getDefence());
	}

	/**
	 * Method:  testSetHealth() 
	 * Purpose: It tests the set and getHealth() method for mobs.
	 */
	
	@Test
	public void testSetHealth() {
		rabbit.setHealth(1);
		assertEquals(1, rabbit.getHealth());
		demon.setHealth(1);
		assertEquals(1, demon.getHealth());
		diablo.setHealth(1);
		assertEquals(1, diablo.getHealth());
		goblin.setHealth(1);
		assertEquals(1, goblin.getHealth());
		insect.setHealth(1);
		assertEquals(1, insect.getHealth());
		mimic.setHealth(1);
		assertEquals(1, mimic.getHealth());
		mp.setHealth(1);
		assertEquals(1, mp.getHealth());
		pikachu.setHealth(1);
		assertEquals(1, pikachu.getHealth());
		treant.setHealth(1);
		assertEquals(1, treant.getHealth());
		uw.setHealth(1);
		assertEquals(1, uw.getHealth());
	}

	/**
	 * Method:  testSetAttack() 
	 * Purpose: It tests the setAttack() method for mobs.
	 */
	
	@Test
	public void testSetAttack() {
		rabbit.setAttack(2);
		assertEquals(2, rabbit.getAttack());
		demon.setAttack(1);
		assertEquals(1, demon.getAttack());
		diablo.setAttack(1);
		assertEquals(1, diablo.getAttack());
		goblin.setAttack(1);
		assertEquals(1, goblin.getAttack());
		insect.setAttack(1);
		assertEquals(1, insect.getAttack());
		mimic.setAttack(1);
		assertEquals(1, mimic.getAttack());
		mp.setAttack(1);
		assertEquals(1, mp.getAttack());
		pikachu.setAttack(1);
		assertEquals(1, pikachu.getAttack());
		treant.setAttack(1);
		assertEquals(1, treant.getAttack());
		uw.setAttack(1);
		assertEquals(1, uw.getAttack());
	}
	
	/**
	 * Method:  testSetDefence() 
	 * Purpose: It tests the setDefence() method for mobs.
	 */
	
	@Test
	public void testSetDefence() {
		rabbit.setDefence(2);
		assertEquals(2, rabbit.getDefence());
		demon.setDefence(1);
		assertEquals(1, demon.getDefence());
		diablo.setDefence(1);
		assertEquals(1, diablo.getDefence());
		goblin.setDefence(1);
		assertEquals(1, goblin.getDefence());
		insect.setDefence(1);
		assertEquals(1, insect.getDefence());
		mimic.setDefence(1);
		assertEquals(1, mimic.getDefence());
		mp.setDefence(1);
		assertEquals(1, mp.getDefence());
		pikachu.setDefence(1);
		assertEquals(1, pikachu.getDefence());
		treant.setDefence(1);
		assertEquals(1, treant.getDefence());
		uw.setDefence(1);
		assertEquals(1, uw.getDefence());
	}
	
	/**
	 * Method:  testCalculateAttack() 
	 * Purpose: It tests the calculateAttack() method for mobs.
	 */
	
	@Test
	public void testCalculateAttack() {
		rabbit.setAttack(1);
		assertTrue(rabbit.calculateAttack() < 4);
		assertTrue(rabbit.calculateAttack() < 4);
		assertTrue(rabbit.calculateAttack() < 4);
		assertTrue(rabbit.calculateAttack() < 4);
		assertTrue(rabbit.calculateAttack() < 4);
		
		demon.setAttack(5);
		assertTrue(demon.calculateAttack() < 8);
		assertTrue(demon.calculateAttack() < 8);
		assertTrue(demon.calculateAttack() < 8);
		assertTrue(demon.calculateAttack() < 8);
		assertTrue(demon.calculateAttack() < 8);
		
		diablo.setAttack(9);
		assertTrue(diablo.calculateAttack() < 14);
		assertTrue(diablo.calculateAttack() < 14);
		assertTrue(diablo.calculateAttack() < 14);
		assertTrue(diablo.calculateAttack() < 14);
		assertTrue(diablo.calculateAttack() < 14);
		
		goblin.setAttack(3);
		assertTrue(goblin.calculateAttack() < 6);
		assertTrue(goblin.calculateAttack() < 6);
		assertTrue(goblin.calculateAttack() < 6);
		assertTrue(goblin.calculateAttack() < 6);
		assertTrue(goblin.calculateAttack() < 6);
		
		insect.setAttack(1);
		assertTrue(insect.calculateAttack() < 4);
		assertTrue(insect.calculateAttack() < 4);
		assertTrue(insect.calculateAttack() < 4);
		assertTrue(insect.calculateAttack() < 4);
		assertTrue(insect.calculateAttack() < 4);
		
		mimic.setAttack(5);
		assertTrue(mimic.calculateAttack() < 8);
		assertTrue(mimic.calculateAttack() < 8);
		assertTrue(mimic.calculateAttack() < 8);
		assertTrue(mimic.calculateAttack() < 8);
		assertTrue(mimic.calculateAttack() < 8);
		
		mp.setAttack(1);
		assertTrue(mp.calculateAttack() < 7);
		assertTrue(mp.calculateAttack() < 7);
		assertTrue(mp.calculateAttack() < 7);
		assertTrue(mp.calculateAttack() < 7);
		assertTrue(mp.calculateAttack() < 7);
		
		pikachu.setAttack(5);
		assertTrue(pikachu.calculateAttack() < 8);
		assertTrue(pikachu.calculateAttack() < 8);
		assertTrue(pikachu.calculateAttack() < 8);
		assertTrue(pikachu.calculateAttack() < 8);
		assertTrue(pikachu.calculateAttack() < 8);
		
		treant.setAttack(4);
		assertTrue(treant.calculateAttack() < 7);
		assertTrue(treant.calculateAttack() < 7);
		assertTrue(treant.calculateAttack() < 7);
		assertTrue(treant.calculateAttack() < 7);
		assertTrue(treant.calculateAttack() < 7);
		
		uw.setAttack(5);
		assertTrue(uw.calculateAttack() < 8);
		assertTrue(uw.calculateAttack() < 8);
		assertTrue(uw.calculateAttack() < 8);
		assertTrue(uw.calculateAttack() < 8);
		assertTrue(uw.calculateAttack() < 8);
	}
	
	/**
	 * Method:  testCalculateDefence() 
	 * Purpose: It tests the calculateDefence() method for mobs.
	 */
	
	@Test
	public void testCalculateDefence() {
		rabbit.setDefence(1);
		assertTrue(rabbit.calculateDefence() < 4);
		assertTrue(rabbit.calculateDefence() < 4);
		assertTrue(rabbit.calculateDefence() < 4);
		assertTrue(rabbit.calculateDefence() < 4);
		assertTrue(rabbit.calculateDefence() < 4);
		
		demon.setDefence(5);
		assertTrue(demon.calculateDefence() < 8);
		assertTrue(demon.calculateDefence() < 8);
		assertTrue(demon.calculateDefence() < 8);
		assertTrue(demon.calculateDefence() < 8);
		assertTrue(demon.calculateDefence() < 8);
		
		diablo.setDefence(9);
		assertTrue(diablo.calculateDefence() < 14);
		assertTrue(diablo.calculateDefence() < 14);
		assertTrue(diablo.calculateDefence() < 14);
		assertTrue(diablo.calculateDefence() < 14);
		assertTrue(diablo.calculateDefence() < 14);
		
		goblin.setDefence(3);
		assertTrue(goblin.calculateDefence() < 6);
		assertTrue(goblin.calculateDefence() < 6);
		assertTrue(goblin.calculateDefence() < 6);
		assertTrue(goblin.calculateDefence() < 6);
		assertTrue(goblin.calculateDefence() < 6);
		
		insect.setDefence(1);
		assertTrue(insect.calculateDefence() < 4);
		assertTrue(insect.calculateDefence() < 4);
		assertTrue(insect.calculateDefence() < 4);
		assertTrue(insect.calculateDefence() < 4);
		assertTrue(insect.calculateDefence() < 4);
		
		mimic.setDefence(5);
		assertTrue(mimic.calculateDefence() < 8);
		assertTrue(mimic.calculateDefence() < 8);
		assertTrue(mimic.calculateDefence() < 8);
		assertTrue(mimic.calculateDefence() < 8);
		assertTrue(mimic.calculateDefence() < 8);
		
		mp.setDefence(1);
		assertTrue(mp.calculateDefence() < 7);
		assertTrue(mp.calculateDefence() < 7);
		assertTrue(mp.calculateDefence() < 7);
		assertTrue(mp.calculateDefence() < 7);
		assertTrue(mp.calculateDefence() < 7);
		
		pikachu.setDefence(5);
		assertTrue(pikachu.calculateDefence() < 8);
		assertTrue(pikachu.calculateDefence() < 8);
		assertTrue(pikachu.calculateDefence() < 8);
		assertTrue(pikachu.calculateDefence() < 8);
		assertTrue(pikachu.calculateDefence() < 8);
		
		treant.setDefence(4);
		assertTrue(treant.calculateDefence() < 7);
		assertTrue(treant.calculateDefence() < 7);
		assertTrue(treant.calculateDefence() < 7);
		assertTrue(treant.calculateDefence() < 7);
		assertTrue(treant.calculateDefence() < 7);
		
		uw.setDefence(5);
		assertTrue(uw.calculateDefence() < 8);
		assertTrue(uw.calculateDefence() < 8);
		assertTrue(uw.calculateDefence() < 8);
		assertTrue(uw.calculateDefence() < 8);
		assertTrue(uw.calculateDefence() < 8);
	}
	
	/**
	 * Method:  testMUDEntityMethods() 
	 * Purpose: It tests the methods obtained from MudEntity for mobs.
	 */
	
	@Test
	public void testMUDEntityMethods() {
		assertTrue(!rabbit.canGrab());
		assertTrue(rabbit.isTraversable());
		assertTrue(rabbit.isStackable());
		
		assertTrue(!demon.canGrab());
		assertTrue(demon.isTraversable());
		assertTrue(demon.isStackable());
		
		assertTrue(!diablo.canGrab());
		assertTrue(diablo.isTraversable());
		assertTrue(diablo.isStackable());
		
		assertTrue(!goblin.canGrab());
		assertTrue(goblin.isTraversable());
		assertTrue(goblin.isStackable());
		
		assertTrue(!insect.canGrab());
		assertTrue(insect.isTraversable());
		assertTrue(insect.isStackable());
		
		assertTrue(!mimic.canGrab());
		assertTrue(mimic.isTraversable());
		assertTrue(mimic.isStackable());
		
		assertTrue(!mp.canGrab());
		assertTrue(mp.isTraversable());
		assertTrue(mp.isStackable());
		
		assertTrue(!pikachu.canGrab());
		assertTrue(pikachu.isTraversable());
		assertTrue(pikachu.isStackable());
		
		assertTrue(!uw.canGrab());
		assertTrue(uw.isTraversable());
		assertTrue(uw.isStackable());
		
		assertTrue(!treant.canGrab());
		assertTrue(treant.isTraversable());
		assertTrue(treant.isStackable());
	}
}
