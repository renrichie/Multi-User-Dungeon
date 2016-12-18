package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Door;
import model.Player;
import model.background.*;
import model.item.Sword;
import model.mob.Pikachu;

/**
 * Author:   Richie Ren
 * File:     MudEntityTest.java
 * Purpose:  This is a unit test for the tiles that extend MudEntity.
 */

public class MudEntityTest {

	private Water w = new Water();
	private Road r = new Road();
	private Wall wa = new Wall();
	private Door d = new Door();
	

	/**
	 * Method:  testToString() 
	 * Purpose: It tests to see if the MudEntity correctly represents itself as string.
	 */
	
	@Test
	public void testToString() {
		assertTrue(w.toString().equals("~"));
		assertTrue(r.toString().equals("="));
		assertTrue(wa.toString().equals("+"));
		assertTrue(d.toString().equals("#"));
		d.use();
		assertTrue(d.toString().equals("-"));
		d.use();
		assertTrue(d.toString().equals("#"));
		assertTrue(new Sword().toString().equals("s"));
		assertTrue(new Pikachu().toString().equals("U"));
		assertTrue(new Player(10,10,10,"Bob").toString().equals("p"));
	}
	
	/**
	 * Method:  testGetDescription() 
	 * Purpose: It tests to see if the MudEntity correctly describes itself.
	 */
	
	@Test
	public void testGetDescription() {
		assertTrue(w.getDescription().equals("Is a body of water that acts to impede the movement of mobs and players alike"));
		assertTrue(r.getDescription().equals("Is a road that can be traversed by both mobs and players"));
		assertTrue(wa.getDescription().equals("Is a wall that acts as a border to contain mobs and players within"));
		assertTrue(d.getDescription().equals("A wooden door"));
	}
	
	/**
	 * Method:  testEquals() 
	 * Purpose: It tests to see if the method correctly checks if two MudEntities are the same.
	 */
	
	@Test
	public void testEquals() {
		assertTrue(!w.equals(r));
		assertTrue(w.compareTo(r) == 0);
		assertTrue(!w.equals(wa));
		assertTrue(w.compareTo(wa) == 0);
		assertTrue(w.compareTo(w) == 0);

		assertTrue(!r.equals(w));
		assertTrue(!r.equals(wa));
		
		assertTrue(!wa.equals(r));
		assertTrue(!wa.equals(w));
		
		assertTrue(!w.equals(new Sword()));
	}
	
	/**
	 * Method:  testIsDestroyed() 
	 * Purpose: It tests to see if the method correctly destroys the tile.
	 */
	
	@Test
	public void testIsDestroyed() {
		Water water = new Water();
		assertTrue(!water.isDestroyed());
		
		water.destroy();
		
		assertTrue(water.isDestroyed());
	}
}
