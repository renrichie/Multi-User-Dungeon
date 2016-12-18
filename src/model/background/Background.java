package model.background;

import model.MudEntity;

/**
 * Purpose of this class is to give an unambiguous definition of a background to 
 * classes that are considered 'background' tile entities. The distinguishing feature
 * of this abstract class is there there may only ever be one background at a time.
 * For example, a water tile and a wall tile will never be allowed on the same tile.
 * This can be done using isinstanceof. Setting a boolean in MudEntity would make
 * code elsewhere cumbersome since there will be multiple locations of checking
 * for Background.
 * @author andrewheyer
 *
 */
public abstract class Background extends MudEntity {
	
	public Background(boolean traversable, boolean stackable){
		super(false, traversable, stackable);
	}
}
