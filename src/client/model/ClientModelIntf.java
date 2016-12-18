package client.model;

import java.util.ArrayList;
import java.util.Vector;

import model.MoveDirection;
import model.Player;
import model.Room;

/**
 * Author:   Brian Lovelace
 * File:     ClientModelIntf.java
 * Purpose:  This is an interface to be used for MudGame.
 */

public interface ClientModelIntf
{
	
	/*
	 * #####################
	 * ## SERVER COMMANDS ##
	 * #####################
	 */
	
	public void addAccount(String username, String password);
	
	public void login(String username, String password);
	
	public void logout();
	
	public void move(MoveDirection dir);
	
	public Room getRoom();
	
	public void say(String said, String target);
	
	/**
	 * Grabs the item if the item can be grabbed.
	 * @param item
	 */
	public void grabItem(String item);
	
	public void dropItem(String item);
	
	public void giveItem(String item, String target);

	public void takeItem(String item, String target);
	
	/**
	 * Causes the player to attack the mob if it is a valid
	 * target.
	 * @param mob
	 */
	public void attackEntity(String entity);
	
	public void interact(String entity);
	
	public void respawnPlayer();
	
	public void shutdown(String password);
	
	/*
	 * ####################
	 * ## LOCAL COMMANDS ##
	 * ####################
	 */
	
	/**
	 * Return whatever should be returned here.
	 */
	public String look();
	
	public String lookUp();
	
	public String lookDown();
	
	public String lookInventory();
	
	public String lookEntity(String entity);
	
	public String lookItem(String item);

	public Player getPlayer();
	
	public String getPlayers();

	public ArrayList<Vector<String>> getNewChat();

	public boolean sessionActive();
	
	public int roomCheck();
	
	public int playerCount();

}
