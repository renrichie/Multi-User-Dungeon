package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.MudEntity;
import model.background.*;
import model.Tile;
import model.item.Potion;

/**
 * Author:   Andrew Heyer
 * File:     TileTest.java
 * Purpose:  This is a unit test for Tile.
 */

public class TileTest
{
	
	@Test
	public void doubleAdd()
	{
		Tile t = new Tile();
		Wall w1 = new Wall();
		Water w2 = new Water();
		Road r1 = new Road();
		ArrayList<MudEntity> elist = new ArrayList<MudEntity>();
		elist.add(w1);
		elist.add(w2);
		
		assertTrue(t.addEntity(w1));
		assertFalse(t.addEntity(w1));
		assertTrue(t.addEntity(w2));
		
		Tile t2 = new Tile();
		assertTrue(t2.addEntity(w1));
		assertTrue(t2.addEntity(w2));
		assertFalse(t2.addEntity(w2));
		
		Tile t3 = new Tile();
		assertTrue(t3.addEntity(w1));
		assertTrue(t3.addEntity(w2));
		assertTrue(t3.addEntity(r1));	
	}
	
	@Test
	public void TileEnterExit()
	{
		Wall w1 = new Wall();
		Water w2 = new Water();
		Road r1 = new Road();
		ArrayList<MudEntity> elist = new ArrayList<MudEntity>();
		elist.add(w1);
		elist.add(w2);
		Tile t = new Tile();
		
		assertFalse(t.onTile(w1));
		assertFalse(t.onTile(w2));
		assertFalse(t.onTile(r1));
				
		assertTrue(t.addEntity(r1));

		assertFalse(t.onTile(r1));
		
		assertFalse(t.onTile(w1));
				

		assertFalse(t.removeEntity(r1));
		assertFalse(t.removeEntity(r1));

		assertFalse(t.onTile(r1));
		
		
		assertFalse(t.onTile(w1));

		assertFalse(t.removeEntity(w1));
		assertFalse(t.removeEntity(w1));
		assertFalse(t.onTile(w1));
		
		assertFalse(t.removeEntity(w2));
		assertFalse(t.removeEntity(w2));
		assertFalse(t.onTile(w2));
		
		assertFalse(t.addEntity(r1));
		assertTrue(t.addEntity(w1));
		assertTrue(t.addEntity(w2));
	}
	
	@Test
	public void testTraversable()
	{
		Tile t = new Tile();
		Road r = new Road();
		Wall w = new Wall();
		
		assertTrue(t.canTraverse());

		t.addEntity(r);
		assertTrue(t.canTraverse());
		
		t.addEntity(w);
		assertFalse(t.canTraverse());
		
		t.removeEntity(w);

		assertFalse(t.canTraverse());
		
	}
	
	@Test
	public void testStackable()
	{
		Tile t = new Tile();
		Road r = new Road();
		Wall w = new Wall();
		Potion p = new Potion();
		Potion p2 = new Potion();
			
		assertTrue(t.canStack());

		t.addEntity(r);
		assertTrue(t.canStack());
		
		t.addEntity(w);
		assertFalse(t.canStack());
		
		t.removeEntity(w);

		assertFalse(t.canStack());
		
		assertFalse(t.addEntity(p));
		assertFalse(t.onTile(p));
		
		assertFalse(t.onTile(p2));
		assertFalse(t.addEntity(p2));

		assertFalse(t.addEntity(p));
		
		assertFalse(t.addEntity(p2));
		assertFalse(t.onTile(p2));
		assertFalse(t.addEntity(p2));

	}
	
	@Test 
	public void testToString(){
		Tile t = new Tile();
		Potion p = new Potion();
		

		t.addEntity(p);
		assertEquals("o",t.toString());
	}
}
	