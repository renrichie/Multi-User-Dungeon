package model;

import java.io.Serializable;

/**
 * This class contains a 5 X 5 map of the 'Room' class. This is 
 * the whole entity of the game. The gameBoard will be the main
 * controller of the game so that there will be no nested method
 * calling to make changes to the game. Moving, setting, knowing
 * related information should all be contained in the gameBoard
 * - Andrew Heyer
 */
public class GameBoard implements Serializable {
	
	private Room[][] gameBoard;
	public final int BOARD_SIZE; // rooms go from (0,0) to (9,9)

	public GameBoard(Room[][] rooms){
		this.BOARD_SIZE = rooms.length;
		this.gameBoard = rooms;
	}
	
	/**
	 * Method:  getRoom(int x, int y) 
	 * Purpose: It returns the room on the map at the specified location.
	 */
	
	public Room getRoom(int x, int y)
	{
		if (gameBoard == null)
			return null;
		else if (gameBoard[0] == null)
			return null;
		else if (x >= gameBoard.length || y >= gameBoard[0].length)
			return null;
		else
		{
			return gameBoard[x][y];
		}
	}
	
	/**
	 * Method:  getBoard() 
	 * Purpose: It returns the map as a 2D array of Rooms.
	 */
	
	public Room[][] getBoard() {
		return gameBoard;
	}
		
	/**
	 * Method:  toString() 
	 * Purpose: It prints out the textual representation of the entire map.
	 */
	
	@Override
	public String toString()
	{
		String s = "";
		String[][][] roomStrings = new String[gameBoard.length][gameBoard[0].length][]; 
		for (int x = 0; x <this.gameBoard.length; x++)
			for (int y = 0; y <this.gameBoard[0].length; y++)
				roomStrings[x][y] = gameBoard[x][y].toString().split("\n");
		
		for (int z = 0; z < roomStrings.length; z++)
		{
			for (int y = 0; y < roomStrings[z][0].length; y++)
			{
				for (int x = 0; x < roomStrings[0].length; x++)
				{
					s += roomStrings[x][z][y];
				}
				s += "\n";
			}
		}
		return s;
	}
	
}
