package model;

import java.awt.Point;
import java.io.Serializable;

/**
 * This class is for simplifying the player moving. We need to be able to
 * move players across a room and also from room to room. In order to do 
 * this with a board that is a room which is 10 x 10 and each room consists
 * of 5 x 5 tiles, and ALSO be able to change the room and board dimensions 
 * I make a "player point class". This will get the dimensions from the other 
 * classes and make moving a player done accordingly. This will also decouple
 * moving a player logic from the board. The only error checking done in this
 * is to make sure that the player is moved within the boundaries of the board
 * it does not care of a tile is not traversable
 * - Andrew
 */
public class Location implements Serializable {
	// the coordinate for which room of the board you are in
	private Point room;
	// the coordinate for which tile of a room you are on
	private Point tile;

	
	public Location() {
		// Be default set the location of a new player to (0,0), of (0,0)
		tile = new Point(0,0);
		room = new Point(0,0);
	}
		// alternative constructor, also used by 'movePlayer' method
	public Location(Point tile, Point room){
		this.tile = tile;
		this.room = room;
	}
	
	/**
	 * Method:  getTile() 
	 * Purpose: It returns a Point object containing the location of the current tile in the room.
	 */
	
	public Point getTile() {
		return tile;
	}
	
	/**
	 * Method:  getRoom() 
	 * Purpose: It returns a Point object containing the location of the current room on the map.
	 */
	
	public Point getRoom() {
		return room;
	}

	/**
	 * Method:  toString() 
	 * Purpose: It returns the textual representation of the location.
	 */
	
	@Override
	public String toString(){
		return ("Tile coordinate: (" + (int)tile.getX() + "," + (int)tile.getY() + ")" +
				" Room coordinate: (" + (int)room.getX() + "," + (int)room.getY() + ")");
	}
}
