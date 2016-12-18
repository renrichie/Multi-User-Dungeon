package client.model;

import server.command.*;
import server.response.AccountCreationResponse;
import server.response.GameStateResponse;
import server.response.LogoffResponse;
import server.response.LogonResponse;
import server.response.ServerResponse;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Vector;

import javax.swing.Timer;

import model.Account;
import model.Location;
import model.MoveDirection;
import model.MudEntity;
import model.MudModelIntf;
import model.Player;
import model.Room;
import model.item.Item;
import model.item.ItemCollection;
import model.item.Shield;
import model.item.Weapon;
import model.mob.Mob;

/**
 * Author:  Brian Lovelace
 * File:    ClientModelIntf.java
 * Purpose:	This class acts as the link between the MudModel, the server,
 * 			and the Graphical User Interface on the client side. Under no
 * 			circumstances should a front end class interact with the MudGame 
 * 			class directly. All interaction with MudGame on the client side 
 * 			should be done through this implementation of ClientModelIntf.  
 */
public class ClientModel extends Observable implements ClientModelIntf
{	
	private final int UPDATE_FREQ_MILLIS = 3000;
	private ProxyServer server;
	private MudModelIntf model;
	private String username;
	private Room prevRoom, currRoom;
	private int prevCount, currCount, currX, currY, prevX, prevY;
	private Location loc;
	private int chatlog = 0;
	private int user_verification = Account.NO_VERIFY;
	private volatile boolean sessionActive;
	private Timer timer;
	
	//Constructor that creates a new instance of CLientModel with the passed in info of the server to connect to. 
	public ClientModel(String address, int port) 
			throws UnknownHostException, IOException
	{
		this.model = null;	
		this.server = new ProxyServer(address, port);
		this.server.start();
		
		this.prevX = -1;
		this.prevY = -1;
		this.prevCount = 1;
		timer = new Timer(UPDATE_FREQ_MILLIS, new UpdateListener());
	}
	
	/**
	 *Method:	sessionInactive()
	 *Purpose:	Method sets the current user to inactive and notifies the observer of the change.  
	 */
	private void sessionInactive()
	{
		sessionActive = false;
		timer.stop();
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 *Method:	sessionActive()
	 *Purpose:	Returns whether the current client is active on the server or not.  
	 */
	@Override 
	public boolean sessionActive()
	{
		return sessionActive;
	}
	
	/**
	 *Method:	addAccount()
	 *Purpose:	Sends off a create account command to the server.   
	 */
	@Override
	public void addAccount(String username, String password)
	{
		Command command = new CreateAccountCommand(username, password);
		server.send(command);	
	}

	/**
	 *Method:	giveItem(String item, String target)
	 *Purpose:	Sends off a give command to the server.   
	 */
	@Override
	public void giveItem(String item, String target)
	{
		Command command = new GiveCommand(username, user_verification, item, target);
		server.send(command);	
	}
	
	/**
	 *Method:	takeItem(String item, String target)
	 *Purpose:	Sends off a take command to the server.   
	 */
	@Override
	public void takeItem(String item, String target)
	{
		Command command = new TakeCommand(username, user_verification, item, target);
		server.send(command);	
	}
	
	/**
	 *Method:	login()
	 *Purpose:	Sends off a logon command to the server.   
	 */
	@Override
	public void login(String username, String password)
	{
		Command command = new LogonCommand(username, password);
		server.send(command);
	}

	/**
	 *Method:	logout()
	 *Purpose:	Sends off a logoff account command to the server.   
	 */
	@Override
	public void logout()
	{
		if (username != null)
		{
			Command command = new LogoffCommand(username, user_verification);
			server.send(command);
			timer.stop();
		}
	}

	/**
	 *Method:	move(MoveDirection dir)
	 *Purpose:	Sends off a move command to the server while also passing off the direction to move in.   
	 */
	@Override
	public void move(MoveDirection dir)
	{
		Command command = new MoveCommand(username, user_verification, dir);
		server.send(command);
		
	}

	/**
	 *Method:	grabItem(String item)
	 *Purpose:	Sends off a GrabItem command to the server while also passing the item to grab.   
	 */
	@Override
	public void grabItem(String item)
	{
		Command command = new GrabItemCommand(username, user_verification, item);
		server.send(command);
		
	}

	/**
	 *Method:	dropItem(String item)
	 *Purpose:	Sends off a drop command to the server while also passing the item to drop.    
	 */
	@Override
	public void dropItem(String item)
	{
		Command command = new DropCommand(username, user_verification, item);
		server.send(command);
		
	}

	/**
	 *Method:	addAccount()
	 *Purpose:	Sends off a UseItem command to the server while also passing the item to use.   
	 */
	public void useItem(String itemName)
	{
		Command command = new UseItemCommand(username, user_verification, itemName);
		server.send(command);
	}
	
	/**
	 *Method:	attackEntity(String entity)
	 *Purpose:	Sends off an attack command to the server while also passing the entity to attack.   
	 */
	@Override
	public void attackEntity(String entity)
	{
		Command command = new AttackCommand(username, user_verification, entity);
		server.send(command);		
	}

	/**
	 *Method:	interact(String entity)
	 *Purpose:	Sends off an interact command to the server while also passing the entity to interact with.   
	 */
	@Override
	public void interact(String entity) {
		Command command = new InteractCommand(username, user_verification, entity);
		server.send(command);
	}
	
	/**
	 *Method:	say(String said, String target)
	 *Purpose:	Sends off a say command to the server while passing the message to send and the target to send it to.  
	 */
	@Override
	public void say(String said, String target)
	{
		Command command = new SpeakCommand(username, user_verification, said, target);
		server.send(command);
	}
	
	/**
	 *Method:	respawnPlayer()
	 *Purpose:	Sends off a respawn command to the server.   
	 */
	@Override
	public void respawnPlayer() {
		Command command = new RespawnCommand(username, user_verification);
		server.send(command);
	}

	/**
	 *Method:	shutdown(String password)
	 *Purpose:	Sends off a shutdown command to the server while passing the password to shut the server down.   
	 */
	@Override
	public void shutdown(String password)
	{
		Command command = new ShutDownCommand(password);
		server.send(command);
		timer.stop();
	}
	
	/**
	 *Method:	look()
	 *Purpose:	Gets the current room location of the active player, and from that, adds all of the items, players
	 *			and mobs that are currently within the room to a string that is then sent back to the user to be 
	 *			displayed within the console.     
	 */
	@Override
	public String look()
	{
		String s = ""; 
		if (username == null) return null;
		Player player = model.getPlayer(username);
		if (player == null) return null;
		Point roomCoord = player.getLocation().getRoom();
		if (roomCoord == null) return null;
		int x = roomCoord.x;
		int y = roomCoord.y;
		Room room = model.getRoom(x, y);

		for (MudEntity e : room.getEntities())
		{
			if (e instanceof Player)
				s += "Player: " + ((Player) e).getIGN() + "\n";
			else if (e instanceof Mob)
				s += "Mob: " + e.getDescription() + "\n";
			else if (e instanceof Item)
				s += "Item: " + e.getDescription() + "\n";
		}
		s += room.calculateExits();
		return s;
	}
	
	/**
	 *Method:	lookInventory()
	 *Purpose:	Accesses the current user's inventory and adds all of the items to a string. String is then returned to the user
	 *			to be displayed within the console.    
	 */
	@Override
	public String lookInventory()
	{
		String s = "Inventory: ";
		
		if (username != null) {
			Player player = model.getPlayer(username);
			if (player != null) {
				ArrayList<Item> inventory = player.getInventory();
				
				if (inventory.size() == 0) {
					return s + "\n";
				}
				
				for (Item i : inventory) {
					s += i.getItemName() + ", ";
				}
				
				s = s.substring(0, s.length() - 2) + "\n";
			}
		}
		
		return s;		
	}
	
	/**
	 *Method:	lookEntity(String entity)
	 *Purpose:	Takes the string parameter of the entity in question, and then checks the current user's room to see if
	 *			that particular entity is within the same room as the user. If it is, then all of the stats of the 
	 *			entity will be added to a string and sent back to the user to be displayed in the console.   
	 */
	@Override
	public String lookEntity(String entity) 
	{
		String s = "";
		
		if (username == null) return null;
		Player player = model.getPlayer(username);
		if (player == null) return null;
		Point roomCoord = player.getLocation().getRoom();
		if (roomCoord == null) return null;
		int x = roomCoord.x;
		int y = roomCoord.y;
		Room room = model.getRoom(x, y);
		
		for (MudEntity e : room.getEntities())
		{
			if (e instanceof Player && ((Player) e).getIGN().toLowerCase().equals(entity)) {
				if (s.length() > 5) {
					s += "\n";
				}
				s += ((Player) e).getIGN() + "\n" + "HP: " + ((Player) e).getCurrentHealth() + "/" + 
						((Player) e).getMaxHealth() + " | Weapon: " + ((Player) e).getWeapon().getItemName() + 
						" | Shield: " + ((Player) e).getShield().getItemName();
			}
			else if (e instanceof Mob && ((Mob) e).getMobName().toLowerCase().equals(entity)) {
				if (s.length() > 5) {
					s += "\n";
				}
				s += ((Mob) e).getMobName() + "\n" + "HP: " + ((Mob) e).getHealth() + " | Attack: " +
						((Mob) e).getAttack() + " | Defence: " + ((Mob) e).getDefence();
			}
		}

		
		return s;
	}
	
	/**
	 *Method:	lookItem(String item)
	 *Purpose:	Takes the passed in string that is the item in question, and checks to see if that item is contained within
	 *			the item list of the game. If it does exist, the description of the item will be added to a string and returned
	 *			to the user to be displayed within the console.   
	 */
	@Override
	public String lookItem(String item) 
	{
		String s = "";
		
		ArrayList<Item> items = ItemCollection.allItems;
		
		for (Item i : items) {
			if (item.toLowerCase().trim().equals(i.getItemName().toLowerCase())) {
				s += i.getDescription();
				
				if (i instanceof Weapon) {
					s += " | Attack Multiplier = " + ((Weapon) i).getAttackMultiplier();
				}
				else if (i instanceof Shield) {
					s += " | Defence Multiplier = " + ((Shield) i).getDefenceMultiplier();
				}
			}
		}
		return s;
	}
	
	/**
	 *Method:	getNewChat()
	 *Purpose:	Checks to see if any new messages were added to the user chat array. If there were new messages for the
	 *			user, they will be added to user's chat message array. The array is then returned to the user.   
	 */
	@Override
	public ArrayList<Vector<String>> getNewChat()
	{
		if (username == null) return null;
		Player p = model.getPlayer(username);
		if (p == null) return null;
		
		ArrayList<Vector<String>> messages = new ArrayList<Vector<String>>();
		
		for (int i = this.chatlog; i < p.getChatLog().size()
				&& i >= 0; i++)
		{
			messages.add(p.getChatLog().get(i));
		}
		this.chatlog = p.getChatLog().size();
		return messages;
	}
	
	/**
	 *Method:	getRoom()
	 *Purpose:	Returns the current room that the user is in.   
	 */
	@Override
	public Room getRoom()
	{
		if (username == null) return null;
		
		Player player = model.getPlayer(username);
		if (player == null) return null;
		
		Location loc = player.getLocation();
		if (loc == null) return null;
		Point room = loc.getRoom();
		if (room != null)
			return model.getRoom(room.x, room.y);
		return null;
	}
	
	/**
	 *Method:	lookUp()
	 *Purpose:	Checks the current room that the user is in and sends the string that is displayed on that current room's ceiling.    
	 */
	@Override
	public String lookUp()
	{
		Room room = getRoom();
		if (room != null)
			return room.lookUp();
		else
			return "";
	}

	/**
	 *Method:	lookDown()
	 *Purpose:	Checks the current room that the user is in and sends the string that is displayed on that current room's floor.    
	 */
	@Override
	public String lookDown()
	{
		Room room = getRoom();
		if (room != null)
			return room.lookDown();
		else
			return "";
	}

	/**
	 *Method:	getPlayer()
	 *Purpose:	Returns the current user's player.    
	 */
	@Override
	public Player getPlayer()
	{
		Player player = null;
		
		if (username != null)
		{
			player = model.getPlayer(username);
		}
		return player;
	}
	
	/**
	 *Method:	getPlayers()
	 *Purpose:	Returns a string representation of all of the currently active players that are in the game.     
	 */
	@Override
	public String getPlayers()
	{
		String s = "Players: ";
		
		ArrayList<Player> players = (ArrayList<Player>) model.getPlayers();
		
		if (players != null) {
			for (Player p : players) {
				s += p.getIGN() + ", ";
			}
		}
		s = s.substring(0, s.length() - 2) + "\n";
		return s;
	}
	
	/**
	 *Method:	roomCheck()
	 *Purpose:	Returns an integer representation of whether any new players have entered or exited the room that
	 *			the user's player is currently in. The new position of the player as well as the amount of players
	 *			that are currently in their room is also set to once again be checked later on.     
	 */
	@Override
	public int	roomCheck() {
		Location l = model.getPlayer(username).getLocation();
		currX = l.getRoom().x;
		currY = l.getRoom().y;
		
		currRoom = model.getRoom(l.getRoom().x, l.getRoom().y);	
		currCount = playerCount();
		int value = -1;
		
		
		if (prevX != -1) {
			//No change
			if ((currX == prevX && currY == prevY) && prevCount == currCount || (currX != prevX || currY != prevY))
				value = 0;
			//A player has left
			else if ((currX == prevX && currY == prevY) && prevCount > currCount)
				value = 1;
			//A player has joined
			else {
				value = 2;
			}
		}
		
		prevX = currX;
		prevY = currY;
		prevCount = currCount;
		
		return value;
	}
	
	/**
	 *Method:	playerCount()
	 *Purpose:	Returns the amount of players that are currently in the room that the user's player is in.     
	 */
	@Override
	public int playerCount()
	{
		int count = 0;
		
		if (username == null) return -1;
		Player player = model.getPlayer(username);
		if (player == null) return -1;
		Point roomCoord = player.getLocation().getRoom();
		if (roomCoord == null) return -1;
		int x = roomCoord.x;
		int y = roomCoord.y;
		Room room = model.getRoom(x, y);
		
		for (MudEntity e : room.getEntities())
		{
			if (e instanceof Player)
				count++;
				
		}
		
		return count;
	}

	
	/**
	 * Performs an action based on the type of ServerResponse received.
	 * @param response
	 */
	private void handleResponse(ServerResponse response)
	{
		if (response instanceof AccountCreationResponse)
		{
			AccountCreationResponse res = (AccountCreationResponse) response;
			if (res.success)
			{
				username = res.username;
				user_verification = res.USER_VERIFICATION;
				sessionActive = true;
				timer.start();
			}
		}
		else if (response instanceof LogonResponse)
		{
			LogonResponse res = (LogonResponse) response;
			if (res.success)
			{
				username = res.username;
				user_verification = res.USER_VERIFICATION;	
				sessionActive = true;
				timer.start();
			}
		}
		else if (response instanceof LogoffResponse)
		{
			LogoffResponse res = (LogoffResponse) response;
			if (res.logOffSuccess)
			{
				username = null;
				user_verification = Account.NO_VERIFY;
			} 
		}
		else if (response instanceof GameStateResponse)
		{
			GameStateResponse res = (GameStateResponse) response;
			if (res.success)
			{
				swapModel(res.updatedmodel);
			}
		}
		this.setChanged();
		this.notifyObservers(response);
	}
	
	/**
	 *Method:	swapModel(MudModelIntf newmodel)
	 *Purpose:	Swaps the current model of the client's game with the new model that is passed in from the server.     
	 */
	private void swapModel(MudModelIntf newmodel)
	{
		if (newmodel != null)
		{
			newmodel.reset();
			if (this.model != null)
			{
				this.model.destroy();
			}
			this.model = newmodel;
			if (username != null)
			{
				Player p = newmodel.getPlayer(username);
				p.setActive();
			}
		}
		
	}
	
	/**
	 * Requests an update from the server every few moments;
	 * @author brian
	 *
	 */
	
	/**
	 * Class:   MobListener
	 * Purpose: It updates the mobs based on a timer.
	 */
	
	private class UpdateListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Command command = new UpdateCommand(username, user_verification);
			server.send(command);
		}
		
	}
	
	/**
	 *Class:	ProxyServer()
	 *Purpose:	Client makes a connection with the game server and sets up the inputstream and output stream to the server.     
	 */
	private class ProxyServer extends Thread
	{
		private Socket server; 
		private ObjectInputStream fromServer;
		private ObjectOutputStream toServer;
		private boolean running = true;
		
		public ProxyServer(String address, int port) 
				throws UnknownHostException, IOException
		{
			server = new Socket(address, port);
			toServer = new ObjectOutputStream(server.getOutputStream());
			fromServer = new ObjectInputStream(server.getInputStream());
		}
		
				
		/**
		 *Method:	run()
		 *Purpose:	While the client is connected to the server, it will send and receive updates to/from the server and accordingly
		 *			update the board and notify the observer.    
		 */
		@Override
		public void run()
		{
			try
			{
				while(running)
				{				
					ServerResponse res = (ServerResponse)(fromServer.readObject());
					handleResponse(res);
				}
			} catch (ClassNotFoundException | IOException e)
			{
			}
			System.out.println("Client Thread done");
			cleanup();
		}
		
		/**
		 * Used to send a new command to the server.
		 * @param command
		 */
		public synchronized void send(Command command)
		{
			try
			{
				toServer.reset();
				toServer.writeObject(command);
			} catch (IOException e)
			{
				cleanup();
				running = false;
			}
		}
		
		/**
		 *Method:	cleanup()
		 *Purpose:	Cleans up the client when the connection is no longer active.     
		 */
		private synchronized void cleanup()
		{
			System.out.println("Client: Cleanup!!!");
			try
			{
				sessionActive = false;
				toServer.close();
				fromServer.close();
				server.close();
				sessionInactive();
			} catch (IOException e)
			{
			}
		}
	}
}
