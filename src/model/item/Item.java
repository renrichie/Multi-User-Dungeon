package model.item;

import model.MudEntity;

/**
 * Author:   Richie Ren
 * File:     Item.java
 * Purpose:  The Item class is an abstract class that is used to represent Item objects in the game, which are subclasses of MudEntity.
 *           An item has a description, an effect (which is performed using Command objects), 
 *           and method for using it, which varies from implementation to implementation.
 */

public abstract class Item extends MudEntity {

	private String description, itemName;
	
	// This constructor sets all tile traits true
	public Item(String itemName, String description) {
		super(true, true, true);
		this.description = description;
		this.itemName = itemName;
	}
	
	// This constructor allows for specific traversability, stackability, grabbability
	public Item(String itemName, String description, boolean grab, boolean traverse, boolean stack){
		super(grab, traverse, stack);
		this.description = description;
		this.itemName = itemName;
	}
	
	/**
	 * Method:  getItemName() 
	 * Purpose: It returns the string containing the item's name.
	 */
	
	public String getItemName() {
		return this.itemName;
	}
	
	/**
	 * Method:  getDescription() 
	 * Purpose: It returns the string containing the item's description.
	 */
	
	@Override
	public String getDescription() {
		return this.toString() + " - (" + this.getItemName() + ") " + this.description;
	}
		
	/**
	 * Method:  isConsumable() 
	 * Purpose: It returns a boolean indicating if the item is a consumable.
	 */
	
	public boolean isConsumable()
	{
		return false;
	}
	
	/**
	 * Method:  use(MudEntity target) 
	 * Purpose: The item performs its effect on the specified target.
	 */
	
	public abstract boolean use(MudEntity target);
}
