package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import model.item.Item;
import model.item.NoShield;
import model.item.Shield;
import model.item.Unarmed;
import model.item.Weapon;

/**
 * Author:   Richie Ren
 * File:     Player.java
 * Purpose:  The Player class is used to represent a user's in-game character.
 *           A player has a current amount of health and max amount of health, as well as some amount of attack and defence.
 *           A player also has an in-game name.
 */

public class Player extends MudEntity implements Serializable
{
	public static final int DEFAULT_HEALTH = 10,
							DEFAULT_ATK = 5,
							DEFAULT_DEF = 5;

	private int currentHealth, maxHealth, attack, defence;
	private Weapon equippedWeapon;
	private Shield equippedShield;
	private String ingameName;
	private ArrayList<Item> inventory;
	private ArrayList<Vector<String>> chatlog;
	private Random rng;
	private String giving = null;
	private String taking = null;
	private String give_take = null;
	private transient boolean activePlayer = false;
	
	
	public Player(int health, int attack, int defence, String name) {
		super(false, true, true);
		this.chatlog = new ArrayList<Vector<String>>();
		this.rng = new Random();
		this.currentHealth = health;
		this.maxHealth = health;
		this.attack = attack;
		this.defence = defence;
		this.ingameName = name;
		this.inventory = new ArrayList<>();
		this.equippedWeapon = new Unarmed();
		this.equippedShield = new NoShield();
	}
	
	public Player(String name)
	{
		this(DEFAULT_HEALTH, DEFAULT_ATK, DEFAULT_DEF, name);
	}
		
	/**
	 * Method:  getInventory() 
	 * Purpose: It returns an array list containing the inventory of the player.
	 */
	
	public ArrayList<Item> getInventory() {
		return this.inventory;
	}
	
	/**
	 * Method:  addToInventory(Item item) 
	 * Purpose: It adds an item to the inventory of the player.
	 */
	
	public void addToInventory(Item item) {
		this.inventory.add(item);
	}
	
	/**
	 * Method:  getWeapon() 
	 * Purpose: It returns the weapon that the player has currently equipped.
	 */
	
	public Weapon getWeapon() {
		return this.equippedWeapon;
	}
	
	/**
	 * Method:  setWeapon() 
	 * Purpose: It sets the weapon that the player has currently equipped.
	 */
	
	public void setWeapon(Weapon weapon) {
		this.equippedWeapon = weapon;
	}
	
	/**
	 * Method:  getShield() 
	 * Purpose: It returns the shield that the player has currently equipped.
	 */
	
	public Shield getShield() {
		return this.equippedShield;
	}
	
	/**
	 * Method:  setShield() 
	 * Purpose: It sets the shield that the player has currently equipped.
	 */
	
	public void setShield(Shield shield) {
		this.equippedShield = shield;
	}
	
	/**
	 * Method:  calculateAttack() 
	 * Purpose: It returns the amount of damage that the player does.
	 */
	
	public int calculateAttack() {
		return (int) Math.round(this.getWeapon().getAttackMultiplier() * this.getAttack() + rng.nextInt(5)) ;
	}
	
	/**
	 * Method:  calculateDefence() 
	 * Purpose: It returns the amount of damage that the player blocks.
	 */
	
	public int calculateDefence() {
		return (int) Math.round(this.getShield().getDefenceMultiplier() * this.getDefence() + rng.nextInt(5));
	}
	
	/**
	 * Method:  getIGN() 
	 * Purpose: It returns a string containing the in-game name of the player. Used for displaying it on the client.
	 */
	
	public String getIGN() {
		return this.ingameName;
	}
	
	/**
	 * Method:  setMaxHealth(int newHealth) 
	 * Purpose: It sets the max health of the player. It is used for when the player possibly levels up or a buffing item is used.
	 */
	
	public void setMaxHealth(int newHealth) {
		this.maxHealth = newHealth;
	}
	
	/**
	 * Method:  setCurrentHealth(int newHealth) 
	 * Purpose: It sets the current health of the player. It is used for when the player takes damage or heals.
	 */
	
	public void setCurrentHealth(int newHealth) {
		this.currentHealth = newHealth;
	}
	
	/**
	 * Method:  setAttack(int newAttack) 
	 * Purpose: It sets the attack of the player to the specified amount.
	 */
	
	public void setAttack(int newAttack) {
		this.attack = newAttack;
	}
	
	/**
	 * Method:  setDefence(int newDefence) 
	 * Purpose: It sets the defence of the player to the specified amount.
	 */
	
	public void setDefence(int newDefence) {
		this.defence = newDefence;
	}
	
	/**
	 * Method:  getMaxHealth() 
	 * Purpose: It returns the max health of the player.
	 */
	
	public int getMaxHealth() {
		return this.maxHealth;
	}
	
	/**
	 * Method:  getCurrentHealth() 
	 * Purpose: It returns the current health of the player.
	 */
	
	public int getCurrentHealth() {
		return this.currentHealth;
	}
	
	/**
	 * Method:  getAttack() 
	 * Purpose: It returns the attack of the player.
	 */
	
	public int getAttack() {
		return this.attack;
	}
	
	/**
	 * Method:  getDefence() 
	 * Purpose: It returns the defence of the player.
	 */
	
	public int getDefence() {
		return this.defence;
	}
	
	/**
	 * Method:  isActive() 
	 * Purpose: It returns a boolean indicating if the player is online on the server.
	 */
	
	public boolean isActive()
	{
		return this.activePlayer;
	}
	
	/**
	 * Method:  setActive() 
	 * Purpose: It sets the active flag to indicate that the player is active on the server.
	 */
	
	public void setActive()
	{
		this.activePlayer = true;
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the character representation of the player.
	 */
	
	@Override
	public String toString()
	{
		if (activePlayer) return "P";
		else return "p";
	}
	
	/**
	 * Method:  getDescription() 
	 * Purpose: It returns the stats of the player.
	 */
	
	@Override
	public String getDescription() {
		String stats = "";
		stats += this.getIGN() + "\n" + "HP: " + this.currentHealth + "/" + this.maxHealth + "\n" + 
		"Weapon: " + this.equippedWeapon.getItemName() + "\n" + "Shield: " + this.equippedShield.getItemName() + "\n";
		
		return stats;
	}

	/**
	 * Method:  updateChat(String text, String sender) 
	 * Purpose: It updates the player's chat with the new message.
	 */
	
	public void updateChat(String text, String sender)
	{
		Vector<String> log = new Vector<String>();
		log.add(sender);
		log.addElement(text);
		this.chatlog.add(log);
	}

	/**
	 * Method:  clearChat() 
	 * Purpose: It empties the player's chat box.
	 */
	
	public void clearChat()
	{
		this.chatlog.clear();
	}

	/**
	 * Sets the item to be taken by the player
	 * @param taking Item name
	 */
	public void setTaking(String taking)
	{
		this.taking = taking;
	}
	
	/**
	 * Returns the item to be taken by the player
	 * @return
	 */
	public String getTaking()
	{
		return taking;
	}
	
	/**
	 * Sets the item to be given by the player
	 * @param taking Item name
	 */
	public void setGiving(String giving)
	{
		this.giving = giving;
	}
	
	/**
	 * Returns the item to be given by the player
	 * @return
	 */
	public String getGiving()
	{
		return giving;
	}
	
	/**
	 * Sets the item to be given by the player
	 * @param taking Item name
	 */
	public void setGiveTakeTarget(String target)
	{
		this.give_take = target;
	}
	
	/**
	 * Returns the item to be given by the player
	 * @return
	 */
	public String getGiveTakeTarget()
	{
		return this.give_take;
	}
	
	/**
	 * Method:  getChatLog() 
	 * Purpose: It returns an ArrayList containing the messages in the chat box.
	 */
	
	public ArrayList<Vector<String>> getChatLog()
	{
		return this.chatlog;
	}
}
