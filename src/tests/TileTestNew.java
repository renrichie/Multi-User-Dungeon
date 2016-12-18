package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.MudEntity;
import model.Tile;
import model.background.*;
import model.item.GlassCannon;
import model.item.Kiteshield;
import model.item.Longsword;
import model.mob.Treant;

/**
 * Author:   Andrew Heyer
 * File:     TileTestNew.java
 * Purpose:  This is a unit test for Tile.
 */

public class TileTestNew {

	/**
	 * Lets check to make sure both constructors of our tile are working
	 * by constructing both ways, and using getters to check if all checks
	 * out.
	 */
	@Test 
	public void tileTest1(){
		// test constructor with no entities
		Tile tileWithNoArguments = new Tile();
		ArrayList<MudEntity> anEmptyList = new ArrayList<MudEntity>();
		assertEquals(tileWithNoArguments.getEntities(), anEmptyList);
		
		// test constructor with list Items ONLY as argument 
		ArrayList<MudEntity> listWithItems = new ArrayList<MudEntity>();
		Longsword longsword = new Longsword();
		Kiteshield kiteshield = new Kiteshield();
		listWithItems.add(longsword);
		listWithItems.add(kiteshield);
		Tile tileWithArguments = new Tile(listWithItems);
		assertEquals(2, tileWithArguments.getEntities().size());
		assertTrue(tileWithArguments.getEntities().contains(longsword));
		assertTrue(tileWithArguments.getEntities().contains(kiteshield));
		
	}
	
	/**
	 * Lets now test specific methods of the Tile class. Lets also keep it fairly 
	 * simple for now, and try removing and adding Item entities. We are using Item
	 * since it is easiest to test, it is traversable and stackable so no specific
	 * cases we need to worry about for now.  
	 */
	@Test
	public void tileTest2(){
		ArrayList<MudEntity> itemList = new ArrayList<MudEntity>();
		Longsword longsword = new Longsword();
		Kiteshield kiteshield1 = new Kiteshield();
		// Hey, let's also see if our UID is working by being able to add same items
		Kiteshield kiteshield2 = new Kiteshield();
		itemList.add(longsword);
		itemList.add(kiteshield1);
		itemList.add(kiteshield2);
		Tile tile = new Tile(itemList);
		
		assertEquals(tile.getEntities().size(), 3);
		assertTrue(tile.getEntities().contains(longsword));
		assertTrue(tile.getEntities().contains(kiteshield1));
		assertTrue(tile.getEntities().contains(kiteshield2));
		
		// Lets remove kiteshield2 and be sure that it returns false
		tile.removeEntity(kiteshield2);
		assertEquals(tile.getEntities().size(), 2);
		assertFalse(tile.getEntities().contains(kiteshield2));
		assertTrue(tile.getEntities().contains(kiteshield1));
		
		// since we have Items on here, lets check some boolean methods make sure they work
		assertTrue(tile.canTraverse());
		assertTrue(tile.canStack());
		
	}
	/**
	 * Lets now test for some items that are not stackable and traversable
	 */
	@Test
	public void tileTest3(){
		// We try to add an entity on a tile that has a GlassCannon which is non-traveravle & non-stackable
		ArrayList<MudEntity> itemList = new ArrayList<MudEntity>();
		GlassCannon cannon = new GlassCannon();
		itemList.add(cannon);
		Tile tile = new Tile(itemList);
		assertFalse(tile.canStack());
		assertFalse(tile.canTraverse());
		
		
		// We try to add an entity on a tile that has a hostile enemy, which is non-traversable, but is stackable.
		ArrayList<MudEntity> mobList = new ArrayList<MudEntity>();
		Treant treant = new Treant();
		mobList.add(treant);
		Tile tile2 = new Tile(mobList);
		assertTrue(tile2.canStack());
		assertTrue(tile2.canTraverse());
		
		
		// Lets test some of the background entities, water is traversable but not stackable
		ArrayList<MudEntity> entityList = new ArrayList<MudEntity>();
		Water water = new Water();
		entityList.add(water);
		Tile tile3 = new Tile(entityList);
		assertFalse(tile3.canTraverse());
		assertFalse(tile3.canStack());
		// if we try adding a longsword here, it should disappear!
		Longsword longsword = new Longsword();
		assertFalse(tile3.addEntity(longsword));
		
		
	}
	
}
