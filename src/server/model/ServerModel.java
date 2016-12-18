package server.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Set;

import javax.swing.Timer;

import model.Account;
import model.MoveDirection;
import model.MudModelIntf;
import model.Player;
import server.response.AccountCreationResponse;
import server.response.GameStateResponse;
import server.response.LogoffResponse;
import server.response.LogonResponse;
import server.response.PlayerStateResponse;
import server.response.ServerResponse;

/**
 * Author:   Brian Lovelace
 * File:     ServerModel.java
 * Purpose:  The ServerModel class implements the ServerModelIntf.
 *           It is used to handle all commands that are sent to the server and execute their respective actions.
 */

public class ServerModel implements ServerModelIntf
{
	private static final String HASH_ALG = "SHA-256";
	
	private MudModelIntf mudmodel;
	private HashMap<String, Account> loggedOn;
	private AccountCollection accounts;
	private Timer timer;
	private final static int 
					DEFAULT_ROOMX = 0,
					DEFAULT_ROOMY = 0,
					DEFAULT_TILEX = 3,
					DEFAULT_TILEY = 1;
					

	public ServerModel (MudModelIntf gamemodel)
	{
		this.mudmodel = gamemodel;
		this.mudmodel.clearPlayers();
		this.loggedOn = new HashMap<String, Account>();
		AccountCollection acc = this.loadAccounts();
		if (acc != null)
			this.accounts = acc;
		else
			accounts = new AccountCollection();
		
		timer = new Timer(4000, new MobListener());
		timer.start();
	}

	/**
	 * Method:  clientLogon(String username, String password) 
	 * Purpose: It logs the client onto the server if they are not currently on and if the account info is correct.
	 */
	
	@Override
	public ServerResponse clientLogon(String username, String password)
	{
		Account user = accounts.verifyAccount(username, hashpassword(password));
		if (user == null || loggedOn.containsKey(username))
			return new LogonResponse(username, false, Account.NO_VERIFY);
		
		int uv = user.resetUserVerification();
		loggedOn.put(user.getUsername(), user);
		
		mudmodel.addEntity(user.getCharacter(), 
		                   ServerModel.DEFAULT_ROOMX, 
		                   ServerModel.DEFAULT_ROOMY,
		                   ServerModel.DEFAULT_TILEX,
		                   ServerModel.DEFAULT_TILEY);
		return new LogonResponse(user.getUsername(), true, uv);
	}

	/**
	 * Method:  clientLogoff(String username, int uv) 
	 * Purpose: It logs the client off the server if the client sending it is the same one that the account is using.
	 */
	
	@Override
	public ServerResponse clientLogoff(String username, int uv)
	{
		if (loggedOn.containsKey(username))
			if (loggedOn.get(username).isVerified(uv))
			{
				Account acc = loggedOn.remove(username);
				acc.clearUserVerification();
				mudmodel.removeEntity(acc.getCharacter());
				if (saveAccounts())
				{
					
				}
				else
				{
					//TODO
				}
				return new LogoffResponse(acc, true);
			}
		return new LogoffResponse(null, false);
	}

	/**
	 * Method:  logOffAllClients()
	 * Purpose: It removes all players from their current location and then logs them off.
	 */
	
	public void logOffAllClients() {
		Set<String> keys = loggedOn.keySet();
		
		for (String s : keys) {
			if (loggedOn.containsKey(s)) {
				Account acc = loggedOn.get(s);
				mudmodel.removeEntity(acc.getCharacter());
			}
		}
		
		saveState();
		
		for (String s : keys) {
			if (loggedOn.containsKey(s)) {
				Account acc = loggedOn.remove(s);
				acc.clearUserVerification();
				mudmodel.removeEntity(acc.getCharacter());
			}
		}
		
		saveState();
	}
	
	/**
	 * Method:  addAccount(String username, String password) 
	 * Purpose: It creates an account in the database with the specified details if the username does not already exist.
	 */
	
	@Override
	public ServerResponse addAccount(String username, String password)
	{
		boolean added = accounts.addAccount(username, hashpassword(password));
		if (added)
		{
			Account acc = accounts.verifyAccount(username, hashpassword(password));
			LogonResponse res = (LogonResponse)(this.clientLogon(username, password));
			saveAccounts();
			return new AccountCreationResponse(acc.getUsername(), true, res.USER_VERIFICATION);
		}
		return new AccountCreationResponse(null, false, Account.NO_VERIFY);
	}
	
	/**
	 * Method:  saveState() 
	 * Purpose: It saves all the info of the server and writes it to a file.
	 */
	
	@Override
	public ServerResponse saveState()
	{
		this.saveAccounts();
		try
		{
			FileOutputStream fout = new FileOutputStream("./serverstate/mudgame.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this.mudmodel);
			oos.close();
			fout.close();
			return new GameStateResponse(mudmodel, true, true);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("Server-side save unsuccessful");
		return new GameStateResponse(null, false, true);

	}

	/**
	 * Method:  savePlayerState(String username, int uv) 
	 * Purpose: It saves the player's state to the server.
	 */
	
	@Override
	public ServerResponse savePlayerState(String username, int uv)
	{
		mudmodel.getPlayers();
		if (loggedOn.containsKey(username))
			if (loggedOn.get(username).isVerified(uv))
			{
				if (saveAccounts())
					return new PlayerStateResponse(loggedOn.get(username), 
					                               "Successfully saved " + username);
			}
		return new PlayerStateResponse(null, "Could not save " + username);
	}
	
	/**
	 * Method:  saveAccounts() 
	 * Purpose: It saves all the accounts and writes them to a file.
	 */
	
	private synchronized boolean saveAccounts()
	{
		try
		{
			FileOutputStream fout = new FileOutputStream("./serverstate/accounts.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this.accounts);
			oos.close();
			fout.close();
			return true;
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;

	}
	
	/**
	 * Method:  loadAccounts() 
	 * Purpose: It loads all the accounts from the file.
	 */
	
	private synchronized AccountCollection loadAccounts()
	{
		AccountCollection acc = null;
		
		FileInputStream fin;
		try
		{
			fin = new FileInputStream("./serverstate/accounts.ser");
			ObjectInputStream ois = new ObjectInputStream(fin);
			acc = (AccountCollection)(ois.readObject());
			ois.close();
			fin.close();
			acc.resetChatlogs();
			return acc;
		} catch (FileNotFoundException e)
		{
		} catch (IOException e)
		{
		} catch (ClassNotFoundException e)
		{
		}		
		System.out.println("Load was unsuccessful");
		return null;

	}

	/**
	 * Method:  movePlayer(String username, int uv, MoveDirection dir) 
	 * Purpose: It moves the player in the specified direction if the client issuing the command is the same one being used by the player.
	 */
	
	@Override
	public ServerResponse movePlayer(String username, int uv, MoveDirection dir) 
	{
		if (loggedOn.containsKey(username))
		{
			if (loggedOn.get(username).isVerified(uv))
			{
				if (mudmodel.moveEntity(loggedOn.get(username).getCharacter(), dir))
				{
					this.saveState();
					return new GameStateResponse(mudmodel, true, true);
				}
			}
		}
		return new GameStateResponse(null, false, false);
	}

	/**
	 * Method:  grabItem(String username, int uv, String item) 
	 * Purpose: It makes the player grab the specified item if the client issuing the command is the same one being used by the player.
	 */
	
	@Override
	public ServerResponse grabItem(String username, int uv, String item) {
		if (loggedOn.containsKey(username))
		{
			if (loggedOn.get(username).isVerified(uv))
			{
				if (mudmodel.grabItem(item, loggedOn.get(username).getCharacter()))
				{
					this.saveState();
					return new GameStateResponse(mudmodel, true, true);
				}
			}
		}
		return new GameStateResponse(null, false, false);
	}

	/**
	 * Method:  useItem(String username, int uv, String item) 
	 * Purpose: It makes the player use the specified item if the client issuing the command is the same one being used by the player.
	 */
	
	@Override
	public ServerResponse useItem(String username, int uv, String item) {
		if (loggedOn.containsKey(username))
		{
			if (loggedOn.get(username).isVerified(uv))
			{
				if (mudmodel.itemUsed(item, loggedOn.get(username).getCharacter()))
				{
					this.saveState();
					return new GameStateResponse(mudmodel, true, true);
				}
			}
		}
		return new GameStateResponse(null, false, false);
	}

	/**
	 * Method:  attackMob(String username, int uv, String entity) 
	 * Purpose: It makes the player attack the specified entity if the client issuing the command is the same one being used by the player.
	 */
	
	@Override
	public ServerResponse attackMob(String username, int uv, String entity) {
		if (loggedOn.containsKey(username))
		{
			if (loggedOn.get(username).isVerified(uv))
			{
				if (mudmodel.attackEntity(entity, loggedOn.get(username).getCharacter()))
				{
					this.saveState();
					return new GameStateResponse(mudmodel, true, true);
				}
			}
		}
		return new GameStateResponse(null, false, false);
	}

	/**
	 * Method:  interact(String username, int uv, String target) 
	 * Purpose: It makes the player interact with a door if the client issuing the command is the same one being used by the player.
	 */
	
	@Override
	public ServerResponse interact(String username, int uv, String target) {
		if (loggedOn.containsKey(username))
		{
			if (loggedOn.get(username).isVerified(uv))
			{
				if (mudmodel.interact(target, loggedOn.get(username).getCharacter()))
				{
					this.saveState();
					return new GameStateResponse(mudmodel, true, true);
				}
			}
		}
		return new GameStateResponse(null, false, false);
	}

	/**
	 * Method:  respawnPlayer(String username, int uv) 
	 * Purpose: It makes the player respawn at the start if the client issuing the command is the same one being used by the player.
	 */
	
	@Override
	public ServerResponse respawnPlayer(String username, int uv) {
		if (loggedOn.containsKey(username))
		{
			if (loggedOn.get(username).isVerified(uv))
			{
				if (mudmodel.respawnPlayer(loggedOn.get(username).getCharacter()))
				{
					this.saveState();
					return new GameStateResponse(mudmodel, true, true);
				}
			}
		}
		return new GameStateResponse(null, false, false);
	}
	
	/**
	 * Method:  dropItem(String username, int uv, String item) 
	 * Purpose: It makes the player drop the specified item if the client issuing the command is the same one being used by the player.
	 */
	
	@Override
	public ServerResponse dropItem(String username, int uv, String item) {
		if (loggedOn.containsKey(username))
		{
			if (loggedOn.get(username).isVerified(uv))
			{
				if (mudmodel.dropItem(item, loggedOn.get(username).getCharacter()))
				{
					this.saveState();
					return new GameStateResponse(mudmodel, true, true);
				}
			}
		}
		return new GameStateResponse(null, false, false);
	}

	/**
	 * Method:  giveItem(String username, int uv, String item, String target) 
	 * Purpose: It makes the player give the specified item to the target if the client issuing the command is the same one being used by the player.
	 */
	
	@Override
	public ServerResponse giveItem(String username, int uv, String item, String target) {
		if (loggedOn.containsKey(username))
		{
			if (loggedOn.get(username).isVerified(uv))
			{				
				
				if (username.toLowerCase().trim().equals(target.toLowerCase().trim())) {
					return new GameStateResponse(mudmodel, true, true);
				}
				
				Player givingPlayer = mudmodel.getPlayer(username);
				Player targetplayer = mudmodel.getPlayer(target);
				givingPlayer.setGiveTakeTarget(target);
				givingPlayer.setGiving(item);
				
				if (givingPlayer == null || targetplayer == null) {}
				else if(targetplayer.getGiveTakeTarget() == null 
						|| !givingPlayer.getGiving().equals(targetplayer.getTaking()))
				{
					targetplayer.updateChat(username + " wants to give you: " + item
					                        + "\nType \"take " + item + " " + username + "\" if you would like to allow it.", "SERVER");
				}
				else if (mudmodel.transferItem(loggedOn.get(username).getCharacter(), item, target))
				{
					givingPlayer.updateChat("Give transaction successful"
					                        , "SERVER");
					targetplayer.updateChat("Take transaction successful"
					                        , "SERVER");
					givingPlayer.setGiveTakeTarget(null);
					givingPlayer.setGiving(null);
					targetplayer.setGiveTakeTarget(null);
					targetplayer.setTaking(null);
					this.saveState();
					return new GameStateResponse(mudmodel, true, true);
				}
			}
		}
		return new GameStateResponse(null, false, false);
	}

	/**
	 * Method:  takeItem(String username, int uv, String item, String target) 
	 * Purpose: It makes the player take the specified item from the target if the client issuing the command is the same one being used by the player.
	 */
	
	@Override
	public ServerResponse takeItem(String username, int uv, String item, String target) {
		if (loggedOn.containsKey(username))
		{
			if (loggedOn.get(username).isVerified(uv))
			{
				
				if (username.toLowerCase().trim().equals(target.toLowerCase().trim())) {
					return new GameStateResponse(mudmodel, true, true);
				}
				
				Player takingPlayer = mudmodel.getPlayer(username);
				Player targetplayer = mudmodel.getPlayer(target);
				takingPlayer.setGiveTakeTarget(target);
				takingPlayer.setTaking(item);				
				
				if (takingPlayer == null || targetplayer == null) 
				{
				}
				else if(targetplayer.getGiveTakeTarget() == null 
						|| !takingPlayer.getTaking().equals(targetplayer.getGiving()))
				{
					targetplayer.updateChat(username + " wants you to give him: " + item
							+ "\nType \"give " + item + " " + username + "\" if you would like to allow it.", "SERVER");
				}
				else if (mudmodel.transferItem(targetplayer, item, username))
				{
					targetplayer.updateChat("Give transaction successful"
					                        , "SERVER");
					takingPlayer.updateChat("Take transaction successful"
					                        , "SERVER");
					takingPlayer.setGiveTakeTarget(null);
					takingPlayer.setTaking(null);
					targetplayer.setGiveTakeTarget(null);
					targetplayer.setGiving(null);
					this.saveState();
					return new GameStateResponse(mudmodel, true, true);
				}
				return new GameStateResponse(mudmodel, true, true);
			}
		}
		return new GameStateResponse(null, false, false);
	}

	/**
	 * Method:  playerChat(String username, int uv, String said, String target) 
	 * Purpose: It makes the player say the specified message to the target if the client issuing the command is the same one being used by the player.
	 */
	
	@Override
	public ServerResponse playerChat(String username, int uv, String said, String target)
	{
		if (loggedOn.containsKey(username))
		{
			if (loggedOn.get(username).isVerified(uv))
			{
				if (target == null)
					return globalChat(said, username);
				else
					return targetedChat(said, username, target);
			}
		}
		return new GameStateResponse(null, false, false);
	}
	
	/**
	 * Method:  targetedChat(String text, String sender, String recipient) 
	 * Purpose: It makes the player say the specified message to the target if the client issuing the command is the same one being used by the player.
	 */
	
	private ServerResponse targetedChat(String text, String sender, String recipient)
	{
		Player p1 = mudmodel.getPlayer(sender);
		Player p2 = mudmodel.getPlayer(recipient);

		if (sender.equals(recipient)) {
			p2 = null;
		}
		
		if (p1 != null)
			p1.updateChat(text, sender);
		if (p2 != null)
			p2.updateChat(text, sender);
		this.saveState();
		return new GameStateResponse(mudmodel, true, true);
	}
	
	/**
	 * Method:  globalChat(String text, String sender) 
	 * Purpose: It makes the player say the specified message to all players if the client issuing the command is the same one being used by the player.
	 */
	
	private ServerResponse globalChat(String text, String sender)
	{
		for (Player p : mudmodel.getPlayers())
		{
			p.updateChat(text, sender + " (OOC)");
		}
		this.saveState();
		return new GameStateResponse(mudmodel, true, true);
	}
	
	/**
	 * Method:  hashpassword(String password) 
	 * Purpose: It hashes the password using the specified algorithm.
	 */
	
	public static String hashpassword(String password)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance(HASH_ALG);
			md.update(password.getBytes());
			byte[] array = md.digest();
			StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < array.length; ++i)
	        	sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	        return sb.toString();
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Class:   MobListener
	 * Purpose: It updates the mobs based on a timer.
	 */
	
	private class MobListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			mudmodel.updateMobs();
		}
		
	}
}
