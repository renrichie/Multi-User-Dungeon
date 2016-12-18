package model.util.worldgen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Door;
import model.MudEntity;
import model.background.*;
import model.item.*;
import model.mob.*;
import model.Room;
import model.Tile;

import model.item.Longsword;

/**
 * Reads XML nodes to determine the initial layout of the
 * Mud Rooms.
 * @author brian
 *
 */
public class LayoutGenerator implements Serializable
{
	
	private Room[][] rooms;
	private Tile[][] allTiles;
	private int board_size;
	private int room_size;
	
	private HashMap<String, Class<?>> allEntities = new HashMap<String, Class<?>>();
	private HashMap<Character, ArrayList<String>> asciiDefs = new HashMap<Character, ArrayList<String>>();
			
	protected LayoutGenerator(Node layoutDef, Node entityDefs)
	{
		NodeList nlist = layoutDef.getChildNodes();
		try 
		{
			this.board_size = Integer.parseInt(nlist.item(0).getTextContent().trim());
			this.room_size = Integer.parseInt(nlist.item(1).getTextContent().trim());
		}catch(NumberFormatException e)
		{
			throw new LayoutValidationException("Invalid Room or Board size");
		}
		
		Node tilesheet = nlist.item(2);
		Node charDefs = nlist.item(3);
		if (tilesheet == null || charDefs == null)
		{
			throw new LayoutValidationException("Validation error in LayoutDef");
		}
		rooms = new Room[board_size][board_size];
		allTiles = new Tile[rooms.length * room_size][rooms.length * room_size];
		
		setEntityDefs(entityDefs);
		setTileDefs(charDefs);
		setTiles(tilesheet.getTextContent());
		setRooms();
	}
	
	/**
	 * Method:  getRooms() 
	 * Purpose: It returns a 2D array of Rooms containing the map.
	 */
	
	public Room[][] getRooms()
	{
		return rooms;
	}
	
	/**
	 * Method:  setRooms() 
	 * Purpose: It sets up the rooms using the specified tiles.
	 */
	
	private void setRooms()
	{
		for (int x = 0; x < rooms.length; x++)
			for (int y = 0; y < rooms[0].length; y++)
			{
				Tile[][] roomtiles = new Tile[Room.ROOM_SIZE][Room.ROOM_SIZE];
				for (int j = (x * Room.ROOM_SIZE); j < ((x + 1) * Room.ROOM_SIZE); j++)
					for (int k = (y * Room.ROOM_SIZE); k < ((y + 1) * Room.ROOM_SIZE); k++)
					{
						roomtiles[j - x * Room.ROOM_SIZE][k - y * Room.ROOM_SIZE] = allTiles[j][k];
					}
				rooms[x][y] = new Room(roomtiles);
			}
	}
	
	/**
	 * Determines how Entities are defined in the XML.
	 */
	private void setEntityDefs(Node entityDefs)
	{
		Node entityNode = entityDefs.getFirstChild();
		while (entityNode != null)
		{
			String name = entityNode.getFirstChild().getTextContent().trim();
			Class<?> eclass = findClass(entityNode.getFirstChild().getTextContent().trim());
			this.allEntities.put(name, eclass);
			entityNode = entityNode.getNextSibling();
			entityNode = clearComments(entityNode);
			
		}
	}
	
	/**
	 * Translate Strings from the XML into MudEntity classes
	 * @param classname An XML representation of a MudEntity class.
	 * @return A MudEntity Class
	 */
	private Class<?> findClass(String classname)
	{
		switch(classname)
		{
			case "Wall":
				return Wall.class; 
			case "Water":
				return Water.class;
			case "Road":
				return Road.class;
			case "LongSword":
				return Longsword.class;
			case "Potion":
				return Potion.class;
			case "Sword":
				return Sword.class;
			case "Rabbit":
				return Rabbit.class;
			case "Demon":
				return Demon.class;
			case "Diablo":
				return Diablo.class;
			case "Treant":
				return Treant.class;
			case "Wooden Shield":
				return WoodenShield.class;
			case "Treasure Chest":
				return TreasureChest.class;
			case "Door":
				return Door.class;
			case "Polymorph":
				return Polymorph.class;
			case "Teleporter":
				return Teleporter.class;
			case "Calcium":
				return Calcium.class;
			case "Glass Cannon":
				return GlassCannon.class;
			case "Hi-Potion":
				return HiPotion.class;
			case "Iron":
				return Iron.class;
			case "Kiteshield":
				return Kiteshield.class;
			case "Mastersword":
				return Mastersword.class;
			case "Max Potion":
				return MaxPotion.class;
			case "Mystery Potion":
				return MysteryPotion.class;
			case "Protein":
				return Protein.class;
			case "Square Shield":
				return SquareShield.class;
			case "Wet Noodle":
				return WetNoodle.class;
			case "Goblin":
				return Goblin.class;
			case "Insect":
				return Insect.class;
			case "Mimic":
				return Mimic.class;
			case "MUD Project":
				return MUDProject.class;
			case "Pikachu":
				return Pikachu.class;
			case "Undead Warrior":
				return UndeadWarrior.class;
			default:
				throw new LayoutValidationException("Invalid class name: " + classname);
		}
	}
	
	/**
	 * Parses XML to record a definition for every Tile found
	 * in the tilesheet.
	 * @param charDefs CharDefs XML Node.
	 */
	private void setTileDefs(Node charDefs)
	{
		NodeList defs = charDefs.getChildNodes();
		if (defs.getLength() == 0)
			throw new LayoutValidationException("No tiles have been defined");
		Node def = defs.item(0);
		
		//goes over each charDef
		while (def != null)
		{
			String ascii = def.getFirstChild().getTextContent().trim();
			
			
			Node occupant = def.getLastChild().getLastChild().getFirstChild();
			ArrayList<String> entitynames = new ArrayList<String>();
			
			//adds each entity
			while (occupant != null)
			{
				String occName = occupant.getTextContent().trim();
				entitynames.add(occName);
				occupant = occupant.getNextSibling();
				occupant = clearComments(occupant);
			}
			asciiDefs.put(ascii.toCharArray()[0], entitynames);			
			def = def.getNextSibling();
			def = clearComments(def);
		}
	}
	
	/**
	 * Used at the end of loops to sift past any
	 * comments appearing in the XML.
	 * @param node starting node
	 * @return noncomment Node or null.
	 */
	public Node clearComments(Node node)
	{
		Node myNode = node;
		if (myNode == null) 
			return myNode;
		
		while (!myNode.hasChildNodes())
		{
			myNode = myNode.getNextSibling();
			if (myNode == null) break;
		}
		return myNode;
	}
	
	/**
	 * Sets up all of the tiles that make up the MudWorld.
	 * @param tilesheet Text from the layout node in the XML.
	 */
	private void setTiles(String tilesheet)
	{
		String[] rows = tilesheet.split("\n");
		int y = 0;
		for (String row : rows)
		{
			int x = 0;
			row = row.trim();
			if (row.isEmpty())
				continue;
			for (char t : row.toCharArray())
			{
				if (!asciiDefs.containsKey(t))
					throw new LayoutValidationException("Undefined tile character: " + t);
				
				ArrayList<MudEntity> entities = new ArrayList<MudEntity>();
				ArrayList<String> occupants = asciiDefs.get(t);
				for (String occ : occupants)
				{
					if (!allEntities.containsKey(occ))
						throw new LayoutValidationException("Undefined entity: " + occ);
					try
					{
						MudEntity myEntity = (MudEntity)allEntities.get(occ).newInstance();
						entities.add(myEntity);
					} catch (InstantiationException | IllegalAccessException e)
					{
						e.printStackTrace();
					}
				}
				//TODO debug this. May need new Tile(y, x, entities);
				this.allTiles[x][y] = new Tile(entities); //with entities
				x++;
			}
			if (x != allTiles.length)
				throw new LayoutValidationException("There are an incorrect number of tiles in row: " + x); 
			y++;
		}
		if (y != allTiles[0].length)
			throw new LayoutValidationException("There are an incorrect number of collumns defined");
	}
	
}
