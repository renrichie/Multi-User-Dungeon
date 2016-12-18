package model;

/**
 * Author:   Chris Derck
 * File:     RulesAndCommands.java
 * Purpose:  The RulesAndCommands class is simply an object that contains the game's rules and commands for playing.
 */

public class RulesAndCommands {
	private String info;
	
	public RulesAndCommands () {
		this.info = "";
		
		Info();
	}
	
	/**
	 * Method:  Info() 
	 * Purpose: It sets the rules and info for the game.
	 */
	
	public void Info() {
		//This is where all the rules and info will be added for the game
		this.info = "Rules: \n"
				+ "1. Players all start in the same location regardless of when they joined the game.\n"
				+ "2. Monsters are capable of attacking players standing in any tiles \n"
				+ "    adjacent or diagonal away from them (up to one space).\n"
				+ "3. Players are capable of attacking players/mobs standing in any tiles\n"
				+ "    adjacent or diagonal away from them (up to one space).\n"
				+ "4. It is possible to attack another player, not just mobs.\n"
				+ "5. Upon death, a player is asked if they would like to restart \n"
				+ "    at the start with no items and default stats.\n"
				+ "6. Player locations and the state of the game map are saved with \n"
				+ "    every action executed, and it is persistent throughout launches and clients.\n"
				+ "7. Players are only able to see the contents of the room that they are currently in.\n"
				+ "8. In order to move to another room, a player simply moves beyond \n"
				+ "    the boundary of their current room.\n"
				+ "9. Mobs have a chance of dropping an item upon death.\n"
				+ "10.You can give other players items, not but mobs.\n"
				+ "\n"
				+ "Commands: \n"
				+ "Help - Lists out all the commands\n"
				+ "Quit - Logs you out of the server\n"
				+ "Shutdown <password> - Shuts down the server\n"
				+ "Score - Describes the current player\n"
				+ "Who - Lists the users currently online\n"
				+ "Say <message> - Sends a message to all players in the current room\n"
				+ "Tell <player> <message> - Whispers a private message to the specified player\n"
				+ "OOC <message> - Sends a message to all players on the server\n"
				+ "Look - Describes the current room and the elements in it\n"
				+ "Look <itemName> - Describes the specified item\n"
				+ "Look <player/mobName> - Lists out the player/mob's stats\n"
				+ "Give <itemName> <targetName> - Gives the item to the target player if it is in the giver's inventory\n"
				+ "Take <itemName> <targetName> - Take the item from the target player if it is in the target's inventory\n"
				+ "Drop <itemName> - Drops the item from the player's inventory\n"
				+ "Inventory - Lists out what is in the player's inventory\n"
				+ "Up/Down - Checks out the ceiling or floor of the current room\n"
				+ "Use <itemName> - Uses the specified item only if it is in the player's inventory; \n"
				+ "\t         also used to equip weapons/shields\n"
				+ "Grab <itemName/itemCharacter> - Grabs the specified item from an adjacent tile or the tile the player is on\n"
				+ "Interact door - Opens/closes an adjacent door\n"
				+ "Attack <targetName/targetCharacter> - Attacks the specified target (mob or player) if they are in an \n"
				+ "\t\t                    adjacent or diagonal tile or the tile the player is on\n"
				+ "North/East/South/West - Moves the player in the specified direction\n";
	}
	
	/**
	 * Method:  getInfo() 
	 * Purpose: It returns the rules and info for the game.
	 */
	
	public String getInfo() {
		return info;
	}
}
