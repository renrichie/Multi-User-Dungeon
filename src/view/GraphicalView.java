/**
 *Author:	Chris Derck 
 *File:		GraphicalView
 *Purpose:	Acts as the view that is responsible for displaying the current state of the game to the player
 *			as well as operating as the location where the user can enter console commands and view past 
 *			commands given. There are 3 different areas of importance within this GUI. There are 2 textareas
 *			that represent the gameboard in text base view as well as the area where all the previous commands
 *			that the user has entered are displayed. The last component of importance is the textfield where
 *			the user can enter in their desired commands of the game. There are action listeners attached to
 *			this gui that will send appropriate commands depending on if the user types in a command or hits
 *			a specific key that acts as a movement shortcut. The board and consoleview will be updated every 
 *			time the server's game state is changed. 
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import client.model.ClientModel;
import client.model.ClientModelIntf;
import controller.ClientLogin;
import controller.MUDClient;
import model.MoveDirection;
import model.MudEntity;
import model.MudModelIntf;
import model.Player;
import model.Room;
import model.RulesAndCommands;
import model.item.ItemCollection;
import server.response.GameStateResponse;
import songplayer.EndOfSongEvent;
import songplayer.EndOfSongListener;
import songplayer.SongPlayer;

public class GraphicalView extends JPanel implements Observer
{
	private ClientModelIntf model;
	private MUDClient frame;
	private JTextArea console, stats, chat;
	private int width, height;
	private JTextField command;
	private ClientModel client;
	private String consoleview, convo;
	private MudModelIntf game;
	private JPanel panel, contentPane;
	private JScrollPane sc, sc2;
	private JLabel [][] grid;
	private int oldLen = 0,
				newLen = 0;
	private boolean hasDied = false, 
					musicStop = false;
	
	//Constructor that sets the components, listeners and images of the graphical view.
	public GraphicalView(MudModelIntf model, int width, int height, ClientModel client, MUDClient frame) {
		this.width = width;
		this.height = height;
		this.client = client;
		this.game = model;
		this.frame = frame;
		
		this.setLayout(null);
		
		consoleview = "";
		convo = "";
		constructView();
		registerListeners();
	}

	/**
	 *Method:	constructView()
	 *Purpose:	Method that is used to construct the view of the game to the user. It is set under a null
	 *			layout, therefore, each component (the gameboard textarea, the consoleview textarea, the chat textarea
	 *			and the console command jtextfield) are created and added at the appropriate portion of the jframe.
	 *			The consoleview that displays all of the accumulated commands of the user is contained within
	 *			a jscrollpane to allow the user to scroll through a possibly long list of previous commands. The chat 
	 *			textarea is also contained within a scrollpane to give the user the ability to scroll up and down to
	 *			view all of the messages that have been sent and received. 
	 */
	private void constructView() {
		Font textFont = new Font("Courier", Font.BOLD, 20);
		
		panel = new JPanel(new GridLayout(5,5));
		panel.setBounds(400, 40, 300, 300);
		panel.setBackground(Color.WHITE);
		
		
		//Setting up a 2D array of labels to hold the state of the gameboard. 
		grid = new JLabel[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				grid[j][i] = new JLabel();
				grid[j][i].setOpaque(true);
				grid[j][i].setBackground(Color.WHITE);
				grid[j][i].setPreferredSize(new Dimension(60, 60));
				panel.add(grid[j][i]);
			}
		}
		this.add(panel);

		
		//Command is the textfield where the user will enter their text base commands. 
		command = new JTextField("");
		command.setBounds(100, 478, 600, 50);	
		command.setFont(textFont);
		this.add(command);
		
		
		//Stats is the textarea that stores the statistics of the current user. 
		stats = new JTextArea("This is where game is going");
		stats.setBounds(80, 50, 279, 138);
		stats.setEditable(false);
		stats.setFont(textFont);
		stats.setForeground(Color.BLUE);
		this.add(stats);
		
		
		//Chat stores all of the sent and received messages from players in the game.
		chat = new JTextArea();
		chat.setBounds(80, 205, 279, 137);
		chat.setFont(textFont);
		chat.setBackground(Color.WHITE);
		chat.setLineWrap(true);
		chat.setEditable(false);
		
		
		//Scroll pane to contain the chat box.
		sc2 = new JScrollPane(chat);
		sc2.setBounds(80, 205, 279, 137);
		this.add(sc2);
		
			
		//Console is where all commands and game messages will be displayed for the user. 
		console = new JTextArea("This is where console commands and text display screen will be");
		console.setBounds(80, 353, 620, 113);
		console.setLineWrap(true);
		console.setEditable(false);
		console.setFont(textFont);
		console.setForeground(Color.RED);
		console.setText(consoleview);
		
		
		//Scroll pane to contain the console box.
		sc = new JScrollPane(console);
		sc.setBounds(80, 353, 620, 113);
		this.add(sc);
		
		//Mute button using two anonymous classes
		JButton mute = new JButton("Mute");
		mute.setFocusable(false);
		mute.setFont(new Font("Arial", Font.PLAIN, 12));
		mute.setLocation(10,490);
		mute.setSize(mute.getPreferredSize());
		mute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        if (!musicStop) {
		        	SongPlayer.stop();
		        	mute.setText("Play ");
		        }
		        else {
		        	SongPlayer.playFile(new EndOfSongListener() {
		        		@Override
		        		public void songFinishedPlaying(EndOfSongEvent eosEvent) {
		        			SongPlayer.playFile(this, "assets/Undertale.mp3");
		        		}
		        	}, "assets/Undertale.mp3");
		        	mute.setText("Mute");
		        }
	        	musicStop = !musicStop;
		    }
		});
		this.add(mute);
		
		//A label to hold the '>' char that won't be editable for entered commands.
		JLabel label = new JLabel(">");
		label.setFont(textFont);
		label.setBounds(80, 478, 20, 50);
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		label.setBorder(border);
		this.add(label);

		
		this.setBackground(Color.WHITE);
	}
	
	/**
	 *Method:	registerListeners()
	 *Purpose:	Method adds all the actionlisteners that are necessary for the game to run commands that 
	 *			the user desires. Places an actionlistener on the textfield so that it registers commands
	 *			that the user types into the textfield. 
	 */
	private void registerListeners() {
		command.addActionListener(new CommandListener());
	}
	
	/**
	 *Method:	CommandListener()
	 *Purpose:	Method that takes all the text that the user enters within the jtextfield of the view and 
	 *			deciphers the command that the user desires the game to make. The textfield is parsed and
	 *			goes through a series of if/else statements that, depending on what the text is, will send
	 *			a series of commands to the clientmodel class so that the game can attempt to perform said
	 *			commands. 
	 */
	private class CommandListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			//Create a word array from the entered text so that string can be parsed to decipher the command.
			String text = command.getText(); //reads text from user and makes it lower case
			String[] words = text.split(" ");
			String word1 = words[0].toLowerCase();
			
			command.setText("");	
			
			//If that will be used for any commands that are more than 1 word.
			if (words.length > 1) {
				String word2 = words[1];
				
				if (word1.equals("give")) { 
					if (word2.equals(null)) {
						consoleview += "\n" + ">Illegal command"; 
						console.setText(consoleview);
					}
					else if (words.length > 2)
					{
						word2 = word2.toLowerCase();
						String word3 = words[2].toLowerCase();
						client.giveItem(word2, word3);
						consoleview += "\n" + ">" + text;
					}
				}
				//If to check for a valid shutdown command
				if (word1.equals("shutdown"))
				{
					if (word2 == null)
					{
						consoleview += "\n" + ">Illegal command";
						console.setText(consoleview);
					}
					else 
					{
						client.shutdown(word2);
						consoleview += "\n" + ">" + text;
					}
				}
				
				//Else if to perform a take command that will try and grab an item from board or from
				//another player.
				else if (word1.equals("take")) { 
					if (word2.equals(null)) {
						consoleview += "\n" + ">Illegal command"; 
						console.setText(consoleview);
					}
					else if (words.length > 2)
					{
						word2 = word2.toLowerCase();
						String word3 = words[2].toLowerCase();
						client.takeItem(word2, word3);
						consoleview += "\n" + ">" + text;
					}
					else { 
						word2 = word2.toLowerCase();
						client.grabItem(word2);
						consoleview += "\n" + ">" + text;
					} 
				}
				
				//Else if to perform a use command that will try and use an item that is within the current
				//users inventory.
				else if (word1.equals("use")) {
					if (word2.equals(null)) {
						consoleview += "\n" + ">Illegal command";
						console.setText(consoleview);
					}
					else {
						word2 = word2.toLowerCase();
						client.useItem(word2);
						consoleview += "\n" + ">" + text;
					}
				}
				
				//Else if to perform drop command to drop and item from user's inventory. 
				else if (word1.equals("drop")) {
					if (word2.equals(null)) {
						consoleview += "\n" + ">Illegal command";
						console.setText(consoleview);
					}
					else {
						word2 = word2.toLowerCase();
						client.dropItem(word2);
						consoleview += "\n" + ">" + text;
					}
				}
				
				//Else if checks for the different variations of the look command. 
				else if (word1.equals("look")) { 
					if (word2.equals(null)) {
						consoleview += "\n" + ">Illegal command"; 
						console.setText(consoleview);
					}
					else if (ItemCollection.isAnItem(word2)) {
						word2 = word2.toLowerCase();
						String description = client.lookItem(word2);
						consoleview += "\n" + description;
						console.setText(consoleview);
					}
					else {
						word2 = word2.toLowerCase();
						String description = client.lookEntity(word2);
						consoleview += "\n" + description;
						console.setText(consoleview);
					}
					
				}
				
				//Else if to perform attack command.
				else if (word1.equals("attack")) {
					if (word2.equals(null)) {
						consoleview += "\n" + ">Illegal command"; 
						console.setText(consoleview);
					}
					else {
						word2 = word2.toLowerCase();
						client.attackEntity(word2);
						consoleview += "\n" + ">" + text;
					}
					
					
				}
				
				//Else if to perform interact command to try and open a door on the map. 
				else if (word1.equals("interact")) {
					if (word2.equals(null)) {
						consoleview += "\n" + ">Illegal command"; 
						console.setText(consoleview);
					}
					else {
						word2 = word2.toLowerCase();
						client.interact(word2);
						consoleview += "\n" + ">" + text;
						console.setText(consoleview);
					}
				}
				
				//Else if to send a message to all players in current room.
				else if (word1.equals("say")) {
					String s = "";

					for (int i = 1; i < words.length; i++) {
						s += words[i] + " ";
					}
					ArrayList<MudEntity> entities = (ArrayList<MudEntity>) client.getRoom().getEntities();

					int numPlayers = 0;
					for (MudEntity m : entities) {
						if (m instanceof Player) {
							numPlayers++;
						}
					}
					
					for (MudEntity m : entities) {
						if (m instanceof Player) {
							Player p = (Player) m;
							if (numPlayers == 1) {
								client.say(s, p.getIGN());
							}
							else {
								if (!p.getIGN().equals(client.getPlayer().getIGN())) {
									client.say(s, p.getIGN());
								}
							}
						}
					}

					consoleview += "\n" + ">" + word1 + " " + s;
				}
				
				//Else if to send a message to all players in the current game. 
				else if (word1.equals("ooc")) {
					String s = "";
					
					for (int i = 1; i < words.length; i++) {
						s += words[i] + " ";
					}
					
					client.say(s, null);
					consoleview += "\n" + ">" + word1 + " " + s;
				}
				
				//Else if to tell a message to a specific player in the current game. 
				else if (word1.equals("tell")) {
					/*for now this has to be a player name*/
					String entity = word2;
					
					String s = "";
					
					for (int i = 2; i < words.length; i++) {
						s += words[i] + " ";
					}
					client.say(s, entity);
					consoleview += "\n" + ">" + word1 + " " + s;
				}
			
			}
			
			//Series of else ifs that are used to find out a direction that the user wants player to go in. 
			else if (word1.equals("north")) {
				client.move(MoveDirection.NORTH);
				consoleview += "\n" + ">" + text;
			} 
			else if (word1.equals("south")) {
				client.move(MoveDirection.SOUTH);
				consoleview += "\n" + ">" + text;
			} 
			else if (word1.equals("east")) {
				client.move(MoveDirection.EAST);
				consoleview += "\n" + ">" + text;
			} 
			else if (word1.equals("west")) {
				client.move(MoveDirection.WEST);
				consoleview += "\n" + ">" + text;
			}
			
			//Else if to look at current items, mobs and players within the current room. 
			else if (word1.equals("look")) {
				//this is where we will do the look command that will display everything within the room
				String description = client.look();
				consoleview += "\n" + description;
				console.setText(consoleview);
			}
			
			//Else if to look at user's inventory.
			else if (word1.equals("inventory")) { 
				String description = client.lookInventory();
				consoleview += "\n" + description;
				console.setText(consoleview);
			}
			
			//Else if for player to look up at ceiling of current room. 
			else if (word1.equals("up")) {
				String description = client.lookUp();
				consoleview += "\n" + description;
				console.setText(consoleview);
			}
			
			//Else if for player to look down at floor of current room. 
			else if (word1.equals("down")) {
				String description = client.lookDown();
				consoleview += "\n" + description;
				console.setText(consoleview);
			}
			
			//Else if for player to quit their current game. 
			else if (word1.equals("quit")) {
				client.logout();
				SongPlayer.stop();
				frame.dispose();
				ClientLogin log = new ClientLogin();
				log.setVisible(true);
				
			}
			
			//Else if for player to see the rules and commands of the game in the console. 
			else if (word1.equals("help")) {
				RulesAndCommands help = new RulesAndCommands();
				
				consoleview += "\n" + help.getInfo();
				console.setText(consoleview);
			}
			
			//Else if for player to see their current statistics in the console. 
			else if (word1.equals("score")) {
				consoleview += "\n" + client.getPlayer().getDescription();
				console.setText(consoleview);
			}
			
			//Else if for player to see what players are currently active on the game board. 
			else if (word1.equals("who")) {
				consoleview += "\n" + client.getPlayers();
				console.setText(consoleview);
			}
		}
		
	}
	
	
	/**
	 *Method:	update()
	 *Purpose:	Method that is called when any changes are made to the server's model. This view currently
	 *			acts as an observer to the clientmodel, the observable. Once a change is made to the model,
	 *			it will update the view of the gameboard as well as set the new list of past console commands
	 *			the user has entered. 
	 */
	@Override
	public void update(Observable arg0, Object arg1)
	{
		
		//If checks to see if the client is currently connected to the server. If the connection is not active, a 
		//joptionpane with a relevant message informs user that game is shutting down. 
		if (!client.sessionActive())
		{
			console.setText("The server has shutdown");
			System.out.println("Client detected server shutdown");
			JOptionPane.showMessageDialog(null, "Server is shutting down. Client is now closing!");
			System.exit(0);
		}
			
		
		
		Font textFont = new Font("Courier New", Font.BOLD, 40);
		
		Player p = client.getPlayer();
		
		//If that checks to see if the player has died. If they have, a joptionpane will give user the option to start 
		//over with fresh stats or to quit the game. 
		if (p != null) {
			stats.setText(p.getDescription());
			if (p.getCurrentHealth() == 0 && !hasDied) {
				hasDied = true;
				int i = JOptionPane.showConfirmDialog(null, "You have died!\nStart over with default stats and no inventory?");
				
				if (i == 0) {
					client.respawnPlayer();
				}
				else {
					frame.dispose();
					ClientLogin log = new ClientLogin();
					log.setVisible(true);
				}
				
			}
		}
		
		ArrayList<Vector<String>> newChat = client.getNewChat();
		
		//Checks the chat that has been sent and received for the user and sets the chat inside chat box. 
		if (newChat != null)
			for (Vector<String> msg : newChat)
			{
				convo += msg.get(0) + ": " + msg.get(1) + "\n";
			}
		chat.setText(convo);
			
		console.setText(consoleview);
		Room playerRoom = client.getRoom();
		
		//If that sets the characters that are on the tiles with the user's current room. 
		if (playerRoom != null) {
			char [][] tiles = playerRoom.getBoard();
			
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					String t = "" + tiles[j][i];
					grid[j][i].setText(t);
					grid[j][i].setFont(textFont);
				}
			}
			
		}
		
		//This bit of code will determine if a player has entered or left the room the user is in
		if (playerRoom != null) {
		
			int val = client.roomCheck();

			if (val == 2) {
				consoleview += "\n" + "Another player has entered the room you are currently in!";
				console.setText(consoleview);
			} else if (val == 1) {
				consoleview += "\n" + "A player has left the room you are currently in!";
				console.setText(consoleview);
			}
		}
		newLen = console.getDocument().getLength();
		if (!(arg1 instanceof GameStateResponse) || (newLen != oldLen))
		{
				console.setCaretPosition(console.getDocument().getLength());
		}
		oldLen = newLen;
		chat.setCaretPosition(chat.getDocument().getLength());
	}
}
