package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * this class creates a 2d array of tiles. It is worth mentioning that in a room
 * the width and the height must be the same.
 * @author andrewheyer
 *
 */
public class Room implements Serializable{
	
	private Tile[][] room;
	public static final int ROOM_SIZE = 5;
	private String upward = "YOU SEE A CEILING!!!";
	private String downward = "YOU SEE A FLOOR!!!";
	
	public Room(Tile[][] tiles){
		this.room = tiles;
	}
	
	/**
	 * Method:  getTile(int x, int y) 
	 * Purpose: It returns the Tile object at the specified location.
	 */
	
	public Tile getTile(int x, int y)
	{
		if (room == null)
			return null;
		else if (x >= room.length)
			return null;
		else if (y >= room[Math.abs(x)].length)
			return null;
		else if (x < 0 || y < 0)
			return null;
		else if (room[x] == null)
			return null;
		else
		{
			return room[x][y];
		}
	}
	
	/**
	 * Method:  getEntities() 
	 * Purpose: It returns a Collection containing all entities in the room.
	 */
	
	public Collection<MudEntity> getEntities()
	{
		ArrayList<MudEntity> lst = new ArrayList<MudEntity>();
		
		for (int i = 0; i < ROOM_SIZE; i++) 
			for (int j = 0; j < ROOM_SIZE; j++) 
			{
				lst.addAll(room[i][j].getEntities());
			}
		return lst;
	}
	
	/**
	 * Method:  lookUp() 
	 * Purpose: It returns the String on the ceiling of the room.
	 */
	
	public String lookUp()
	{
		return upward;
	}
	
	/**
	 * Method:  lookDown() 
	 * Purpose: It returns the String on the floor of the room.
	 */
	
	public String lookDown()
	{
		return downward;
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the textual representation of the Room.
	 */
	
	@Override
	public String toString()
	{
		String s = "";
		for (int y = 0; y < this.room.length; y++)
		{
			for (int x = 0; x < this.room[0].length; x++)
			{
				s += room[x][y].toString() + " ";
			}
			s += "\n";
		}
		return s;
	}
	
	/**
	 * Method:  getBoard() 
	 * Purpose: It returns a 2D array of the Room.
	 */
	
	public char[][] getBoard()
	{
		char [][] tiles = new char[5][5];
		for (int y = 0; y < this.room.length; y++)
		{
			for (int x = 0; x < this.room[0].length; x++)
			{
				tiles[x][y] = room[x][y].toString().charAt(0);
			}
		}
		return tiles;
	}
	
	/**
	 * Method:  calculateExits() 
	 * Purpose: It returns a String containing the amount of exits there are in the room.
	 */
	
	public String calculateExits() {
		
		if (room == null) 
			return "";
		
		String result = "There are ";
		
		int count = 0;
		
		//Counts on the top and bottom rows, excluding the first and last tile of the rows
		for (int i = 1; i < Room.ROOM_SIZE - 1; i++) {
			if (room[0][i].canTraverse()) {
				count++;
			}
			if (room[Room.ROOM_SIZE - 1][i].canTraverse()) {
				count++;
			}
		}
		
		//Counts the first and last columns
		for (int i = 0; i < Room.ROOM_SIZE; i++) {
			if (room[i][0].canTraverse()) {
				count++;
			}
			if (room[i][Room.ROOM_SIZE - 1].canTraverse()) {
				count++;
			}
		}
		
		return result + count + " exits in this room.\n";
	}
}
