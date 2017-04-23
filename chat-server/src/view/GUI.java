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
import javax.swing.text.DefaultCaret;

import model.Message;
import transmission.ChatServer;

@SuppressWarnings("serial")
public class GUI extends JFrame
{
	JButton turnOff; 
	JTextArea output;
	JScrollPane scroll;
	ChatServer cs;

	final int INPUT_WIDTH = 20;
	final int HISTORY = 40;
	
	public GUI(String title, ChatServer cs) {
		super(title);
		setResizable(true);
		setLocation(100, 100);
		this.cs = cs;
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); 																		
		turnOff = new JButton("Turn off!"); 
		output = new JTextArea(HISTORY, INPUT_WIDTH);
		output.setEditable(false);
		DefaultCaret caret = (DefaultCaret)output.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		scroll = new JScrollPane(output);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setAutoscrolls(true);
		add(scroll);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(turnOff);
		add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		turnOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				/* TODO */
			}
		});
	}

	public void updateMessages(ArrayList<Message> messages) {
		String outputMessage = "";
		int listSize = messages.size();
		for(int i = 0; i <= (listSize - 1); i++) {
			outputMessage += messages.get(i) 
					+ (i == (listSize - 1) ? "" : "\n");
		}
		String currentText = output.getText();
		String newText = currentText + (currentText.length() == 0 ? "" : "\n") + outputMessage;
		output.setText(newText);
	}
	
	public void showGui() {
		pack();
		setVisible(true); 
	}
}