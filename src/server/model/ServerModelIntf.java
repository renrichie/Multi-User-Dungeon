package server.model;

import model.MoveDirection;
import server.response.ServerResponse;

/**
 * An interface used by Command objects to interface with the
 * server's model.
 * @author Brian Lovelace
 *
 */
public interface ServerModelIntf
{
	/**
	 * Logs a client on as a player if the username and
	 * password are correct
	 * @param usernane Username of the client
	 * @param password Password associated with that username
	 * @return A ServerResponse indicating the result of the operation
	 */
	public ServerResponse clientLogon(String username, String password);
	
	/**
	 * Logs off the client.
	 * @param username Username of the client
	 * @return A ServerResponse indicating the result of the operation
	 */
	public ServerResponse clientLogoff(String username, int uv);
	
	/**
	 * Adds a new account to the Server if an account with that username
	 * does not already exist. That user is then logged onto the
	 * server.
	 * @param username Username of the new account
	 * @param password Password to be associated with that username
	 * @return A ServerResponse indicating the result of the operation
	 */
	public ServerResponse addAccount(String username, String password);
	
	/**
	 * Saves the entire game state of the server.
	 * @return A ServerResponse indicating the result of the operation
	 */
	public ServerResponse saveState();
	
	/**
	 * Saves the state of the player.
	 * @param username Username associated with the player to be saved.
	 * @return A ServerResponse indicating the result of the operation
	 */
	public ServerResponse savePlayerState(String username, int uv);

	public ServerResponse movePlayer(String username, int uv, MoveDirection dir);
	
	public ServerResponse grabItem(String username, int uv, String item);
	
	public ServerResponse useItem(String username, int uv, String item);
	
	public ServerResponse attackMob(String username, int uv, String mob);
	
	public ServerResponse interact(String username, int uv, String target);
	
	public ServerResponse respawnPlayer(String username, int uv);
	
	public ServerResponse dropItem(String username, int uv, String item);
	
	public ServerResponse giveItem(String username, int uv, String item, String target);
	
	public ServerResponse takeItem(String username, int uv, String item, String target);

	public ServerResponse playerChat(String username, int uv, String said, String target);
	
	public void logOffAllClients();
}
