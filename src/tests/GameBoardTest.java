package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.GameBoard;
import model.Room;
import model.Tile;
import model.util.worldgen.WorldGen;

/**
 * Author:   Andrew Heyer
 * File:     GameBoardTest.java
 * Purpose:  This is a unit test for GameBoard.
 */

public class GameBoardTest {

	
	/*
	 * Lets test basic room creation and coordinate testing
	 * to make sure our for loop coordinate logic makes sense.
	 * In other words the board should be as such.
	 * 
	 * (0,0) ...... (10,0)
	 * .
	 * .
	 * (0,10).......(10,10)
	 * 
	 * In order to test for these I imagine we would need to set 
	 * "flags" and printing of the board to see if all checks out.
	 * I will be printing a board of 0's if a tile is traversable and 
	 * 1 if not. 
	 * - Andrew Heyer
	 */
	
	@Test
	public void testBoardCreationAndSize() {
		GameBoard gb = null;
		assertTrue(gb == null);
		
		Room[][] board = new Room[Room.ROOM_SIZE][Room.ROOM_SIZE];
		
		Tile[][] room = new Tile[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				room[i][j] = new Tile();
			}
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = new Room(room);
			}
		}
		
		gb = new GameBoard(board);
		assertTrue(gb != null);
	}
	
	@Test
	public void testGameBoard() {
		Room[][] rooms = null;
		Room[][] board = new Room[Room.ROOM_SIZE][Room.ROOM_SIZE];

		Tile[][] room = new Tile[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				room[i][j] = new Tile();
			}
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				board[i][j] = new Room(room);
			}
		}
		
		GameBoard gb = new GameBoard(board);
		
		GameBoard gb2 = new GameBoard(gb.getBoard());
		assertTrue(gb2 != null);
		
		Room r = gb2.getRoom(2, 2);
		assertTrue(r != null);
		assertTrue(gb2.getRoom(4, 9) == null);
	}
	
	@Test
	public void testGameBoardToString(){
		WorldGen gen = WorldGen.getWorldGen(WorldGen.FILENAME1);
		GameBoard board = gen.generateGameBoard();
		//System.out.println(board.toString());
		String r = "+ + + + + + + + + + + + + + + + + + + + + + + + + + + + + + \n"
				+ "+ = = = = = = = = = = t = + = = = = t + ~ ~ ~ ~ ~ ~ ~ ~ ~ + \n"
				+ "+ = w = = = = = = = = = = # = = = = t + ~ ~ ~ ~ ~ ~ ~ ~ ~ + \n"
				+ "+ s R = = = o = l w = + + + + + # + + + ~ ~ ~ ~ ~ ~ ~ ~ ~ + \n"
				+ "+ = = = = = = = = = = + o l + + = = = ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ + \n"
				+ "+ = = = = = = = = = = + T = + + = = = ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ + \n"
				+ "+ = = = = = = = = = = + = = + + = = R = = = = = o ~ ~ ~ ~ + \n"
				+ "+ T = = = s = = = + + + = = + + + = = ~ = = ~ ~ ~ ~ ~ ~ ~ + \n"
				+ "+ R = = = w = = = = = # = = = = # = = ~ = = = = = = ~ ~ ~ + \n"
				+ "+ ~ ~ ~ ~ ~ ~ ~ = + + + = = + + + ~ = ~ ~ ~ ~ ~ ~ = ~ ~ ~ + \n"
				+ "+ ~ ~ ~ ~ = = = = w = w = ~ + + ~ ~ = ~ ~ ~ ~ ~ ~ = = = = + \n"
				+ "+ ~ ~ ~ ~ = = ~ ~ ~ ~ + + + + + ~ ~ = ~ ~ ~ ~ ~ ~ ~ = l = + \n"
				+ "+ + + + + = = ~ ~ ~ = + ~ ~ + + ~ ~ = ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ + \n"
				+ "+ + + + + = l = l = = + ~ ~ + + ~ ~ = ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ + \n"
				+ "+ + + + + = + + + + = = + + + + ~ ~ = ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ + \n"
				+ "+ + + + + = + + + + + = = = = = ~ ~ = + + + + + + + + + + + \n"
				+ "+ ~ ~ = = = ~ ~ ~ ~ ~ ~ ~ ~ E = ~ ~ = ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ + \n"
				+ "+ ~ = = = = ~ ~ ~ ~ ~ ~ ~ ~ = = = = l ~ ~ ~ ~ ~ ~ ~ ~ ~ = + \n"
				+ "+ ~ = ~ ~ = = = ~ ~ ~ + + + + + ~ ~ = ~ ~ ~ ~ ~ ~ ~ ~ = = + \n"
				+ "+ ~ = ~ ~ = ~ = ~ ~ ~ + ~ ~ + + ~ ~ = ~ ~ ~ ~ ~ ~ ~ = = = + \n"
				+ "+ ~ = = T = ~ = ~ ~ ~ + ~ = + + ~ ~ = ~ ~ ~ ~ ~ = = = t = + \n"
				+ "+ ~ = ~ = ~ ~ = ~ ~ ~ + ~ E + + ~ ~ = = = = = # = = = D = + \n"
				+ "+ = = = = ~ ~ = ~ ~ + + ~ = + + ~ ~ = = = = = # = = = = = + \n"
				+ "+ ~ = = o = T = o = = + ~ = + + ~ ~ = ~ ~ ~ ~ ~ = = = t = + \n"
				+ "+ ~ ~ ~ ~ ~ ~ ~ = + + + = = + + ~ ~ = ~ ~ ~ ~ ~ ~ ~ = = = + \n"
				+ "+ ~ ~ ~ ~ = = = = = = # = = + + ~ ~ = = = = = = = = ~ = = + \n"
				+ "+ ~ ~ ~ ~ = = ~ ~ ~ ~ + + + + + ~ ~ = = = = = o = = ~ ~ = + \n"
				+ "+ + + + + = = = = = = + ~ ~ + + ~ ~ = = = = = = = = ~ ~ ~ + \n"
				+ "+ + + + + = = = R = = = = = = # = = = = = = = = = = ~ ~ ~ + \n"
				+ "+ + + + + + + + + + + + + + + + + + + + + + + + + + + + + + \n";
		assertEquals(r.length(), board.toString().length());
	}
	
}
