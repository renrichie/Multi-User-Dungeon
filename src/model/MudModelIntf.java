package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Author:   
 * File:     MudModelIntf.java
 * Purpose:  This is an interface to be used for MudGame.
 */

public interface MudModelIntf extends Serializable
{
	public Player getPlayer(String username);
	
	public Room getRoom(int x, int y);
	
	public boolean itemUsed(String item, MudEntity entity);

	public boolean addEntity(MudEntity entity, int roomx, int roomy, int tilex, int tiley);

	public boolean removeEntity(MudEntity entity);

	public boolean moveEntity(MudEntity entity, MoveDirection dir);
	
	public boolean grabItem(String item, Player player);
	
	public boolean attackEntity(String entity, MudEntity e);
	
	public boolean interact(String target, Player player);
	
	public void updateMobs();
	
	public void clearPlayers();
	
	public void reset();
	
	public void destroy();

	public boolean respawnPlayer(Player player);
	
	public boolean dropInventory(Player player);
	
	public boolean dropItem(String item, Player player);

	public boolean transferItem(Player player, String item, String target);
	
	public Collection<Player> getPlayers();

	public ArrayList<MudEntity> getAdjacentEntities(MudEntity target, boolean b);
}
