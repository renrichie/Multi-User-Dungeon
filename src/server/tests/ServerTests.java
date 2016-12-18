package server.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import server.command.CreateAccountCommand;
import server.command.LogoffCommand;
import server.command.LogonCommand;
import server.model.ServerModel;
import server.response.AccountCreationResponse;
import server.response.LogoffResponse;
import server.response.LogonResponse;

/**
 * Author:   Brian Lovelace
 * File:     ServerTests.java
 * Purpose:  The ServerTests class tests out the commands and responses with the server.
 */

public class ServerTests
{
	/*
	 * Saving is not yet implemented, so the tests are not run.
	 * The ServerModel does not yet use a MudModel to maintain
	 * a game state.
	 */
	
	@Test
	public void testAddAccount()
	{
		System.out.println("TestAddAccount!");
		
		ServerModel model = new ServerModel(null);
		
		CreateAccountCommand com = new CreateAccountCommand("Test", "lalala");
		AccountCreationResponse  res = (AccountCreationResponse) com.executeCommand(model);
		System.out.println(res);
		assertTrue(res.success);
		res = (AccountCreationResponse) com.executeCommand(model);
		assertFalse(res.success);
		com = new CreateAccountCommand("NewUser", "1234");
		res = (AccountCreationResponse) com.executeCommand(model);
		assertTrue(res.success);
	}
	
	@Test
	public void testLogon()
	{
		ServerModel model = new ServerModel(null);
		
		CreateAccountCommand com = new CreateAccountCommand("Test", "lalala");
		AccountCreationResponse  res = (AccountCreationResponse) com.executeCommand(model);
		LogonCommand logoncom = new LogonCommand("Test", "lalala");
		LogonResponse res1 = (LogonResponse) logoncom.executeCommand(model);
		assertFalse(res1.success);
		
		LogoffCommand logoffcom = new LogoffCommand("Test", res.USER_VERIFICATION);
		LogoffResponse logoffres = (LogoffResponse) logoffcom.executeCommand(model);
		assertTrue(logoffres.logOffSuccess);
		
		res1 = (LogonResponse) logoncom.executeCommand(model);
		assertTrue(res1.success);
		res1 = (LogonResponse) logoncom.executeCommand(model);
		assertFalse(res1.success);
		
		CreateAccountCommand com2 = new CreateAccountCommand("Tester2", "lalalalla");
		AccountCreationResponse  newacc2 = (AccountCreationResponse) com2.executeCommand(model);
		assertTrue(newacc2.success);
		
		//Can log off and on while another account is logged on
		logoffres = (LogoffResponse) logoffcom.executeCommand(model);
		assertTrue(logoffres.logOffSuccess);
		
		//Makes sure the other account didn't log off with you
		LogonCommand acc2logoncom = new LogonCommand("Tester2", "lalalalla");
		LogonResponse accres2 = (LogonResponse) acc2logoncom.executeCommand(model);
		assertFalse(accres2.success);
		
		LogonCommand badpw = new LogonCommand("Test", "badpw"); 
		LogonResponse badres = (LogonResponse) badpw.executeCommand(model);
		assertFalse(badres.success);
		
		
		
	}
	
	@Test
	public void testLogoff()
	{
		ServerModel model = new ServerModel(null);
		
		CreateAccountCommand com = new CreateAccountCommand("Test", "lalala");
		AccountCreationResponse  res = (AccountCreationResponse) com.executeCommand(model);
		
		LogoffCommand logoffcom = new LogoffCommand("Test", -2);
		LogoffResponse logoffres = (LogoffResponse) logoffcom.executeCommand(model);
		assertFalse(logoffres.logOffSuccess);
		
		logoffcom = new LogoffCommand("Test", res.USER_VERIFICATION);
		logoffres = (LogoffResponse) logoffcom.executeCommand(model);
		assertTrue(logoffres.logOffSuccess);
		
		logoffres = (LogoffResponse) logoffcom.executeCommand(model);
		assertFalse(logoffres.logOffSuccess);
		
		LogonCommand logoncom = new LogonCommand("Test", "lalala");
		LogonResponse logonres = (LogonResponse) logoncom.executeCommand(model);
		assertTrue(logoffres.logOffSuccess);
		logoffres = (LogoffResponse) logoffcom.executeCommand(model);
		assertFalse(logoffres.logOffSuccess);
		
		logoffcom = new LogoffCommand("Test", logonres.USER_VERIFICATION);
		
		logoffres = (LogoffResponse) logoffcom.executeCommand(model);
		assertTrue(logoffres.logOffSuccess);
		
		LogoffCommand noone = new LogoffCommand("NoUser", 5);
		LogoffResponse nooneres = (LogoffResponse) noone.executeCommand(model);
		assertFalse(nooneres.logOffSuccess);
	}
	
}
