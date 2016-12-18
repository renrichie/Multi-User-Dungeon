package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import server.command.CreateAccountCommand;
import server.command.LogoffCommand;
import server.command.LogonCommand;
import server.command.TestGlobalCommand;
import server.response.LogonResponse;
import server.response.ServerResponse;

/**
 * Author:   Brian Lovelace
 * File:     ServerTest.java
 * Purpose:  The ServerTest class is used to test if the server is actually receiving and responding to commands.
 */

public class ServerTest
{
	Socket server;
	ObjectOutputStream toServer; 
	ObjectInputStream fromServer;
	
	public ServerTest() throws UnknownHostException, IOException
	{
		Socket server = new Socket("localhost", 4001);
		toServer = new ObjectOutputStream(server.getOutputStream());
		fromServer = new ObjectInputStream(server.getInputStream());
		ServerListener listener = new ServerListener();
		listener.start();
	}
	
	
	public static void main(String[] args)
	{
		
		try
		{
			ServerTest s = new ServerTest();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private class ServerListener extends Thread
	{
				
		@Override
		public void run()
		{
			System.out.println("EXPECTED : RESULT");
			int uv = -1;
			int uv2 = -1;
			try
			{	
				//Should return with a BadCommandResponse
				System.out.print("BadCommand: ");
				toServer.writeObject("bad");
				ServerResponse res0 = (ServerResponse)(fromServer.readObject());
				System.out.println(res0);
				
				//should send to everyone
				System.out.print("Send Globally: ");
				toServer.writeObject(new TestGlobalCommand());
				ServerResponse res1 = (ServerResponse)(fromServer.readObject());
				System.out.println(res1);
				
				//Log Brian in
				System.out.print("New account success: ");
				toServer.writeObject(new CreateAccountCommand("Brian", "1234"));
				LogonResponse res2 = (LogonResponse)(fromServer.readObject());
				System.out.println(res2);
				uv = res2.USER_VERIFICATION;
				
				//Fail to create a new Account
				System.out.print("Fail on new account: ");
				toServer.writeObject(new CreateAccountCommand("Brian", "1234"));
				LogonResponse acc = (LogonResponse)(fromServer.readObject());
				System.out.println(acc);

				
				//Fail to log Brian in again
				System.out.print("Fail login: ");
				toServer.writeObject(new LogonCommand("Brian", "1234"));
				ServerResponse res3 = (ServerResponse)(fromServer.readObject());
				System.out.println(res3);
				
				//Log Brian off
				System.out.print("Brian logoff success: ");
				toServer.writeObject(new LogoffCommand("Brian", uv));
				ServerResponse res4 = (ServerResponse)(fromServer.readObject());
				System.out.println(res4);
				
				//Log Brian in with new u-v
				System.out.print("Login success w/ new uv: ");
				toServer.writeObject(new LogonCommand("Brian", "1234"));
				LogonResponse res5 = (LogonResponse)(fromServer.readObject());
				uv2 = res5.USER_VERIFICATION;
				System.out.println(res5);
				
				//Fail to log Brian off
				System.out.print("Fail logoff w/ old uv: ");
				toServer.writeObject(new LogoffCommand("Brian", uv));
				ServerResponse res6 = (ServerResponse)(fromServer.readObject());
				System.out.println(res6);
				
				//Successfully log Brian off
				System.out.print("Success logoff w/ new uv: ");
				toServer.writeObject(new LogoffCommand("Brian", uv2));
				ServerResponse res7 = (ServerResponse)(fromServer.readObject());
				System.out.println(res7);
				
				//Fail to log Brian off
				System.out.print("Fail recursive logoff: ");
				toServer.writeObject(new LogoffCommand("Brian", -1));
				ServerResponse res8 = (ServerResponse)(fromServer.readObject());
				System.out.println(res8);
				
				//Fail to log in
				System.out.print("No login wrong pw: ");
				toServer.writeObject(new LogonCommand("Brian", "wrongpw"));
				ServerResponse res9 = (ServerResponse)(fromServer.readObject());
				System.out.println(res9);
				
				//Log Brian back in with a new u-v
				System.out.print("Successful login: ");
				toServer.writeObject(new LogonCommand("Brian", "1234"));
				LogonResponse res10 = (LogonResponse)(fromServer.readObject());
				uv = res10.USER_VERIFICATION;
				System.out.println(res10);
				

				while (true)
				{
					System.out.print("NEW GLOBAL: ");
					ServerResponse res = (ServerResponse)(fromServer.readObject());
					System.out.println(res);
				}				
			}
			catch (IOException e)
			{
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
		}	
	}

}
