package server.response;

import java.io.Serializable;

import model.Player;
import server.command.Command;

/**
 * ServerResponse Objects are returned by the Server
 * in response to a Command.
 * @author Brian Lovelace
 *
 */
public abstract class ServerResponse implements Serializable
{
	public final String MESSAGE;
	public final boolean GLOBAL;
	public Command command;
	public Player player;
	
	public ServerResponse(Command com) 
	{
		this(com, "", false);
	}
	
	public ServerResponse (Command com, String msg)
	{
		this(com, msg, false);
	}
	
	public ServerResponse(String msg, boolean global)
	{
		this(null, msg, global);
	}
	
	public ServerResponse (Command com, String msg, boolean global)
	{
		this.command = com;
		this.MESSAGE = msg;
		this.GLOBAL = global;
	}
	
	public ServerResponse (Player p, boolean global, String user)
	{
		this.player = p;
		this.GLOBAL = global;
		this.MESSAGE = user;
	}
	
	/**
	 * Method:  toString() 
	 * Purpose: It returns the String indicating the result of the command.
	 */
	
	@Override
	public String toString()
	{
		return MESSAGE;
	}
}
