package model.mob;

import java.util.ArrayList;


import model.MoveDirection;
import model.MudEntity;
import model.MudModelIntf;
import model.Player;
import model.Room;
import model.Tile;

import model.item.Item;

/**
 * Author:   Richie Ren
 * File:     Mob.java
 * Purpose:  The Mob class represents a monster in the game.
 *           A monster has a specified amount of health, attack, and defence stats.
 *           The attack and defence calculations will differ from monster to monster, as their level/difficulty will be different.
 *           The same goes for their item drops and movement AI.
 */

public abstract class Mob extends MudEntity
{
	private int health, attack, defence;
	private double updateChance;
	private String mobName, description;
	
	public Mob(String mobName, String description, int health, int attack, int defence) {
		super();
		this.mobName = mobName;
		this.description = description;
		this.health = health;
		this.attack = attack;
		this.defence = defence;
	}
	
	public Mob(String mobName, String description, int health, int attack, int defence, boolean grab, boolean traverse, boolean stack) {
		super(grab, traverse, stack);
		this.mobName = mobName;
		this.description = description;
		this.health = health;
		this.attack = attack;
		this.defence = defence;
	}
	
	/**
	 * Method:  getMobName() 
	 * Purpose: It returns the string containing the mob's name.
	 */
	
	public String getMobName() {
		return this.mobName;
	}
	
	/**
	 * Method:  getDescription() 
	 * Purpose: It returns the string containing the mob's description.
	 */
	
	@Override
	public String getDescription() {
		return this.toString() + " - (" + this.getMobName() + ") " + this.description;
	}
	
	/**
	 * Method:  getHealth() 
	 * Purpose: It returns the health of the monster. Monsters only have one health variable, as their max health will not change, most likely.
	 */
	
	public int getHealth() {
		return health;
	}
	
	/**
	 * Method:  getAttack() 
	 * Purpose: It returns the attack of the monster.
	 */
	
	public int getAttack() {
		return attack;
	}
	
	/**
	 * Method:  getDefence() 
	 * Purpose: It returns the defence of the monster.
	 */
	
	public int getDefence() {
		return defence;
	}
	
	/**
	 * Method:  setHealth(int newHealth) 
	 * Purpose: It sets the health of the monster. It is used for when the monster takes damage.
	 */
	
	public void setHealth(int newHealth) {
		this.health = newHealth;
	}
	
	/**
	 * Method:  setAttack(int newAttack) 
	 * Purpose: It sets the attack of the monster to the specified amount.
	 */
	
	public void setAttack(int newAttack) {
		this.attack = newAttack;
	}
	
	/**
	 * Method:  setDefence(int newDefence) 
	 * Purpose: It sets the defence of the monster to the specified amount.
	 */
	
	public void setDefence(int newDefence) {
		this.defence = newDefence;
	}

	/**
	 * Method:  move(MudModelIntf model) 
	 * Purpose: It is the AI for moving the monster randomly.
	 */
	
	private void move(MudModelIntf model) {
		double result = Math.random();
		
		if (result < 0.25) {
			model.moveEntity(this, MoveDirection.NORTH);
		}
		else if (result >= 0.25 && result < 0.5) {
			model.moveEntity(this, MoveDirection.EAST);
		}
		else if (result >= 0.5 && result < 0.75) {
			model.moveEntity(this, MoveDirection.SOUTH);
		}
		else {
			model.moveEntity(this, MoveDirection.WEST);
		}
	}
	
	/**
	 * Method:  attack(MudModelIntf model) 
	 * Purpose: It attacks a player if there is one in an adjacent or diagonal tile.
	 */
	
	private void attack(MudModelIntf model) {
		
		//The locations of the adjacent tiles
		int currentBoardX = this.getLocation().getRoom().x;
		int currentBoardY = this.getLocation().getRoom().y;

		int currentRoomX = this.getLocation().getTile().x;
		int currentRoomY = this.getLocation().getTile().y;
		
		int north = currentRoomY - 1;
		int east = currentRoomX + 1;
		int south = currentRoomY + 1;
		int west = currentRoomX - 1;
		
		Room currentRoom = model.getRoom(currentBoardX, currentBoardY);
		if (currentRoom == null) return;
		
		/*            0 1 2
		 *         0  + + +
		 *         1  + M +
		 *         2  + + +
		 */
		
		ArrayList<MudEntity> entities = new ArrayList<>();
		
		//Finds all entities adjacent to the MudEntity's current tile including diagonals
		if (north >= 0) {
			Tile tile = currentRoom.getTile(currentRoomX, north);
			entities.addAll(tile.getEntities());
		}
		if (east < Room.ROOM_SIZE) {
			Tile tile = currentRoom.getTile(east, currentRoomY);
			entities.addAll(tile.getEntities());
		}
		if (south < Room.ROOM_SIZE) {
			Tile tile = currentRoom.getTile(currentRoomX, south);
			entities.addAll(tile.getEntities());
		}
		if (west >= 0) {
			Tile tile = currentRoom.getTile(west, currentRoomY);
			entities.addAll(tile.getEntities());
		}
		if (north >= 0 && west >= 0) {
			Tile tile = currentRoom.getTile(west, north);
			entities.addAll(tile.getEntities());
		}
		if (north >= 0 && east < Room.ROOM_SIZE) {
			Tile tile = currentRoom.getTile(east, north);
			entities.addAll(tile.getEntities());
		}
		if (south < Room.ROOM_SIZE && west >= 0) {
			Tile tile = currentRoom.getTile(west, south);
			entities.addAll(tile.getEntities());
		}
		if (south < Room.ROOM_SIZE && east < Room.ROOM_SIZE) {
			Tile tile = currentRoom.getTile(east, south);
			entities.addAll(tile.getEntities());
		}

		//Adds the current tile
		entities.addAll(currentRoom.getTile(currentRoomX, currentRoomY).getEntities());
		
		//Attacks the first player it finds in the list of entities
		for (MudEntity m : entities) {
			if (m instanceof Player) {
				Player player = (Player) m;
				
				//Calculates the damage done
				int damage = this.calculateAttack() - player.calculateDefence();
				
				if (damage <= 0) {
					if (!(m instanceof Rabbit) || !(m instanceof Insect)) {
						damage = 1;
					}
					else {
						damage = 0;
					}
				}
				
				player.setCurrentHealth(player.getCurrentHealth() - damage);
				
				if (player.getCurrentHealth() <= 0) {
					player.setCurrentHealth(0);
					model.dropInventory(player);
				}
			}
		}
	}
	
	/**
	 * Method:  update() 
	 * Purpose: This is the AI.
	 */
	
	public void update(MudModelIntf model) {
		//Mob randomly attacks or moves
		if (Math.random() <= getUpdateChance()) {
			if (Math.random() <= 0.3) {
				attack(model);
			}
			else {
				move(model);
			}
		}
	}
	
	/**
	 * Method:  getUpdateChance() 
	 * Purpose: It returns the update chance of the mob.
	 */
	
	public double getUpdateChance() {
		return updateChance;
	}
	
	/**
	 * Method:  getUpdateChance(int newUpdateChance) 
	 * Purpose: It sets the update chance of the monster to the specified amount.
	 */
	
	public void setUpdateChance(double newUpdateChance) {
		this.updateChance = newUpdateChance;
	}
	
	/**
	 * Method:  calculateAttack() 
	 * Purpose: It returns the amount of damage that the monster does.
	 */
	
	public abstract int calculateAttack();
	
	/**
	 * Method:  calculateDefence() 
	 * Purpose: It returns the amount of damage that the monster blocks.
	 */
	
	public abstract int calculateDefence();
	
	/**
	 * Method:  generateItemDrops() 
	 * Purpose: It returns the item that the monster drops.
	 */
	
	public abstract Item generateItemDrops();
}
