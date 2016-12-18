package model;

/**
 * Author:   Richie Ren
 * File:     Door.java
 * Purpose:  The Door class represents a door entity in game.
 *           It can be opened and closed, and its character representation changes when used.
 */

public class Door extends MudEntity {

	private boolean open;
	
	public Door() {
		super(false, false, true);
		open = false;
	}
	
	/**
	 * Method:  getDescription() 
	 * Purpose: It returns the description of the entity.
	 */
	
	@Override
	public String getDescription() {
		return "A wooden door";
	}

	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString() {
		if (!open) { 
			return "#";
		}
		else {
			return "-";
		}
	}
	
	/**
	 * Method:  use() 
	 * Purpose: It opens or closes the door.
	 */
	
	public boolean use() {
		this.open = !open;
		super.setTraversable(this.open);
		
		return this.open;
	}
}
