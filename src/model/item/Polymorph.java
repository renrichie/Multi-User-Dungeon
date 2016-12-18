package model.item;

import java.util.ArrayList;

import model.MudEntity;
import model.MudModelIntf;
import model.mob.Diablo;
import model.mob.MUDProject;
import model.mob.Mob;
import model.mob.Rabbit;
import model.mob.Treant;

/**
 * Author:   Richie Ren
 * File:     Polymorph.java
 * Purpose:  The Polymorph class is used to represent an item that turns surrounding mobs into rabbits/treants.
 */

public class Polymorph extends Item {
	
	public Polymorph() {
		super("Polymorph", "This strange item has the power to turn enemies into harmless rabbits. It doesn't always go quite as planned though.");
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
	 * Purpose: It changes all mobs around the target.
	 *          Returns true since the action was successful.
	 */
	
	public boolean use(MudEntity target, MudModelIntf model) {
		
		boolean result = Math.random() <= 0.7;

		ArrayList<MudEntity> entities = model.getAdjacentEntities(target, true);
				
		//Changes the mobs to rabbits or treants
		for (MudEntity m : entities) {
			if (m instanceof Mob) {
				if (!(m instanceof Diablo) && !(m instanceof MUDProject)) {
					if (result) {
						model.addEntity(new Rabbit(), m.getLocation().getRoom().x, m.getLocation().getRoom().y, m.getLocation().getTile().x, m.getLocation().getTile().y);
					}
					else {
						model.addEntity(new Treant(), m.getLocation().getRoom().x, m.getLocation().getRoom().y, m.getLocation().getTile().x, m.getLocation().getTile().y);
					}
					model.removeEntity(m);
				}
			}
		}
		
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
		return "r";
	}
}
