package model;

import java.io.Serializable;
import java.util.UUID;

import model.background.Background;
import model.item.Item;
import model.mob.Mob;

/**
 * Author:   Brian Lovelace
 * File:     MudEntity.java
 * Purpose:  The MudEntity class is an abstract class used to represent essentially any object that shows up in the game,
 *           such as items, monsters, players, and even the room tiles.
 */

public abstract class MudEntity implements Serializable, Comparable<MudEntity>
{
	private boolean grabbable,
					traversable,
					stackable;
	private volatile boolean destroyed;
			
	private Location location;
	public final UUID UID;
	
	private Tile tile;

	public MudEntity()
	{
		this.UID = UUID.randomUUID();
		this.grabbable = false;
		this.traversable = true;
		this.stackable = true;
		this.destroyed = false;
		this.tile = null;
	}
	
	public MudEntity(Location loc)
	{
		this();
		this.location = loc;
	}

	public MudEntity(boolean grabbable, 
	                 boolean traversable,
	                 boolean stackable)
	{
		this.UID = UUID.randomUUID();
		this.grabbable = grabbable;
		this.traversable = traversable;
		this.stackable = stackable;
	}
	
	public MudEntity(Location loc,
	                 boolean grabbable, 
	                 boolean traversable,
	                 boolean stackable)
	{
		this(loc);
		this.grabbable = grabbable;
		this.traversable = traversable;
		this.stackable = stackable;
	}	
	
	/**
	 * Method:  setTile() 
	 * Purpose: It sets the tile that the entity is on.
	 */
	
	public void setTile(Tile tile){
		this.tile = tile;
	}
	
	/**
	 * Method:  getTile() 
	 * Purpose: It returns the tile that the entity is on.
	 */
	
	public Tile getTile(){
		return tile;
	}
	
	/**
	 * Method:  getLocation() 
	 * Purpose: It returns location of the entity.
	 */
	
	public Location getLocation()
	{
		return this.location;
	}
	
	/**
	 * Method:  canGrab() 
	 * Purpose: It returns whether or not the entity is grabbable.
	 */
	
	public boolean canGrab()
	{
		return this.grabbable;
	}
	
	/**
	 * Method:  isTraversable() 
	 * Purpose: It returns whether or not the entity can be moved through.
	 */
	
	public boolean isTraversable()
	{
		return this.traversable;
	}
	
	/**
	 * Method:  isStackable() 
	 * Purpose: It returns whether or not the entity can have something placed on top of it.
	 */
	
	public boolean isStackable()
	{
		return this.stackable;
	}
	
	/**
	 * Method:  setGrabbable(boolean grabbable) 
	 * Purpose: It sets whether or not the entity is grabbable.
	 */
	
	protected void setGrabbable(boolean grabbable)
	{
		this.grabbable = grabbable;
	}
	
	/**
	 * Method:  setTraversable(boolean traversable) 
	 * Purpose: It sets whether or not the entity is traversable.
	 */
	
	protected void setTraversable(boolean traversable)
	{
		this.traversable = traversable;
	}
	
	/**
	 * Method:  setStackable(boolean stackable) 
	 * Purpose: It sets whether or not the entity is stackable.
	 */
	
	protected void setStackable(boolean stackable)
	{
		this.stackable = stackable;
	}
	
	/**
	 * Method:  setLocation(Tile tile, Room room) 
	 * Purpose: It sets the location of the entity to be at the specified tile.
	 */
	
	protected void setLocation(Location loc)
	{
		this.location = loc;
	}
	
	/**
	 * Method:  getDescription() 
	 * Purpose: It returns the description of the entity.
	 */
	
	public abstract String getDescription();
	
	/**
	 * Method:  equals(Object obj) 
	 * Purpose: It returns whether or not the two objects are the same exact instance.
	 */
	
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof MudEntity))
			return false;
		
		MudEntity ent = (MudEntity)(obj);
		return ent.UID.equals(this.UID);
	}
	
	/**
	 * Method:  compareTo(MudEntity e) 
	 * Purpose: It returns whether or not the two objects are instances of the same class/hierarchy.
	 */
	
	@Override
	public int compareTo(MudEntity e)
	{
		EntityCompare e1 = EntityCompare.getEntityCompare(this);
		EntityCompare e2 = EntityCompare.getEntityCompare(e);
		return e1.value - e2.value;
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the entity.
	 */
	
	@Override
	public String toString()
	{
		if (this instanceof Mob)
			return "m";
		else if (this instanceof Item)
			return "i";
		else if (this instanceof Player)
			return "p";
		else if (this instanceof Background)
			return ".";
		else
			return "?";
	}
	
	/**
	 * Method:  destroy() 
	 * Purpose: It removes itself from its tile and sets itself to null.
	 */
	
	public void destroy(){
		this.destroyed = true;
		if (tile !=null){
			tile.removeEntity(this);
			this.tile = null;
		}
	}
	
	/**
	 * Method:  isDestroyed() 
	 * Purpose: It returns a boolean indicating if it is destroyed.
	 */
	
	public boolean isDestroyed(){
		return this.destroyed;
	}
	
	/**
	 * Method:  EntityCompare() 
	 * Purpose: It is used to compare instances of MudEntity.
	 */
	
	private enum EntityCompare
	{
		BACKGROUND(0), MISC(1), ITEM(2), MOB(3), PLAYER(4), ActivePlayer(5);
		
		private final int value;
		
		private EntityCompare(int val)
		{
			this.value = val;
		}
		
		private static EntityCompare getEntityCompare(MudEntity e)
		{
			if (e instanceof Background) return EntityCompare.BACKGROUND;
			else if (e instanceof Item) return EntityCompare.ITEM;
			else if (e instanceof Mob) return EntityCompare.MOB;
			else if (e instanceof Player)
			{
				if (((Player) e).isActive()) return EntityCompare.ActivePlayer;
				else return EntityCompare.PLAYER;
			}
			else return EntityCompare.MISC;
		}
	}
}
