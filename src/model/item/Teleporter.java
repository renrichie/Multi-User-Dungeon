package model.item;

import java.util.Random;

import model.MudEntity;
import model.MudModelIntf;
import model.Room;

/**
 * Author:   Richie Ren
 * File:     Teleporter.java
 * Purpose:  The Teleporter class is used to represent an item that randomly teleports the player.
 */

public class Teleporter extends Item {

	public Teleporter() {
		super("Teleporter", "This is a futuristic device capable of moving its user to a random location.");
	}

	/**
	 * Method:  use(MudEntity target) 
	 * Purpose: Stub method.
	 *          Returns true since the action was successful.
	 */

	@Override
	public boolean use(MudEntity target) {
		return true;
	}
	
	/**
	 * Method:  use(MudEntity target, MudModelIntf model) 
	 * Purpose: It randomly moves the user.
	 *          Returns true since the action was successful.
	 */
	
	public boolean use(MudEntity target, MudModelIntf model) {
		
		Random rng = new Random();
		int randomRoomX = rng.nextInt(Room.ROOM_SIZE);
		int randomRoomY = rng.nextInt(Room.ROOM_SIZE);
		int randomTileX = rng.nextInt(Room.ROOM_SIZE);
		int randomTileY = rng.nextInt(Room.ROOM_SIZE);
		
		while (!model.getRoom(randomRoomX, randomRoomY).getTile(randomTileX, randomTileY).canTraverse()) {
			randomRoomX = rng.nextInt(Room.ROOM_SIZE);
			randomRoomY = rng.nextInt(Room.ROOM_SIZE);
			randomTileX = rng.nextInt(Room.ROOM_SIZE);
			randomTileY = rng.nextInt(Room.ROOM_SIZE);
		}
		
		//Spawns the player at the spawn location
		target.getTile().removeEntity(target);
		model.getRoom(randomRoomX, randomRoomY).getTile(randomTileX, randomTileY).addEntity(target);
		target.getLocation().getRoom().setLocation(randomRoomX, randomRoomY);
		target.getLocation().getTile().setLocation(randomTileX, randomTileY);
		
		return true;
	}
	
	/**
	 * Method:  isConsumable() 
	 * Purpose: It returns a boolean indicating if the item is a consumable.
	 */
	
	@Override
	public boolean isConsumable()
	{
		return true;
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		return "y";
	}
}
