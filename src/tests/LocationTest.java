package tests;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import model.Location;

/**
 * Author:   Richie Ren
 * File:     LocationTest.java
 * Purpose:  This is a unit test for Location.
 */

public class LocationTest {
	
	/**
	 * Method:  testGetRoomAndTile() 
	 * Purpose: It tests to see if the method correctly returns the location of the current room and tile.
	 */
	
	@Test
	public void testGetRoomAndTile() {

		Location l = new Location();
		Point room = l.getRoom();
		Point tile = l.getTile();
		
		assertTrue(room != null);
		assertTrue(room.x == 0);
		assertTrue(room.y == 0);
		assertTrue(tile != null);
		assertTrue(tile.x == 0);
		assertTrue(tile.y == 0);
		
		Location l2 = new Location(new Point(1,1), new Point(2,2));
		Point room2 = l2.getRoom();
		Point tile2 = l2.getTile();
		
		assertTrue(room2 != null);
		assertTrue(room2.x == 2);
		assertTrue(room2.y == 2);
		assertTrue(tile2 != null);
		assertTrue(tile2.x == 1);
		assertTrue(tile2.y == 1);
		
		assertTrue(l2.toString().equals("Tile coordinate: (1,1)" + " Room coordinate: (2,2)"));
	}
	
	/**
	 * Method:  testToString() 
	 * Purpose: It tests to see if the method correctly returns the location of the current room and tile as a string.
	 */
	
	@Test
	public void testToString() {
		Location l = new Location(new Point(1,1), new Point(2,2));
		assertTrue(l.toString().equals("Tile coordinate: (1,1)" + " Room coordinate: (2,2)"));
	}
}
