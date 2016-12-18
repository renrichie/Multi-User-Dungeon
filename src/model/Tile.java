package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import model.background.Background;
import model.item.Item;

/**
 * The Tile class is the most primitive part of the game Board.
 * Thus, it will only know of very primitive things for the game.
 * such as if items can go on it, if it is traversable etc. The
 * Room will have a 2d array of tiles which will keep track of 
 * tile locations and other abstract knowledge.
 * 
 *  - Being traversable is all I can think of for now, will add
 *  more as they are needed of course
 */
public class Tile implements Serializable
{

	private HashMap<UUID, MudEntity> entities;
	private Background background;
	
	public Tile()
	{
		this(new ArrayList<MudEntity>());
	}
	
	/**
	 * Second constructor that the map generator will be using. Simplifies
	 * creating tiles because rather than creating a tile and adding entities
	 * we can create a tile with entities. Also currently Water is the default
	 * background entity.
	 * @param entities
	 */
	public Tile(Collection<MudEntity> entities)
	{
		this.entities = new HashMap<UUID, MudEntity>();
		this.background = null;//ew Water();
		
		for (MudEntity e : entities)
		{
			this.addEntity(e);
		}
	}
	
	/**
	 * Return a list of entities that currently are on this tile. Notice that
	 * we reverse the list before returning it. This is so that when testing
	 * or retrieving the list, we get it in the same exact order that it was
	 * added. If we were to not reverse the list the getEntities would 
	 * return a list of entities that are in reverse order of the list that
	 * was used to add to it.
	 */
	public Collection<MudEntity> getEntities()
	{
		ArrayList<MudEntity> lst = new ArrayList<MudEntity>();
		if (this.background != null)
			lst.add(background);
		for (UUID uid : entities.keySet()){
			lst.add(entities.get(uid));
		}
		return lst;
	}
	
	/**
	 * Check to see if an entity is on a tile, checks by UID so we could
	 * potentially have the same item, but not the exact same item. 
	 */
	public boolean onTile(MudEntity entity)
	{
		boolean onTile = this.entities.containsKey(entity.UID);
		if (onTile && this.background != null)
			onTile = this.background.equals(entity);
		return onTile;
	}
	
	/**
	 * Check to see if this instance of tile is traversable. Uses the abstract
	 * method isTraversable.
	 * @return boolean
	 */
	public boolean canTraverse()
	{
		if (this.background != null)
			if (! this.background.isTraversable())
				return false;
		for (UUID uid : entities.keySet())
		{
			if (!this.entities.get(uid).isTraversable())
				return false;
		}
		return true;
	}
	
	/**
	 * Checks through all current entities on a tile, if any single tile is not
	 * stackable, returns false.
	 * @return boolean
	 */
	public boolean canStack()
	{
		if (this.background != null)
			if (!this.background.isStackable())
			{
				return false;
			}
		
		for (UUID uid : entities.keySet())
		{
			if (!this.entities.get(uid).isStackable())
				return false;
		}
		return true;
	}
	
	/**
	 * Destroy all entities that are NOT items on the tile. This method is so
	 * we may have more interesting mechanics. For example, if we wanted to simulate
	 * the behavior of a table. The table, we can have items on a table. But we can
	 * not have mobs, players, (anything not an item!) 
	 */
	private void destroyTraversables()
	{
		for (UUID uid : entities.keySet())
		{	
			MudEntity e = entities.get(uid);
			if (!(e instanceof Item)){
				entities.get(uid).destroy();
			}
		}
	}
	
	/**
	 * Destroys all entities that ARE items on the tile. This method, just like above
	 * is so that we may have interesting mechanics. For example, if we wanted to 
	 * simulate the behavior of tall grass, in which any items dropped on disappear.
	 * Then this grass would be traversable allowing mobs and players, but NOT items.
	 */
	private void destroyItems()
	{
		for (UUID uid : entities.keySet())
		{	
			MudEntity e = entities.get(uid);
			if ((e instanceof Item)){
				entities.get(uid).destroy();
			}
		}
	}
	
	/**
	 * This removes a specific entity from a tile by its UID.
	 */
	public boolean removeEntity(MudEntity entity)
	{
		if (!this.entities.containsKey(entity.UID))
			return false;
		else
		{
			this.entities.remove(entity.UID);
			return true;
		}
	}
	
	
	/**
	 * This methods adds an entity to a tile, and is in a way the 'controller' for
	 * defining the behavior of 'stackable' and 'traversable' item placement. Using
	 * helper methods, and checking to see the traits of the entities on the tile
	 * we are adding to, and checking the traits of the entity we want to add, we
	 * either destroy what is on the tile, simply add, destroy ONLY items etc...
	 * (by traits I mean if it is traversable, or stackable)
	 * @param entity
	 * @return boolean
	 */
	public boolean addEntity(MudEntity entity)
	{
		if (entity == null)
			return false; // here
		if (entity.equals(this.background) || this.entities.containsKey(entity.UID))
			return false; // here
		else if (!(entity instanceof Background))
		{
			if (entity instanceof Item)
			{
				if (! this.canStack())
					return false; // here
			}
			else 
				if (! this.canTraverse())
					return false; // here
		}

		/* 
		 * If the entity we are adding is not traversable, for example a table
		 * this will destroy everything but the items. 
		 */
		if (!entity.isTraversable()){	
			destroyTraversables();
		}
		
		/*
		 * If the entity we are adding is not stackable but is traversable
		 * (like very tall grass) we destroy the items on that entity 
		 * (as if they dissapeared in the grass)
		 */
		if (!entity.isStackable()){
			destroyItems();
		}
		
		
		if (entity instanceof Background){
			this.background = (Background) entity;
		} else {
			entities.put(entity.UID, entity);
			entity.setTile(this);
		}
		return true;
	}
	
	/**
	 * If there is nothing on top of this tile entity, print the background
	 * entity by default. 
	 */
	@Override
	public String toString()
	{		
		MudEntity e = background;
		for (UUID uid : entities.keySet())
		{
			if (e == null)
				e = entities.get(uid);
			else
			{
				if (e.compareTo(entities.get(uid)) < 0)
					e = entities.get(uid);
			}
		}
		if (e == null) return " ";
		else return e.toString();
	}	
}
