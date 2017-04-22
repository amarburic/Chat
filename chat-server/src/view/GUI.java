package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import transmission.ChatServer;

@SuppressWarnings("serial")
public class GUI extends JFrame
{
	JButton bChange; // reference to the button object
	JTextArea output;
	JScrollPane scroll;
	ChatServer cs;

	final int INPUT_WIDTH = 20;
	final int HISTORY = 40;
	
	// constructor for ButtonFrame
	public GUI(String title, ChatServer cs) {
		super(title);
		setResizable(true);
		setLocation(100, 100);
		this.cs = cs;
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); 																		
		bChange = new JButton("Turn off!"); // construct a JButton
		output = new JTextArea(HISTORY, INPUT_WIDTH);
		output.setEditable(false);
		scroll = new JScrollPane(output);
		//scroll.setAlignmentX(0);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(bChange);
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				/*ugasit server*/
			}
		});
	}

	public void updateMessages(ArrayList<String> receivedMessages) {
		String outputMessage = "";
		int listSize = receivedMessages.size();
		for(int i = 0; i <= (listSize - 1); i++) {
			outputMessage += receivedMessages.get(i) + "\n";
		}
		output.setText(outputMessage);
	}
	
	public void showGui() {
		pack();
		setVisible(true); 
	}
}