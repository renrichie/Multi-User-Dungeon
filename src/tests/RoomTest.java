package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Room;
import model.Tile;

/**
 * Author:   Richie Ren
 * File:     RoomTest.java
 * Purpose:  This is a unit test for Room.
 */

public class RoomTest {
	
	/**
	 * Method:  testCanTraversableAndGetTile() 
	 * Purpose: It tests the canTraverse() method.
	 */
	
	@Test
	public void testIsTraversableAndGetTile() {
		
		Tile[][] board = new Tile[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = new Tile();
			}
		}
		Room r = new Room(board);
		
		boolean allTraversable = true;
		
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (!r.getTile(i, j).canTraverse()) {
					allTraversable = false;
				}
			}
		}
		
		assertTrue(r.getTile(-1, -1) == null);
		assertTrue(r.getTile(4, 6) == null);
		assertTrue(r.getTile(6, 4) == null);
		assertTrue(allTraversable);
		
		Tile[][] board2 = null;
		Room r2 = new Room(board2);
		assertTrue(r2.getTile(1, 1) == null);
	}
	
	/**
	 * Method:  testGetTile() 
	 * Purpose: It tests to see if the method correctly returns the tile at the specified location.
	 */
	
	@Test
	public void testGetEntities() {

		Tile[][] board = new Tile[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = new Tile();
			}
		}
		Room r = new Room(board);
		
		assertEquals(0,r.getEntities().size());
	}
	
	/**
	 * Method:  testLookUpAndDown() 
	 * Purpose: It tests to see if the method correctly returns what's on the ceiling and on the floor.
	 */
	
	@Test
	public void testLookUpAndDown() {
		Tile[][] board = new Tile[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = new Tile();
			}
		}
		Room r = new Room(board);
		
		assertTrue(r.lookUp().equals("YOU SEE A CEILING!!!"));
		assertTrue(r.lookDown().equals("YOU SEE A FLOOR!!!"));
	}
	
	/**
	 * Method:  testCalculateExits() 
	 * Purpose: It tests to see if the method correctly returns the number of exits to the room.
	 */
	
	@Test
	public void testCalculateExits() {
		Tile[][] board = new Tile[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = new Tile();
			}
		}
		Room r = new Room(board);
		assertTrue(r.calculateExits().equals("There are 16 exits in this room.\n"));
	}
	
	/**
	 * Method:  testToString() 
	 * Purpose: It tests to see if the method correctly returns textual representation.
	 */
	
	@Test
	public void testToString() {
		Tile[][] board = new Tile[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = new Tile();
			}
		}
		Room r = new Room(board);
		
		//(Number of elements in the row * 2 (because there is the element and then a whitespace) + 1 (because of the new line character)) * Number of rows
		assertEquals((5 * 2 + 1) * 5, r.toString().length());
	}
	
	/**
	 * Method:  testGetBoard() 
	 * Purpose: It tests to see if the method correctly returns the 2D array of the board.
	 */
	
	@Test
	public void testGetBoard() {
		Tile[][] board = new Tile[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = new Tile();
			}
		}
		Room r = new Room(board);
		
		char [][] test = r.getBoard();
		assertEquals(5, test[0].length);
	}
}
