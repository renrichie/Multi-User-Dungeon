package server.command;

import server.model.ServerModelIntf;
import server.response.ServerResponse;
import server.response.TestGlobalResponse;

/**
 * Author:   Brian Lovelace
 * File:     TestGlobalCommand.java
 * Purpose:  The TestGlobalCommand object is used to test out global commands for the server.
 */

public class TestGlobalCommand extends Command
{
	public TestGlobalCommand()
	{
		super("GlobalTest");
	}
	
	@Override
	public ServerResponse executeCommand(ServerModelIntf model)
	{
		return new TestGlobalResponse(this);
	}
	
}
