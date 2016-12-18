package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.Player;
import model.item.*;

/**
 * Author:   Richie Ren
 * File:     ItemTest.java
 * Purpose:  This is a unit test for any classes that extend Item.
 */

public class ItemTest {

	private Potion potion = new Potion();
	private Calcium calcium = new Calcium();
	private GlassCannon gc = new GlassCannon();
	private HiPotion hipotion = new HiPotion();
	private Iron iron = new Iron();
	private Kiteshield ks = new Kiteshield();
	private Longsword ls = new Longsword();
	private Mastersword ms = new Mastersword();
	private MaxPotion mp = new MaxPotion();
	private MysteryPotion mp2 = new MysteryPotion();
	private NoShield ns = new NoShield();
	private Protein protein = new Protein();
	private SquareShield ss = new SquareShield();
	private Sword s = new Sword();
	private Unarmed u = new Unarmed();
	private WetNoodle wd = new WetNoodle();
	private WoodenShield ws = new WoodenShield();
	private TreasureChest t = new TreasureChest();

	/**
	 * Method:  testGetDescription() 
	 * Purpose: It tests the getDescription() method. It should probably return the item's description.
	 */
	
	@Test
	public void testGetDescription() {
		assertTrue(potion.getDescription().equals(potion.toString() + " - (" + potion.getItemName() +") " + "This potion was brewed by the kingdom's finest apothecary. It is capable of healing the user by 10 HP."));
		assertTrue(calcium.getDescription().equals(calcium.toString() + " - (" + calcium.getItemName() +") " + "This mysterious powder is said to make its user healthier by strengthening their bones."));
		assertTrue(gc.getDescription().equals(gc.toString() + " - (" + gc.getItemName() +") " + "This item turns its user into a figurative glass cannon--greatly increasing attack at the cost of defence."));
		assertTrue(hipotion.getDescription().equals(hipotion.toString() + " - (" + hipotion.getItemName() +") " + "This potion was brewed by the kingdom's finest apothecary. It is capable of healing the user by 20 HP."));
		assertTrue(iron.getDescription().equals(iron.toString() + " - (" + iron.getItemName() +") " + "This mysterious powder is said to raise the pain tolerance of whoever uses it."));
		assertTrue(ks.getDescription().equals(ks.toString() + " - (" + ks.getItemName() +") " + "A heavy metal shield mainly used by cavalry units."));
		assertTrue(ls.getDescription().equals(ls.toString() + " - (" + ls.getItemName() +") " + "A longsword smithed in the great forges of the royal blacksmiths."));
		assertTrue(ms.getDescription().equals(ms.toString() + " - (" + ms.getItemName() +") " + "A longsword that lets off a strange glow when wielded. It is said to have been the weapon of choice of a particular Hero of Time."));
		assertTrue(mp.getDescription().equals(mp.toString() + " - (" + mp.getItemName() +") " + "A legendary potion that most people have only read about in tales of lore. It completely heals its user."));
		assertTrue(mp2.getDescription().equals(mp2.toString() + " - (" + mp2.getItemName() +") " + "A strange concoction whose effect is unknown. Some tales tell of its wondrous healing abilities. Others tell of its users meeting a terrible fate."));
		assertTrue(ns.getDescription().equals(ns.toString() + " - (" + ns.getItemName() +") " + "You have no shield equipped."));
		assertTrue(protein.getDescription().equals(protein.toString() + " - (" + protein.getItemName() +") " + "This mysterious powder is said to raise the physical strength of whoever uses it."));
		assertTrue(ss.getDescription().equals(ss.toString() + " - (" + ss.getItemName() +") " + "A metal shield that was crafted in the shape of a square. It seems to barely offer any defence."));
		assertTrue(s.getDescription().equals(s.toString() + " - (" + s.getItemName() +") " + "A sword painstakingly crafted by the local blacksmith."));
		assertTrue(u.getDescription().equals(u.toString() + " - (" + u.getItemName() +") " + "These are your hands."));
		assertTrue(wd.getDescription().equals(wd.toString() + " - (" + wd.getItemName() +") " + "A single strand of pasta. Truly a terrifying weapon."));
		assertTrue(ws.getDescription().equals(ws.toString() + " - (" + ws.getItemName() +") " + "A shield crudely made from a leftover piece of timber."));
		assertTrue(t.getDescription().equals(t.toString() + " - (" + t.getItemName() +") " + "A slightly worn-out treasure chest possibly containing untold riches."));
	}
	
	/**
	 * Method:  testGetItemName() 
	 * Purpose: It tests the getItemName() method. It should probably return the item's name.
	 */
	
	@Test
	public void testGetItemName() {
		assertTrue(potion.getItemName().equals("Potion"));
		assertTrue(calcium.getItemName().equals("Calcium"));
		assertTrue(gc.getItemName().equals("Glass-Cannon"));
		assertTrue(hipotion.getItemName().equals("Hi-Potion"));
		assertTrue(iron.getItemName().equals("Iron"));
		assertTrue(ks.getItemName().equals("Kite-Shield"));
		assertTrue(ls.getItemName().equals("Longsword"));
		assertTrue(ms.getItemName().equals("Mastersword"));
		assertTrue(mp.getItemName().equals("Max-Potion"));
		assertTrue(mp2.getItemName().equals("Mystery-Potion"));
		assertTrue(ns.getItemName().equals("No Shield"));
		assertTrue(protein.getItemName().equals("Protein"));
		assertTrue(ss.getItemName().equals("Square-Shield"));
		assertTrue(s.getItemName().equals("Sword"));
		assertTrue(u.getItemName().equals("Unarmed"));
		assertTrue(wd.getItemName().equals("Wet-Noodle"));
		assertTrue(ws.getItemName().equals("Wooden-Shield"));
		assertTrue(t.getItemName().equals("Treasure-Chest"));
	}
	
	/**
	 * Method:  testUse() 
	 * Purpose: It tests the potion's use() method.
	 */
	
	@Test
	public void testUse() {
		Player c = new Player(10,10,10,"Bob");
		
		//Potion
		//Player had max hp and used a potion, so it doesn't change anything
		assertTrue(c.getCurrentHealth() == 10);
		potion.use(c);
		assertTrue(c.getCurrentHealth() == 10);
		
		//Player had less than max, but the potion shouldn't bring him over max
		c.setCurrentHealth(1);
		assertTrue(c.getCurrentHealth() == 1);
		potion.use(c);
		assertTrue(c.getCurrentHealth() == 10);
		
		//Player is healed for 10 hp
		c.setCurrentHealth(0);
		assertTrue(c.getCurrentHealth() == 0);
		potion.use(c);
		assertTrue(c.getCurrentHealth() == 10);
		
		//Calcium
		c.setMaxHealth(10);
		assertTrue(c.getMaxHealth() == 10);
		calcium.use(c);
		assertTrue(c.getMaxHealth() == 15);
		c.setMaxHealth(150);
		calcium.use(c);
		assertTrue(c.getMaxHealth() == 150);
		
		//GlassCannon
		assertTrue(c.getAttack() == 10);
		assertTrue(c.getDefence() == 10);
		gc.use(c);
		assertTrue(c.getAttack() == 15);
		assertTrue(c.getDefence() == 3);
		gc.use(c);
		assertTrue(c.getAttack() == 20);
		assertTrue(c.getDefence() == 1);
		
		//HiPotion
		//Player had max hp and used a potion, so it doesn't change anything
		c.setCurrentHealth(10);
		c.setMaxHealth(10);
		assertTrue(c.getCurrentHealth() == 10);
		hipotion.use(c);
		assertTrue(c.getCurrentHealth() == 10);

		//Player had less than max, but the potion shouldn't bring him over max
		c.setCurrentHealth(1);
		assertTrue(c.getCurrentHealth() == 1);
		hipotion.use(c);
		assertTrue(c.getCurrentHealth() == 10);

		//Player is healed for 20 hp
		c.setCurrentHealth(0);
		c.setMaxHealth(21);
		assertTrue(c.getCurrentHealth() == 0);
		hipotion.use(c);
		assertTrue(c.getCurrentHealth() == 20);
		
		//Iron
		c.setAttack(10);
		c.setDefence(10);
		assertTrue(c.getAttack() == 10);
		assertTrue(c.getDefence() == 10);
		iron.use(c);
		assertTrue(c.getDefence() == 11);
		c.setDefence(20);
		iron.use(c);
		assertTrue(c.getDefence() == 20);
		
		//Kiteshield
		assertTrue(c.getShield() instanceof NoShield);
		ks.use(c);
		assertTrue(c.getShield() instanceof Kiteshield);
		
		//Longsword
		assertTrue(c.getWeapon() instanceof Unarmed);
		ls.use(c);
		assertTrue(c.getWeapon() instanceof Longsword);
		
		//Mastersword
		assertTrue(c.getWeapon() instanceof Longsword);
		ms.use(c);
		assertTrue(c.getWeapon() instanceof Mastersword);
		
		//MaxPotion
		//Player had max hp and used a potion, so it doesn't change anything
		c.setMaxHealth(20);
		c.setCurrentHealth(20);
		assertTrue(c.getCurrentHealth() == 20);
		mp.use(c);
		assertTrue(c.getCurrentHealth() == 20);

		//Player had less than max, but the potion shouldn't bring him over max
		c.setCurrentHealth(0);
		assertTrue(c.getCurrentHealth() == 0);
		mp.use(c);
		assertTrue(c.getCurrentHealth() == 20);

		//Player is healed for full hp
		c.setCurrentHealth(0);
		c.setMaxHealth(999);
		assertTrue(c.getCurrentHealth() == 0);
		mp.use(c);
		assertTrue(c.getCurrentHealth() == 999);
		
		//MysteryPotion
		for (int i = 0; i < 30; i++) {
			c.setCurrentHealth(16);
			c.setMaxHealth(30);
			assertTrue(c.getCurrentHealth() == 16);
			mp2.use(c);
			assertTrue(c.getCurrentHealth() == 1 || c.getCurrentHealth() == 30);
			c.setCurrentHealth(15);
			c.setMaxHealth(30);
			assertTrue(c.getCurrentHealth() == 15);
			mp2.use(c);
			assertTrue(c.getCurrentHealth() == 1 || c.getCurrentHealth() == 30);
		}
		
		//NoShield
		assertTrue(c.getShield() instanceof Kiteshield);
		ns.use(c);
		assertTrue(c.getShield() instanceof NoShield);
		
		//Protein
		c.setAttack(10);
		c.setDefence(10);
		assertTrue(c.getAttack() == 10);
		assertTrue(c.getDefence() == 10);
		protein.use(c);
		assertTrue(c.getAttack() == 11);
		c.setAttack(20);
		protein.use(c);
		assertTrue(c.getAttack() == 20);
		
		//SquareShield
		assertTrue(c.getShield() instanceof NoShield);
		ss.use(c);
		assertTrue(c.getShield() instanceof SquareShield);
		
		//Sword
		assertTrue(c.getWeapon() instanceof Mastersword);
		s.use(c);
		assertTrue(c.getWeapon() instanceof Sword);
		
		//Unarmed
		assertTrue(c.getWeapon() instanceof Sword);
		u.use(c);
		assertTrue(c.getWeapon() instanceof Unarmed);
		
		//WetNoodle
		assertTrue(c.getWeapon() instanceof Unarmed);
		wd.use(c);
		assertTrue(c.getWeapon() instanceof WetNoodle);
		
		//WoodenShield
		assertTrue(c.getShield() instanceof SquareShield);
		ws.use(c);
		assertTrue(c.getShield() instanceof WoodenShield);
		
		//TreasureChest
		Player p = new Player(10,10,10,"Bob");
		assertTrue(p.getInventory().size() == 0);
		t.use(p);
		assertTrue(p.getInventory().size() == 0 || p.getInventory().size() == 1);
		
		for (int i = 0; i < 30; i++) {
			t.use(p);
		}
		
		assertTrue(p.getInventory().size() > 1);
	}
	
	/**
	 * Method:  testGetIsConsumable() 
	 * Purpose: It tests the isConsumable() method.
	 */
	
	@Test
	public void testIsConsumable() {
		assertTrue(potion.isConsumable());
		assertTrue(calcium.isConsumable());
		assertTrue(gc.isConsumable());
		assertTrue(hipotion.isConsumable());
		assertTrue(iron.isConsumable());
		assertTrue(!ks.isConsumable());
		assertTrue(!ls.isConsumable());
		assertTrue(!ms.isConsumable());
		assertTrue(mp.isConsumable());
		assertTrue(mp2.isConsumable());
		assertTrue(!ns.isConsumable());
		assertTrue(protein.isConsumable());
		assertTrue(!ss.isConsumable());
		assertTrue(!s.isConsumable());
		assertTrue(!u.isConsumable());
		assertTrue(!wd.isConsumable());
		assertTrue(!ws.isConsumable());
		assertTrue(t.isConsumable());
	}
	
	/**
	 * Method:  testToString() 
	 * Purpose: It tests the toString() method. It should probably return the character representation of the item.
	 */
	
	@Test
	public void testToString() {
		assertTrue(potion.toString().equals("o"));
		assertTrue(calcium.toString().equals("c"));
		assertTrue(gc.toString().equals("g"));
		assertTrue(hipotion.toString().equals("h"));
		assertTrue(iron.toString().equals("i"));
		assertTrue(ks.toString().equals("k"));
		assertTrue(ls.toString().equals("l"));
		assertTrue(ms.toString().equals("m"));
		assertTrue(mp.toString().equals("x"));
		assertTrue(mp2.toString().equals("j"));
		assertTrue(ns.toString().equals("i"));
		assertTrue(protein.toString().equals("b"));
		assertTrue(ss.toString().equals("q"));
		assertTrue(s.toString().equals("s"));
		assertTrue(u.toString().equals("i"));
		assertTrue(wd.toString().equals("n"));
		assertTrue(ws.toString().equals("w"));
		assertTrue(t.toString().equals("t"));
	}
	
	/**
	 * Method:  testCollection() 
	 * Purpose: It tests the static item collection class.
	 */
	
	@Test
	public void testCollection() {
		ArrayList<Item> a = ItemCollection.allItems;
		assertTrue(a.size() > 0);
		
		assertTrue(!ItemCollection.isAnItem("bob"));
		assertTrue(ItemCollection.isAnItem("potion"));
	}
}
