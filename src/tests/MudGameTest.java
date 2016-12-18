package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.MoveDirection;
import model.MudGame;
import model.Player;
import model.item.GlassCannon;
import model.item.HiPotion;
import model.item.Iron;
import model.item.Longsword;
import model.item.TreasureChest;
import model.mob.Insect;
import model.mob.Treant;

/**
 * Author:   Andrew Heyer
 * File:     MudGameTest.java
 * Purpose:  This is a unit test for MudGame.
 */

public class MudGameTest
{
	/**
	 * Lets first make sure that our MudGame is making the board correctly
	 * with the map generator. Since we hard coded the map with the generator
	 * we know where/what every entity is. With this we can check to see if the entities
	 * we places with the generator are matching with the get methods in MudGame
	 * 
	 * !!!!!!! IF FAILING THE FOLLOWING CASES IT IS BECAUSE OF RANDOM.INT ATTACKS !!!!!! 
	 *                                  line 143 
	 *                                  line 152
	 * 
	 * 
	 *     
	 *               (should work more times than not though, so run test again)
	 */
	
	@Test
	public void testMudGame1(){
		MudGame game = new MudGame();
		assertFalse(game.getRoom(0,0).getTile(0, 0).canStack());
		assertFalse(game.getRoom(0,0).getTile(0, 0).canTraverse());
		assertFalse(game.getRoom(0,3).getTile(0, 2).canTraverse());
		// This tile has no mobs or iems, but is still 1 because background is entity
		assertEquals(1, game.getRoom(0, 0).getTile(0, 0).getEntities().size());
		// We put a sword here with the map generator
		assertEquals(2, game.getRoom(0, 0).getTile(2, 2).getEntities().size());
		// more tests... 
	}
	
	/*
	 * lets now test the adding, checking, removing of mud entities from a tile location. Lets
	 * also be sure to try to add all different combination of entity traits. Such as
	 * traversable on non-traversable etc,. 
	 */
	@Test
	public void testMudGame2(){
		// the game to test on
		MudGame game = new MudGame();
		
		// some entities to play with
		Longsword longsword = new Longsword();
		GlassCannon cannon = new GlassCannon();
		Treant treant = new Treant();
		
		
		// Add/Remove entity related tests
		assertTrue(game.addEntity(longsword, 0, 0, 3, 1));
		assertFalse(game.addEntity(longsword, 0, 0, 3, 1)); // addEntity checks for duplicates	
		assertTrue(game.getRoom(0, 0).getTile(3, 1).getEntities().contains(longsword));
		assertTrue(game.removeEntity(longsword));
		// more tests..
	}
	
	
	/**
	 * Now we will test for more complicated methods, such as the moveEntity and try to get every
	 * possible case of leaving the room. This way we can make sure moving entities from room to
	 * room works in all cases. 
	 */
	@Test
	public void testMudGame3(){
		MudGame game  = new MudGame();
		
		Player player = new Player("joe"); // default spawn is (0,0,1,1)
		Longsword longsword = new Longsword();
		Iron iron = new Iron();
		
		// Interact test
		assertTrue(!game.interact("door", player));
		assertTrue(!game.interact("fake", player));
		
		// Get Player test
		assertTrue(game.getPlayer("joe") == null);
		
		// Update mobs
		game.updateMobs();
		
		// Move Entity test, check for all ways to switch rooms
		assertTrue(game.addEntity(player, 0, 0, 1, 1));
		assertTrue(game.getRoom(0, 0).getTile(1, 1).getEntities().contains(player));
		// shouldnt be able to go up or or left because of wall
		assertFalse(game.moveEntity(player, MoveDirection.NORTH));
		assertFalse(game.moveEntity(player, MoveDirection.WEST));
		assertTrue(game.moveEntity(player, MoveDirection.EAST));
		assertTrue(game.moveEntity(player, MoveDirection.EAST));
		assertTrue(game.moveEntity(player, MoveDirection.EAST));
		assertEquals(0, player.getLocation().getRoom().getX(), 0); // 3rd arg is epsilon
		assertTrue(game.moveEntity(player, MoveDirection.EAST));
		// We should be in room (1,0) now
		assertEquals(1, player.getLocation().getRoom().getX(), 0);
		assertTrue(game.moveEntity(player, MoveDirection.SOUTH));
		assertTrue(game.moveEntity(player, MoveDirection.SOUTH));
		assertTrue(game.moveEntity(player, MoveDirection.SOUTH));
		assertEquals(0, player.getLocation().getRoom().getY(), 0);
		assertTrue(game.moveEntity(player, MoveDirection.SOUTH));
		// We should be in room (1,1) now
		assertEquals(1, player.getLocation().getRoom().getY(), 0);
		assertTrue(game.moveEntity(player, MoveDirection.WEST));
		// and back into room (0,1)
		assertEquals(0, player.getLocation().getRoom().getX(), 0);
		assertTrue(game.moveEntity(player, MoveDirection.NORTH));
		// and now into our original room (0,0)
		assertEquals(0, player.getLocation().getRoom().getY(), 0);
		
		// Now we will test for adjacent entities, keep in mind that this should not return
		// entities from another room, so we will also do this check.
		assertTrue(game.moveEntity(player, MoveDirection.EAST)); // player in (1,0) (0,4)
		assertTrue(game.addEntity(longsword, 1, 0, 0, 3)); // longsword in the same room
		assertTrue(game.addEntity(iron, 0, 0, 4, 4)); // Iron in the different room
		assertEquals(2, game.getRoom(1, 0).getTile(0,3).getEntities().size(), 1);
		assertTrue(game.grabItem("longsword", player));
		assertFalse(game.grabItem("longsword", player)); // F, we only placed 1 sword
		assertFalse(game.grabItem("iron", player)); // F, Iron in the other room
		assertTrue(game.moveEntity(player, MoveDirection.WEST));
		assertTrue(game.moveEntity(player, MoveDirection.SOUTH)); 
		assertFalse(game.grabItem("longsword", player));
		
		// Let's now test for using items, lets give our player a potion to test that too 
		HiPotion potion = new HiPotion();
		TreasureChest chest = new TreasureChest();
		assertTrue(game.addEntity(potion, 0, 1, 4, 3)); // longsword in the same room
		assertTrue(game.addEntity(chest, 0, 1, 4, 3));
		assertEquals(0, player.getLocation().getRoom().getX(), 0); // player at (0,1) (0,4)
		assertFalse(game.grabItem("Hi-Potion", player));
		assertFalse(game.grabItem("treasure-chest", player));
		assertFalse(game.itemUsed("Hi-Potion", player));
		assertTrue(game.itemUsed("longsword", player));
		
		// Now checking for attack
		Insect insect = new Insect();
		insect.setHealth(1);
		assertTrue(game.addEntity(insect, 0, 1, 4, 3));
		assertFalse(game.attackEntity("insect", player));
		player.getLocation().getRoom().setLocation(0, 1);
		player.getLocation().getTile().setLocation(4, 3);
		assertTrue(game.attackEntity("insect", player));
		// insect should be dead
		assertFalse(game.getRoom(0, 1).getEntities().contains(insect));
		
		// player vs player attacks
		Player enemy = new Player("enemy");
		assertTrue(game.addEntity(enemy, 0, 1, 4, 3));
		enemy.getLocation().getRoom().setLocation(4, 3);
		enemy.getLocation().getTile().setLocation(4, 3);
		assertFalse(game.attackEntity(player.getIGN(), enemy));
		assertTrue(game.attackEntity(enemy.getIGN(), player));
		
		// Interact test
		game.addEntity(player, 2, 0, 2, 2);
		player.getLocation().getRoom().setLocation(2, 0);
		player.getLocation().getTile().setLocation(2, 2);
		assertTrue(game.interact("door", player));
		assertTrue(!game.interact("fake", player));
		
		// Attack test
		game.addEntity(player, 4, 0, 3, 3);
		player.getLocation().getRoom().setLocation(4, 0);
		player.getLocation().getTile().setLocation(3, 3);
		assertTrue(!game.attackEntity("rabbit", player));
		
		// Clears the players
		for (int i = 0; i < 10; i ++) {
			try {
				game.clearPlayers();
				game.reset();
			}
			catch (Exception e) {
				System.out.println("Error");
			}
		}
	}
}
