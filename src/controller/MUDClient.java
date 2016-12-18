/**
 *Author:	Chris Derck 
 *File:		MUDClient
 *Purpose:	Acts as the JFrame that is launced when a user has either successfully logged into an existing account or created
 *			a new account. The frame initializes a jtabbedpane with 2 different views that will be used while the user plays 
 *			the game. One view, textview, will be used to show the rules and commands of the game. The other view, graphicalview,
 *			will display the gake to the user. The game will always start with the rules and commands information being displayed
 *			to the user. 
 */

package controller;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import client.model.ClientModel;
import model.MudModelIntf;
import model.RulesAndCommands;
import view.GraphicalView;
import view.TextView;

public class MUDClient extends JFrame
{
	private MudModelIntf model;
	private RulesAndCommands info;
	public static final int width = 800;
	public static final int height = 600;
	private GraphicalView graphicalView; // First observer initialized
	private TextView commandInfo; 
	private ClientModel client;
	
	
	//Constructor to initialize the JFrame with a passed in representation of the game and the client model. 
	public MUDClient(MudModelIntf model, ClientModel client)
	{
		this.model = model;
		this.info = new RulesAndCommands();
		this.client = client;
		
		
		setupGUI();
		this.client.addObserver(graphicalView);
	}
	
	/**
	 *Method:	setupGUI()
	 *Purpose:	Method that is used to set up the components of the JFrame. A jtabbed pane with the 2 possible views, textview
	 *			and graphicalview, as well as the title of the game are all set.  
	 */
	private void setupGUI()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(width, height);
		this.setLocation(100, 40);
		this.setTitle("Multi User Dungeon");
		this.setResizable(false);
		this.setBackground(Color.WHITE);
		
		graphicalView = new GraphicalView(model, width, height, client, this);
		commandInfo = new TextView(info, width, height);
		
		// Creating a Tabbed Pane to hold the 2 views
		JTabbedPane pane = new JTabbedPane();
		pane.add("Command Info", commandInfo);
		pane.add("Graphical View", graphicalView);
		this.add(pane);
		
	}

	
}
