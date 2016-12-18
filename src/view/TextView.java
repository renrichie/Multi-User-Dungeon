/**
 *Author:	Chris Derck 
 *File:		TextView
 *Purpose:	Acts as the view that is responsible for displaying the rules and commands of the mud game. The user will be able
 *			to hit the textview tabbed pane of the frame to view the game info that will be contained within a text area that
 *			is decorated with a scroll pane. 
 */

package view;

import model.RulesAndCommands;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextView extends JPanel{
	
	private RulesAndCommands info;
	private int width, height;
	private JTextArea text;
	
	// Constructor that sets the components, listeners and images of the
	// graphical view.
	public TextView(RulesAndCommands info, int width, int height) {
		this.info = info;
		this.width = width;
		this.height = height;
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		
		setupView();	
	}
	
	/**
	 *Method:	setupView()
	 *Purpose:	Method that is used to set up the components of the JFrame. The view is composed of a single JTextArea that is 
	 *			contained with a scrollpane. The text area is then set to display the rules and commands that are created for
	 *			the mud game.  
	 */
	private void setupView() {
 		text = new JTextArea();
  		text.setText(info.getInfo());
  		text.setEditable(false);
 		text.setSize(740, 485);
 		text.setLocation(20, 20);
  		
  		Font textFont = new Font("Courier", Font.BOLD, 14);
  		text.setFont(textFont);
  		
 		this.add(text, BorderLayout.CENTER);	
 		JScrollPane sc = new JScrollPane(text);
 		sc.setSize(740, 485);
 		sc.setLocation(20, 20);
 		
 		this.add(sc);	
	}
	
}
