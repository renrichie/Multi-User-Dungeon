package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import model.background.Background;
import model.item.Item;
import model.item.NoShield;
import model.item.Polymorph;
import model.item.Shield;
import model.item.Teleporter;
import model.item.TreasureChest;
import model.item.Unarmed;
import model.item.Weapon;
import model.mob.Mimic;
import model.mob.Mob;
import model.util.worldgen.WorldGen;


/**
 * MudGame is an instance of MudModelIntf. It represents the
 * entire game state  of Mud and will drive all of the interactions
 * with the Mud Game.
 * @author Brian Lovelace
 *
 */
public class MudGame implements MudModelIntf {

	private GameBoard board;
	private WorldGen gen;
	private HashMap<UUID, MudEntity> entities;
	private volatile boolean active = true;
	private transient MudGameCleaner cleaner = null;
	
	public MudGame() 
	{
		this.gen = WorldGen.getWorldGen(WorldGen.FILENAME1);
		this.board = gen.generateGameBoard();
		this.entities = new HashMap<UUID, MudEntity>();
		populateEntities();
		cleaner = new MudGameCleaner();
		cleaner.start();
	}
	
	/**
	 * When a board if created for the first time, MudGame will not
	 * be tracking the entities on that board. This method interated
	 * through the entire board and logs each non-Background entity
	 * so that they may be updated later on. Backgrounds are not
	 * tracked because they have no AI to accompany them. 
	 * 
	 * Furthermore, populateEntities is responsible for ensuring that
	 * all entities have their locations set.
	 * 
	 * setTile() should not need to be called in this method as it is called
	 * whenever an entity is added to a tile, but feel free to add it later if
	 * lacking it causes problems.
	 * 
	 * Please forgive me for the ugly 5 level deep for-loop, but there are only
	 * so many ways to iterate through all these things while keeping track of the
	 * indices.
	 */
	private void populateEntities()
	{
		Room[][] rooms = board.getBoard();
		for (int y = 0; y < rooms.length; y++)
			for (int x = 0; x < rooms[y].length; x++)
				for (int k = 0; k < Room.ROOM_SIZE; k++)
					for (int j = 0; j < Room.ROOM_SIZE; j++)
					{
						Location loc = new Location(new Point(j, k),
						                            new Point(x, y));
						Tile tile = rooms[x][y].getTile(j, k);
						for (MudEntity e : tile.getEntities())
						{
							e.setLocation(loc);
							if (!(e instanceof Background))
								entities.put(e.UID, e);
						}
					}			
	}
	
	/**
	 * remove an entity, because we are keeping track of the UUID's in our Mudgame, we can take in an entity
	 * and remove it. Using the UID we can find where the location of the entity is, and what it is.
	 */
	@Override 
	public synchronized boolean removeEntity(MudEntity entity)
	{
		Location loc;
		Room r;
		Tile t;
		if (entity == null || !active) return false;
		else if(!entities.containsKey(entity.UID))
			return false;
		else
		{
			loc = entity.getLocation();
			if (loc == null) return false;
			else
			{
				r = board.getRoom(loc.getRoom().x, loc.getRoom().y);
				if (r ==  null) return false;
				t = r.getTile(loc.getTile().x, loc.getTile().y);
				if (t == null) return false;
				t.removeEntity(entity);
				entities.remove(entity.UID);
				return true;
			}
		}
	}
	
	/**
	 * Method:  addEntity(MudEntity entity, int roomx, int roomy, int tilex, int tiley) 
	 * Purpose: It adds the specified MudEntity to the hashmap at the specified coordinates.
	 */
	
	@Override
	public synchronized boolean addEntity(MudEntity entity, int roomx, int roomy, int tilex, int tiley)
	{
		Room room;
		Tile tile;
		if (entity == null || !active) return false;
		else if (entities.containsKey(entity.UID))
		{
			return false;
		}
		else
		{
			room = board.getRoom(roomx, roomy);
			tile = room.getTile(tilex, tiley);
			if (tile == null) return false;
			
			if (tile.addEntity(entity))
			{
				entities.put(entity.UID, entity);
				entity.setLocation(new Location(new Point(tilex, tiley),
				                                new Point(roomx, roomy)));
				return true;
			}
		}
		return false;
	}

	/**
	 * Method:  getPlayer(String username) 
	 * Purpose: It returns a Player object with the specified username.
	 */
	
	@Override
	public synchronized Player getPlayer(String username)
	{
		Player p = null;
		for (UUID key : entities.keySet()){
			MudEntity e = entities.get(key);
			if (e instanceof Player){
				if(((Player)e).getIGN().equals(username)){
					String 	current = ((Player)e).getIGN().toLowerCase(),
					   		usernameLowercase = username.toLowerCase();
					if(current.equals(usernameLowercase))
						p = (Player)(e);
				}
			}
		}
		
		return p;
	}

	/**
	 * Method:  getRoom(int x, int y) 
	 * Purpose: It returns the Room object at the specified location.
	 */
	
	@Override
	public Room getRoom(int x, int y)
	{
		if (board == null)
			return null;
		else
		{
			return board.getRoom(x, y);
		}
	}
	
	/**
	 * Method:  moveEntity(MudEntity entity, MoveDirection dir) 
	 * Purpose: It moves the MudEntity in the specified direction. It also handles movement across rooms.
	 */
	
	@Override
	public synchronized boolean moveEntity(MudEntity entity, MoveDirection dir){
		Room newRoom;
		Tile newTile;
		int currentBoardX = entity.getLocation().getRoom().x;
		int currentBoardY = entity.getLocation().getRoom().y;
		
		int currentRoomX = entity.getLocation().getTile().x;
		int currentRoomY = entity.getLocation().getTile().y;
		
		int newRoomX = currentRoomX, 
			newRoomY = currentRoomY, 
			newBoardX = currentBoardX, 
			newBoardY = currentBoardY;
		
		if (!entities.containsKey(entity.UID) || !active) return false; //Can't move an entity not tracked by MudGame
		
		Room oldRoom = this.getRoom(currentBoardX, currentBoardY);
		Tile oldTile = null;
		
		if (oldRoom != null)
			oldTile = oldRoom.getTile(currentRoomX, currentRoomY);
		
		if(dir == MoveDirection.NORTH){
			// North and south directional movements only change Y values. 
			newRoomY = currentRoomY - 1;
			newBoardY = currentBoardY;

			if(newRoomY<0){
				newBoardY--;
				newRoomY = Room.ROOM_SIZE - 1; 
			}

			if(newBoardY < 0){
				return false;
			}
		}
		else if (dir == MoveDirection.SOUTH){
			newRoomY = currentRoomY + 1;
			newBoardY = currentBoardY;

			if(newRoomY > Room.ROOM_SIZE - 1){
				newBoardY++;
				newRoomY = 0; 
			}
			
			if(newBoardY > board.BOARD_SIZE - 1){
				return false;
			}
		}
		else if (dir == MoveDirection.EAST){
			newRoomX = currentRoomX + 1;
			newBoardX = currentBoardX;
			
			if(newRoomX > Room.ROOM_SIZE - 1){
				newBoardX++;
				newRoomX = 0; 
			}
			
			if(newBoardX > board.BOARD_SIZE - 1){
				return false;
			}		
		} 
		else if (dir == MoveDirection.WEST){
			newRoomX = currentRoomX - 1;
			newBoardX = currentBoardX;
			
			if(newRoomX < 0){
				newBoardX--;
				newRoomX = Room.ROOM_SIZE - 1; 
			}
			
			if(newBoardX < 0){
				return false;
			}
		}
		
		newRoom = this.board.getRoom(newBoardX, newBoardY);
		if (newRoom == null) return false;
		newTile = newRoom.getTile(newRoomX, newRoomY);
		if (newTile.addEntity(entity))
		{
			if (oldTile != null)
				oldTile.removeEntity(entity);
			entity.setLocation(new Location(
			                                new Point(newRoomX, newRoomY),
			                                new Point(newBoardX, newBoardY)));
			return true;
		} 
		else
		{
			return false;
		}
	}
	
	/**
	 * Method:  getAdjacentEntities(MudEntity entity, boolean diagonal) 
	 * Purpose: It returns an ArrayList containing all MudEntities in the adjacent tiles, and even diagonal if specified.
	 */
	
	@Override
	public synchronized ArrayList<MudEntity> getAdjacentEntities(MudEntity entity, boolean diagonal)
	{
		
		if (entity == null || entity.getLocation() == null) return null;
		
		//The locations of the adjacent tiles
		int currentBoardX = entity.getLocation().getRoom().x;
		int currentBoardY = entity.getLocation().getRoom().y;

		int currentRoomX = entity.getLocation().getTile().x;
		int currentRoomY = entity.getLocation().getTile().y;

		int north = currentRoomY - 1;
		int east = currentRoomX + 1;
		int south = currentRoomY + 1;
		int west = currentRoomX - 1;

		Room currentRoom = this.getRoom(currentBoardX, currentBoardY);
		if (currentRoom == null) return null;

		ArrayList<MudEntity> entities = new ArrayList<>();

		//Finds all entities adjacent to the MudEntity's current tile
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

		if (diagonal) {
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
		}
		
		//Adds the current tile
		entities.addAll(currentRoom.getTile(currentRoomX, currentRoomY).getEntities());
		return entities;
	}
	
	/**
	 * Method:  grabItem(String item, Player player) 
	 * Purpose: It grabs the specified item if there is such one in the adjacent tiles to the Player.
	 */
	
	@Override
	public synchronized boolean grabItem(String item, Player player)
	{
		ArrayList<MudEntity> entities = this.getAdjacentEntities(player,false);
		if (entities == null) return false;
		
		//Grabs the first item it finds in the list of entities
		for (MudEntity m : entities) 
		{
			if (m instanceof Item)
			{
				Item itemfound = (Item)(m);
				if (item.toLowerCase().trim().equals(itemfound.getItemName().toLowerCase().trim()) || item.toLowerCase().trim().equals(itemfound.toString().toLowerCase().trim()))
				{
					
					if (itemfound instanceof TreasureChest) {
						
						int roomx = itemfound.getLocation().getRoom().x,
							roomy = itemfound.getLocation().getRoom().y,
							tilex = itemfound.getLocation().getTile().x,
							tiley = itemfound.getLocation().getTile().y;
						
						itemfound.getTile().removeEntity(itemfound);
						itemfound.setTile(null);
						
						//Spawns a mimic if it didn't get used, which only applies to treasure chest
						if (!itemfound.use(player)) {
							this.addEntity(new Mimic(), roomx, roomy, tilex, tiley);
						}
						return true;
					}
					
					player.addToInventory(itemfound);
					itemfound.getTile().removeEntity(itemfound);
					itemfound.setTile(null);
					return true;
				}
			}
		}
		
		//Returns false if an item was not found
		return false;
	}
	
	/**
	 * Method:  itemUsed(String item, MudEntity entity) 
	 * Purpose: It uses the specified item on the specified entity if it is in their inventory.
	 */
	
	@Override
	public synchronized boolean itemUsed(String item, MudEntity entity)
	{
		if (entity == null || !active)
			return false;
		else
		{
			ArrayList<Item> inventory = ((Player) entity).getInventory();
			
			//Checks to see if the player actually has the item
			boolean itemInInventory = false;
			Item toRemove = null;
			for (Item i : inventory) {
				if (i.getItemName().trim().toLowerCase().equals(item.trim().toLowerCase())) {
					itemInInventory = true;
					toRemove = i;
					break;
				}
			}
			
			//Uses the item if so
			if (itemInInventory) {
				
				if (toRemove instanceof Polymorph) {
					((Polymorph) toRemove).use(entity, this);
					inventory.remove(toRemove);
					this.removeEntity(toRemove);
					return true;
				}
				else if (toRemove instanceof Teleporter) {
					((Teleporter) toRemove).use(entity, this);
					inventory.remove(toRemove);
					this.removeEntity(toRemove);
					return true;
				}
				
				toRemove.use(entity);
				
				//Only removes the item if it's a consumable
				if (toRemove.isConsumable()) {
					inventory.remove(toRemove);
					this.removeEntity(toRemove);
				}
				
				return true;
			}
			return false;
		}
	}
	
	/**
	 * Method:  attackEntity() 
	 * Purpose: It makes the specified MudEntity attack the target if there is such an entity in the adjacent and diagonal tiles.
	 */
	
	@Override
	public boolean attackEntity(String target, MudEntity entity) {
		ArrayList<MudEntity> entities = this.getAdjacentEntities(entity,true);
		if (entities == null) return false;
		
		//Attacks the first mob/player found with the specified name
		for (MudEntity m : entities) {
			if (m instanceof Mob) {
				Mob mob = (Mob) m;
				if (mob.getMobName().toLowerCase().trim().equals(target.toLowerCase().trim()) || mob.toString().toLowerCase().trim().equals(target.toLowerCase().trim())) {
					Player player = (Player) entity;
					
					//Calculates the damage done
					int damage = player.calculateAttack() - mob.calculateDefence();
					
					if (damage <= 0) {
						damage = 1;
					}
					mob.setHealth(mob.getHealth() - damage);
					
					if (mob.getHealth() <= 0) {
						Item item = mob.generateItemDrops();
						this.addEntity(item, mob.getLocation().getRoom().x, mob.getLocation().getRoom().y, mob.getLocation().getTile().x, mob.getLocation().getTile().y);
						this.removeEntity(mob);
					}
					return true;
				}
			}
			else if (m instanceof Player) {
				Player enemy = (Player) m;
				if (enemy.getIGN().toLowerCase().trim().equals(target.toLowerCase().trim()) || enemy.toString().toLowerCase().trim().equals(target.toLowerCase().trim())) {
					Player player = (Player) entity;
					if (!enemy.getIGN().toLowerCase().trim().equals(player.getIGN().toLowerCase().trim())) {
						
						//Calculates the damage done
						int damage = player.calculateAttack() - enemy.calculateDefence();
						
						if (damage <= 0) {
							damage = 1;
						}
						
						enemy.setCurrentHealth(enemy.getCurrentHealth() - damage);
						
						if (enemy.getCurrentHealth() <= 0) {
							enemy.setCurrentHealth(0);
							this.dropInventory(enemy);
						}
						
						return true;
					}
					return false;
				}
			}
		}
		
		//Returns false if the mob/player couldn't be found
		return false;
	}
	
	/**
	 * Method:  interact(String target, Player player) 
	 * Purpose: It returns makes the Player interact with a door if there is an adjacent one.
	 */
	
	@Override
	public boolean interact(String target, Player player) {
		
		ArrayList<MudEntity> entities = this.getAdjacentEntities(player,false);
		if (entities == null) return false;
		
		if (!target.toLowerCase().trim().equals("door") && !target.toLowerCase().trim().equals("#") && !target.toLowerCase().trim().equals("-")) {
			return false;
		}
		
		for (MudEntity m : entities) {
			if (m instanceof Door) {
				((Door) m).use();
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Method:  transferItem(Player player, String item, String target) 
	 * Purpose: It gives the specified item from the inventory of the Player to the target player.
	 */
	@Override
	public boolean transferItem(Player player, String item, String target) {
		
		if (player == null || !active)
		{
			return false;
		}
		else
		{
			ArrayList<Item> inventory = player.getInventory();
			
			//Checks to see if the player actually has the item
			boolean itemInInventory = false;
			Item toRemove = null;
			for (Item i : inventory) {
				if (i.getItemName().trim().toLowerCase().equals(item.trim().toLowerCase())) {
					itemInInventory = true;
					toRemove = i;
					break;
				}
			}
			
			//Gives the item to the target
			if (itemInInventory) {

				//Looks for the player only in the current room
				Player p = null;
				int roomx = player.getLocation().getRoom().x,
					roomy = player.getLocation().getRoom().y;
				ArrayList<MudEntity> entities = (ArrayList<MudEntity>) this.getRoom(roomx, roomy).getEntities();
				
				for (MudEntity m : entities) {
					if (m instanceof Player) {
						Player temp = (Player) m;
						if (temp.getIGN().toLowerCase().trim().equals(target.toLowerCase().trim())) {
							p = temp;
							break;
						}
					}
				}
						
				if (p != null) {
					
					//Unequips the item if it is being given/taken
					if (toRemove instanceof Weapon) {
						if (player.getWeapon() == toRemove) {
							player.setWeapon(new Unarmed());
						}
					}
					else if (toRemove instanceof Shield) {
						if (player.getShield() == toRemove) {
							player.setShield(new NoShield());
						}
					}
					
					inventory.remove(toRemove);
					p.addToInventory(toRemove);
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * Method:  dropItem(String item, Player player) 
	 * Purpose: It drops the specified item, if it is in the player's inventory, to the location of the Player.
	 */
	
	@Override
	public boolean dropItem(String item, Player player) {
		
		if (player == null || !active)
			return false;
		else
		{
			ArrayList<Item> inventory = player.getInventory();
			
			//Checks to see if the player actually has the item
			boolean itemInInventory = false;
			Item toRemove = null;
			for (Item i : inventory) {
				if (i.getItemName().trim().toLowerCase().equals(item.trim().toLowerCase())) {
					itemInInventory = true;
					toRemove = i;
					break;
				}
			}
			
			//Drops the item
			if (itemInInventory) {
				
				//Unequips the item
				if (toRemove instanceof Weapon) {
					if (player.getWeapon() == toRemove) {
						player.setWeapon(new Unarmed());
					}
				}
				else if (toRemove instanceof Shield) {
					if (player.getShield() == toRemove) {
						player.setShield(new NoShield());
					}
				}
				
				player.getTile().addEntity(toRemove);
				inventory.remove(toRemove);
				return true;
			}
			
			return false;
		}
	}
	
	/**
	 * Method:  dropInventory(Player player) 
	 * Purpose: It drops the inventory of the Player at their location.
	 */
	
	@Override
	public boolean dropInventory(Player player) {
		//Drops all their items on death
		for (Item i : player.getInventory()) {
			player.getTile().addEntity(i);
		}
		
		return true;
	}
	
	/**
	 * Method:  respawnPlayer(Player player) 
	 * Purpose: It respawns the Player at the start and resets their stats.
	 */
	
	@Override
	public boolean respawnPlayer(Player player) {
		
		//Spawns the player at the spawn location
		player.getTile().removeEntity(player);
		this.getRoom(0, 0).getTile(3, 1).addEntity(player);
		player.getLocation().getRoom().setLocation(0, 0);
		player.getLocation().getTile().setLocation(3,1);
		
		//Resets their stats
		player.setMaxHealth(Player.DEFAULT_HEALTH);
		player.setCurrentHealth(player.getMaxHealth());
		player.setAttack(Player.DEFAULT_ATK);
		player.setDefence(Player.DEFAULT_DEF);
		player.getInventory().clear();
		player.setWeapon(new Unarmed());
		player.setShield(new NoShield());
		
		return true;
	}
	
	/**
	 * Method:  updateMobs() 
	 * Purpose: It makes all mobs take their actions.
	 */
	
	@Override
	public synchronized void updateMobs()
	{
		if (!active) return;
		for (UUID key : entities.keySet())
		{
			MudEntity e = entities.get(key);
			if (e instanceof Mob)
			{
				((Mob) e).update(this);
			}
		}
	}
	
	/**
	 * Method:  clearPlayers() 
	 * Purpose: It removes all Players from the game, which is used when closing the server.
	 */
	
	@Override
	public synchronized void clearPlayers()
	{
		if (!active)  return;
		Iterator<Map.Entry<UUID, MudEntity>>iter = entities.entrySet().iterator();
		while (iter.hasNext())
		{
			MudEntity e = iter.next().getValue();
			if (e instanceof Player)
			{
				entities.remove(e);
			}
		}
	}
	
	/**
	 * Method:  getPlayers() 
	 * Purpose: It returns a Collection containing all Players in the game.
	 */
	
	@Override
	public Collection<Player> getPlayers()
	{
		ArrayList<Player> list = new ArrayList<Player>();
		
		if (!active) return list;
		for (UUID key : entities.keySet())
		{
			MudEntity e = entities.get(key);
			if (e instanceof Player)
			{
				list.add((Player)e);
			}
		}	
		return list;
	}
	
	/**
	 * Method:  cleanEntities() 
	 * Purpose: It removes all entities from the game that have been soft-removed.
	 */
	
	private synchronized void cleanEntities()
	{
		if (!active)  return;
		Iterator<Map.Entry<UUID, MudEntity>>iter = entities.entrySet().iterator();
		while (iter.hasNext())
		{
			MudEntity e = iter.next().getValue();
			if (e.isDestroyed())
			{
				entities.remove(e);
			}
		}
	}
	
	/**
	 * Method:  reset() 
	 * Purpose: It resets the cleaner for the game.
	 */
	
	@Override
	public void reset()
	{
		if (! active) return;
		cleaner = new MudGameCleaner();
		cleaner.start();
	}

	/**
	 * Method:  destroy() 
	 * Purpose: It makes the session inactive.
	 */
	
	@Override
	public synchronized void destroy()
	{
		this.active = false;
	}
	
	/**
	 * Class:   MudGameCleaner
	 * Purpose: It maintains the list of all active entities in the game.
	 */
	
	private class MudGameCleaner extends Thread
	{
		
		public MudGameCleaner (){}
	
		@Override
		public void run()
		{
			while(active)
			{
				cleanEntities();
				try
				{
					Thread.sleep(3000);
				} catch (InterruptedException e)
				{
				}
			}
		}
	}
}
