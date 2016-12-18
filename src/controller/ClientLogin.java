/**
 *Author:	Chris Derck 
 *File:		ClientLogin
 *Purpose:	Acts as the initial GUI that is launched when the client opens up the MUD application. It will
 *			prompt the user to login with their account. If the client does not currently have an account 
 *			in the system, they can create an account with their desired info. When the user logs into a 
 *			valid account, the MUDClient GUi will be activated with that user's account and will then display
 *			the game GUI. 
 */

package controller;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.model.ClientModel;
import model.MudModelIntf;
import server.response.AccountCreationResponse;
import server.response.LogonResponse;
import songplayer.EndOfSongEvent;
import songplayer.EndOfSongListener;
import songplayer.SongPlayer;

public class ClientLogin extends JFrame implements Observer {
	private JTextField userName, createName;
	private JPasswordField pwd, createPwd, createPwd2;
	private ClientModel client;
	private JPanel panel, panel2;
	private JButton log, createA, cancel, create;
	private MudModelIntf model;
	private ObjectWaitingForSongToEnd waiter;
	private boolean songPlaying;
	private final String address = "localhost";
	private final int port = 4001;

	public static void main(String[] args) {
		ClientLogin log = new ClientLogin();
		log.setVisible(true);
	}

	/**
	 * Method: ClientLogin() Purpose: Constructor that initializes the size,
	 * location and title of GUI. Calls for the view to be initialized.
	 */
	public ClientLogin() {
		
		try {
			client = new ClientModel(address, port);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		client.addObserver(this);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 400);
		this.setLayout(null);
		this.setTitle("MUD Login");
		this.setBackground(Color.WHITE);

		initializeView();
	}

	/**
	 *Method:	initializeView()
	 *Purpose:	Method that is used to set up the components of the login GUI. 2 differen't views are created for GUI. The 
	 *			GUI will start out with the login view that will prompt the user to sign in on a valid account. If the user
	 *			chooses to create an account, a second view will be shown on the GUI to allow the user to create a new 
	 *			account.  
	 */
	private void initializeView() {

		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));

		JLabel label1 = new JLabel("Username");
		JLabel label2 = new JLabel("Password");
		userName = new JTextField();
		pwd = new JPasswordField();

		log = new JButton("Login");
		log.addActionListener(new buttonListener());

		createA = new JButton("Create Account");
		createA.addActionListener(new buttonListener());

		// The first panel acts to store the login view of the GUI and is the
		// first to be displayed
		// when the GUI is opened.
		panel.add(label1);
		panel.add(userName);
		panel.add(label2);
		panel.add(pwd);
		panel.add(createA);
		panel.add(log);

		panel.setSize(300, 150);
		panel.setLocation(40, 100);
		panel.setBackground(Color.white);
		this.add(panel);

		// This is a hidden panel that will be used when the Client wants to
		// create a new account
		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(4, 2));

		JLabel label3 = new JLabel("Choose Username");
		JLabel label4 = new JLabel("Enter Password");
		JLabel label5 = new JLabel("Re-enter Password");
		createName = new JTextField();
		createPwd = new JPasswordField();
		createPwd2 = new JPasswordField();

		cancel = new JButton("Cancel");
		cancel.addActionListener(new buttonListener());

		create = new JButton("Create");
		create.addActionListener(new buttonListener());

		panel2.add(label3);
		panel2.add(createName);
		panel2.add(label4);
		panel2.add(createPwd);
		panel2.add(label5);
		panel2.add(createPwd2);
		panel2.add(create);
		panel2.add(cancel);

		panel2.setSize(300, 200);
		panel2.setLocation(40, 75);
		panel2.setBackground(Color.white);

		waiter = new ObjectWaitingForSongToEnd();
		songPlaying = false;
	}

	/**
	 * Class: 	buttonListener() 
	 * Purpose: A private class that acts to listen for whenever any buttons are selected 
	 * 			within the GUI. Depending on what button is selected, specific actions will 
	 * 			be performed.
	 */
	private class buttonListener implements ActionListener {

		/**
		 * Method:	 actionPerformed() 
		 * Purpose:  Listens for when a button is selected on the GUI and depending on which 
		 *			 button was selected, a specific action will be performed. These actions can vary from
		 * 			 switching between the login view and the create account view, to logging into the
		 * 			 game and then launching a new GUI with the game and user's account.
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String name, pass = "", pass2 = "";
			char[] passwordAsChars, passwordAsChars2;
			String text = ((JButton) arg0.getSource()).getText();

			// If login button is selected, it checks to make sure the passed in
			// information is valid or not. If it is, it will set the current
			// user to the passed in
			// credentials.
			if (text.equals("Login")) {

				name = userName.getText();
				passwordAsChars = pwd.getPassword();

				for (char character : passwordAsChars) {
					pass += character;
				}

				// If else statements to check for errors while logging in.
				if (name.equals("") && !pass.equals("")) {
					JOptionPane.showMessageDialog(null, "Please provide a name");
					return;
				} else if (!name.equals("") && pass.equals("")) {
					JOptionPane.showMessageDialog(null, "Please provide a password");
					return;
				} else if (name.equals("") && pass.equals("")) {
					JOptionPane.showMessageDialog(null, "Missing name/password");
					return;
				}

				userName.setText("");
				pwd.setText("");

				// If no errors during the login, it will check to verify
				// whether the account
				// exists within the AccountCollection.
				client.login(name, pass);

			}

			// At this else we will want to create a new account for the user.
			else if (text.equals("Create Account")) {
				createAccount();
			}

			else if (text.equals("Cancel")) {
				switchView();
			}

			// This will be triggered when the user has entered in their desired
			// credentials for their account
			else {
				name = createName.getText();
				passwordAsChars = createPwd.getPassword();
				passwordAsChars2 = createPwd2.getPassword();

				for (char character : passwordAsChars) {
					pass += character;
				}

				for (char character : passwordAsChars2) {
					pass2 += character;
				}

				createName.setText("");
				createPwd.setText("");
				createPwd2.setText("");

				// If else statements that check for errors during the account
				// creation process.
				if (name.equals("") && !pass.equals("")) {
					JOptionPane.showMessageDialog(null, "Please provide a name");
					return;
				} else if (!name.equals("") && pass.equals("")) {
					JOptionPane.showMessageDialog(null, "Please provide a password");
					return;
				} else if (name.equals("") && pass.equals("")) {
					JOptionPane.showMessageDialog(null, "Missing name/password");
					return;
				} else if (!pass.equals(pass2)) {
					JOptionPane.showMessageDialog(null, "Passwords did not match");
					return;
				}

				// If no errors in creation, it will check to see if the user
				// has already been created. If not,
				// a new account with the desired name/password will be created.
				client.addAccount(name, pass);
			}

		}

	}

	/**
	 * Method: 	createAccount() 
	 * Purpose: When the user selects the Create Account button, this method will be called and will 
	 * 			remove the panel that holds the login and replace it with a panel that holds the fields 
	 * 			necessary for the client to create a new account.
	 */
	private void createAccount() {
		userName.setText("");
		pwd.setText("");

		this.remove(panel);
		this.add(panel2);
		repaint();
		validate();
	}

	/**
	 * Method: 	switchView() 
	 * Purpose: Method is called when the client has created a new account with proper credentials. 
	 * 			The create account panel is removed and the login panel is added back so the client can login.
	 */
	private void switchView() {
		userName.setText("");
		pwd.setText("");

		this.remove(panel2);
		this.add(panel);
		repaint();
		validate();
	}

	/**
	 *Method:	update(Observable o, Object response)
	 *Purpose:	Method that is used to determine if a successful login or account creation is triggered. If one was,
	 *			then the game music will start playing for the user while they play the game.   
	 */
	@Override
	public void update(Observable o, Object response) {
		
		if (response instanceof AccountCreationResponse)
		{
			AccountCreationResponse res = (AccountCreationResponse) response;
			
			// If else that checks to see if the account was successfully created or not.
			// If the account was not created, an error message will be presented to the user. 
			if (res.success) {
				dispose();
				MUDClient clientGUI = new MUDClient(model, client);
				clientGUI.setVisible(true);
				if (!songPlaying) {
					SongPlayer.playFile(waiter, "assets/Undertale.mp3");
					songPlaying = true;
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "That account already exists. Please try again");
				return;
			}
				
		}
		else if (response instanceof LogonResponse)
		{
			LogonResponse res = (LogonResponse) response;
			
			// If else to see if the account was valid or not. If valid, the
			// login GUI will close and
			// it will open up the game GUI with the logged in account.
			if (res.success)
			{
				dispose();
				MUDClient clientGUI = new MUDClient(model, client);
				clientGUI.setVisible(true);
				if (!songPlaying) {
					SongPlayer.playFile(waiter, "assets/Undertale.mp3");
					songPlaying = true;
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "You have provided an invalid username/password");
				return;
			}
		}
	}

	/**
	 * This inner class allows us to have an callback function that receive a
	 * songFinishedPlaying message whenever an audio file has been played.
	 */

	private class ObjectWaitingForSongToEnd implements EndOfSongListener {

		@Override
		public void songFinishedPlaying(EndOfSongEvent eosEvent) {
			SongPlayer.playFile(waiter, "assets/Undertale.mp3");
		}
	}

}