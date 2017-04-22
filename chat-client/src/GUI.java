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

public class GUI extends JFrame {
	JButton bChange; // reference to the button object
	JTextField input;
	JTextArea output;
	JScrollPane scroll;
	ChatClient cc;

	final int INPUT_WIDTH = 20;
	final int HISTORY = 40;
	
	// constructor for ButtonFrame
	GUI(String title, ChatClient cc) {
		super(title);
		setResizable(true);
		setLocation(100, 100);
		this.cc = cc;
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); 																		
		bChange = new JButton("Send!"); // construct a JButton
		input = new JTextField("", INPUT_WIDTH);
		output = new JTextArea(HISTORY, INPUT_WIDTH);
		output.setEditable(false);
		scroll = new JScrollPane(output);
		//scroll.setAlignmentX(0);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(input);
		panel.add(bChange);
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				cc.sendMessage(input.getText());
				input.setText("");
			}
		});
		input.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				cc.sendMessage(input.getText());
				input.setText("");
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
		input.requestFocus();
	}

}
