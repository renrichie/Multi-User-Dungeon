package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Player;
import model.item.Mastersword;
import model.item.NoShield;
import model.item.Sword;
import model.item.Unarmed;
import model.item.WoodenShield;

/**
 * Author:   Richie Ren
 * File:     PlayerTest.java
 * Purpose:  This is a unit test for Player.
 */

public class PlayerTest {

	/**
	 * Method:  testGetMaxHealth() 
	 * Purpose: It tests the getMaxHealth() method. It should return 10.
	 */
	
	@Test
	public void testGetMaxHealth() {
		Player p = new Player(10,10,10,"Bob");
		assertEquals(10, p.getMaxHealth());
	}

	/**
	 * Method:  testSetMaxHealth() 
	 * Purpose: It tests the set and getMaxHealth() methods.
	 */
	
	@Test
	public void testSetMaxHealth() {
		Player p = new Player(10,10,10,"Bob");
		p.setMaxHealth(666);
		assertEquals(666, p.getMaxHealth());
	}
	
	/**
	 * Method:  testGetCurrentHealth() 
	 * Purpose: It tests the getCurrentHealth() method. It should return 10.
	 */
	
	@Test
	public void testGetCurrentHealth() {
		Player p = new Player(10,10,10,"Bob");
		assertEquals(10, p.getCurrentHealth());
	}

	/**
	 * Method:  testSetCurrentHealth() 
	 * Purpose: It tests the set and getCurrentHealth() methods.
	 */
	
	@Test
	public void testSetCurrentHealth() {
		Player p = new Player(10,10,10,"Bob");
		p.setCurrentHealth(666);
		assertEquals(666, p.getCurrentHealth());
	}
	
	/**
	 * Method:  testGetAttack() 
	 * Purpose: It tests the getAttack() method. It should return 10.
	 */
	
	@Test
	public void testGetAttack() {
		Player p = new Player(10,10,10,"Bob");
		assertEquals(10, p.getAttack());
	}

	/**
	 * Method:  testSetAttack() 
	 * Purpose: It tests the setAttack() method.
	 */
	
	@Test
	public void testSetAttack() {
		Player p = new Player(10,10,10,"Bob");
		p.setAttack(666);
		assertEquals(666, p.getAttack());
	}
	
	/**
	 * Method:  testGetDefence() 
	 * Purpose: It tests the getDefence() method. It should return 10.
	 */
	
	@Test
	public void testGetDefence() {
		Player p = new Player(10,10,10,"Bob");
		assertEquals(10, p.getDefence());
	}

	/**
	 * Method:  testSetDefence() 
	 * Purpose: It tests the setDefence() method. It should return 10.
	 */
	
	@Test
	public void testSetDefence() {
		Player p = new Player(10,10,10,"Bob");
		p.setDefence(666);
		assertEquals(666, p.getDefence());
	}
	
	/**
	 * Method:  testGetIGN() 
	 * Purpose: It tests the getIGN() method.
	 */
	
	@Test
	public void testGetIGN() {
		Player p = new Player(10,10,10,"Bob");
		assertTrue(p.getIGN().equals("Bob"));
	}
	
	/**
	 * Method:  testGetDescription() 
	 * Purpose: It tests the getDescription() method.
	 */
	
	@Test
	public void testGetDescription() {
		Player p = new Player(10,10,10,"Bob");
		assertTrue(p.getDescription().equals("Bob\nHP: 10/10\nWeapon: Unarmed\nShield: No Shield\n"));
	}
	
	/**
	 * Method:  testCalculateAttack() 
	 * Purpose: It tests the calculateAttack() method.
	 */
	
	@Test
	public void testCalculateAttack() {
		Player p = new Player(10,10,10,"Bob");
		assertTrue(p.calculateAttack() < 8);
		assertTrue(p.calculateAttack() < 8);
		assertTrue(p.calculateAttack() < 8);
		assertTrue(p.calculateAttack() < 8);
		assertTrue(p.calculateAttack() < 8);
		assertTrue(p.calculateAttack() < 8);
	}
	
	/**
	 * Method:  testCalculateDefence() 
	 * Purpose: It tests the calculateDefence() method.
	 */
	
	@Test
	public void testCalculateDefence() {
		Player p = new Player(10,10,10,"Bob");
		assertTrue(p.calculateDefence() < 8);
		assertTrue(p.calculateDefence() < 8);
		assertTrue(p.calculateDefence() < 8);
		assertTrue(p.calculateDefence() < 8);
		assertTrue(p.calculateDefence() < 8);
		assertTrue(p.calculateDefence() < 8);
	}
	
	/**
	 * Method:  testGetInventoryAndAddToInventory() 
	 * Purpose: It tests the getInventory() and addToInventory() methods.
	 */
	
	@Test
	public void testGetInventoryAndAddToInventory() {
		Player p = new Player(10,10,10,"Bob");
		assertTrue(p.getInventory().size() == 0);
		p.addToInventory(new Sword());
		assertTrue(p.getInventory().size() == 1);
	}
	
	/**
	 * Method:  testGetAndSetWeapon() 
	 * Purpose: It tests the getWeapon() and setWeapon() methods.
	 */
	
	@Test
	public void testGetWeaponAndSetWeapon() {
		Player p = new Player(10,10,10,"Bob");
		assertTrue(p.getWeapon() instanceof Unarmed);
		p.setWeapon(new Mastersword());
		assertTrue(p.getWeapon() instanceof Mastersword);
	}
	
	/**
	 * Method:  testGetAndSetShield() 
	 * Purpose: It tests the getShield() and setShield() methods.
	 */
	
	@Test
	public void testGetShieldAndSetShield() {
		Player p = new Player(10,10,10,"Bob");
		assertTrue(p.getShield() instanceof NoShield);
		p.setShield(new WoodenShield());
		assertTrue(p.getShield() instanceof WoodenShield);
	}
}
