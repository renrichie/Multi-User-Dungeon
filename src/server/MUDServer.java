package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import model.Account;
import model.MudGame;
import server.command.BadCommand;
import server.command.Command;
import server.command.ShutDownCommand;
import server.model.ServerModel;
import server.model.ServerModelIntf;
import server.response.LogoffResponse;
import server.response.LogonResponse;
import server.response.ServerResponse;

/**
 * Author:   Brian Lovelace
 * File:     MUDServer.java
 * Purpose:  The MUDServer class creates a MUDServer object that functions as the server for MUD using the ServerModel interface.
 */

public class MUDServer extends Thread
{
	private static Scanner sc;
	private ServerModelIntf model;
	public static final int SERVER_PORT = 4001;

	private ServerSocket sock;
	private List<ObjectOutputStream> clients;
	private SessionListener listener;
	private volatile String password;

	
	public MUDServer(ServerModelIntf model, int port)
	{
		this(port);
		this.model = model;
	}
	
	private MUDServer(int port)
	{
		model = null;
		try
		{
			clients = Collections.synchronizedList(new ArrayList<>());
			sock = new ServerSocket(port);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
		
	/**
	 * Method:  run() 
	 * Purpose: It starts the server and sets up the data.
	 */
	
	@Override
	public void run()
	{		
		System.out.print("Enter a password for this session: ");
		String pw = sc.nextLine();
		this.listener = new SessionListener(ServerModel.hashpassword(pw));
		this.listener.start();
		
		while (true) 
		{
			Socket s;
			try
			{
				s = sock.accept();
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				synchronized (clients)
				{
					clients.add(oos);
				}
				ClientHandler c = new ClientHandler(ois, oos);
				c.start();
			} catch (IOException e)
			{
				System.out.println("Server is closing.");
				System.exit(1);
			}
		}
	}
	
	/**
	 * Method:  logOffAllClients() 
	 * Purpose: It logs off all active clients from the server.
	 */
	
	private synchronized void logOffAllClients()
	{	
		model.logOffAllClients();
	}
	
	/**
	 * Sends a ServerResponse to every connected Client, cleaning
	 * disconnected clients from the list as it goes.
	 * @param res ServerResponse to be sent globally.
	 */
	private synchronized void sendGlobalResponse(ServerResponse res)
	{
		for (int i = 0; i < clients.size(); i++)
		{
			ObjectOutputStream client = clients.get(i);
			try
			{
				client.reset();
				client.writeObject(res);
			}catch (IOException e)
			{
				clients.remove(client);
			}
		}
	}
	
	/**
	 * Class:   SessionListener
	 * Purpose: It listens for the start of the server.
	 */
	
	private class SessionListener extends Thread
	{
		
		
		public SessionListener(String pw)
		{
			password = pw;
		}
		
		/**
		 * Method:  run() 
		 * Purpose: It starts the server and sets up the password.
		 */
		
		@Override
		public void run()
		{
			System.out.println("Enter 'exit' followed the session password to terminate the server.");
			while (true)
			{
				if (sc.next().toLowerCase().trim().equals("exit"))
				{
					if (ServerModel.hashpassword(sc.next()).equals(password))
					{
						try
						{
							sock.close();
							break;
						} catch (IOException e)
						{
						}
					}
				}
			}
		}
	}
	
	/**
	 * Class:   ClientHandler
	 * Purpose: It manages the connections to the clients.
	 */
	
	private class ClientHandler extends Thread
	{
		ObjectInputStream input;
		ObjectOutputStream output;
		String username = null;
		int uv = Account.NO_VERIFY;
		
		public ClientHandler(ObjectInputStream ois, ObjectOutputStream oos)
		{
			this.input = ois;
			this.output = oos;
		}
		
		/**
		 * Method:  run() 
		 * Purpose: It reads and writes commands to the server.
		 */
		
		@Override
		public void run()
		{
			while (true) 
			{
				Command command;
				ServerResponse response;
				try 
				{
					Object o = input.readObject();
					if (o instanceof Command)
					{
						command = (Command)(o);
					}
					else
						command = new BadCommand(o);
					
					if (o instanceof ShutDownCommand)
					{
						ShutDownCommand com = (ShutDownCommand) (o);
						if (ServerModel.hashpassword(com.password).equals(password))
						{
							sock.close();
							logOffAllClients();
						}
					}
					
					response = command.executeCommand(model);
					
					if (response != null)
					{
						if (!response.GLOBAL)
						{
							output.reset();
							output.writeObject(response);
						}
						else
						{
							sendGlobalResponse(response);
						}
						
						if (response instanceof LogonResponse)
						{
							this.username = ((LogonResponse)response).username;
							this.uv = ((LogonResponse)response).USER_VERIFICATION;
						}
						else if (response instanceof LogoffResponse)
						{
							this.username = null;
							this.uv = Account.NO_VERIFY;
						}
						
						if (response instanceof LogonResponse 
								|| response instanceof LogoffResponse)
						{
							output.reset();
							sendGlobalResponse(model.saveState());
						}
					}
				} catch (IOException e) 
				{
					this.cleanUp();
					return;
				} catch (ClassNotFoundException e) 
				{
					this.cleanUp();
					e.printStackTrace();
					return;
				}
			}
			
		}
		
		/**
		 * Method:  cleanUp() 
		 * Purpose: It cleans up the connection upon disconnecting.
		 */
		
		private void cleanUp()
		{
			if (this.username != null)
			{
				model.clientLogoff(username, uv);
				sendGlobalResponse(model.saveState());
			}
			try
			{
				input.close();
				output.close();
			} catch (IOException e)
			{
				System.out.println("A client is being disconnected");
			}
			
		}
	}
	
	public static void main(String[] args)
	{
		MUDServer.sc = new Scanner(System.in);
		MudGame gamemodel;		
		FileInputStream fin;
		File f = new File("./serverstate/mudgame.ser");
		if (f.exists())
		{
			System.out.print("Previous game state detected.\nWould you like"
					+ " load this world (y/n): ");
			String ans = sc.nextLine();
			if (ans.equals("y"))
			{
				try
				{
					fin = new FileInputStream(f);
					ObjectInputStream ois = new ObjectInputStream(fin);
					gamemodel = (MudGame)(ois.readObject());
					ois.close();
					fin.close();
					System.out.println("The game was loaded successfully.");
		
				} catch (FileNotFoundException e)
				{
					System.out.println("The previous game state could not be loaded.");
					gamemodel = newGameSequence();
				} catch (IOException e)
				{
					System.out.println("The previous game state could not be loaded.");
					gamemodel = newGameSequence();
				} catch (ClassNotFoundException e)
				{
					System.out.println("The previous game state could not be loaded.");
					gamemodel = newGameSequence();
				}				
			}
			else 
				gamemodel = newGameSequence();
		}
		else
			gamemodel = newGameSequence();
		
		ServerModel serverModel = new ServerModel(gamemodel);
		MUDServer server = new MUDServer(serverModel, SERVER_PORT);
		server.start();
	}

	private static MudGame newGameSequence()
	{
		MudGame model;
		System.out.println("Creating new game!");
		model = new MudGame();
		//TODO Ask what type of game to make
		return model;
	}
}
