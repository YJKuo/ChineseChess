package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;
public class ChatUI extends JPanel{
	private String player1="";
	private String player2="";
	private String content;
	private int side;
	private int server_or_client;
	public JTextField input;
	private JTextArea chat;
	private JButton enter;
	public ChatUI() {
		setLayout(null);
		setBackground(new Color(52,141,189));
		setSize(400,680);
		//chat area
		Font f1 = new Font("微軟正黑體",Font.PLAIN,20);
		chat = new JTextArea();
		chat.setSize(355, 200);
		chat.setLocation(5,220);
		chat.setLineWrap(true);
		chat.setFont(f1);
		this.add(chat);
		Font f2 = new Font("微軟正黑體",Font.PLAIN,16);
		input = new JTextField();
		input.setLocation(10,450);
		input.setSize(265, 30);
		input.setFont(f2);
		chat.setEditable(false);
		this.add(input);
		enter = new JButton("Send");
		enter.setLocation(290,450);
		enter.setSize(70, 30);
		this.add(enter);
		content = "";
		
	}
	public JButton getSendBtn() {
		return enter;
	}
	public String getName(int server_or_client) {
		if(server_or_client == 1)
			return player1;
		else
			return player2;
				
	}
	public void setName(String name,int server_or_client,int side) {
		if(server_or_client == 1)
			player1 = name;
		else
			player2 = name;
		
		this.side=side;
		this.server_or_client= server_or_client;
		repaint();
	
	}
	
	public void paint(Graphics g){
		super.paint(g);
		Font f = new Font("Arial Bold",Font.BOLD|Font.ITALIC,30);
		g.setFont(f);
		g.setColor(Color.WHITE);
		g.drawRoundRect(25, 60, 150, 80, 20, 20);
		g.fillRoundRect(25, 60, 150, 80, 20, 20);		
		g.drawRoundRect(195, 60, 150, 80, 20, 20);
		g.fillRoundRect(195, 60, 150, 80, 20, 20);
		if(side==1 && server_or_client ==1) {
			g.setColor(Color.RED);
			g.drawString(player1, 40, 110);
			g.setColor(Color.BLACK);
			g.drawString(player2, 210, 110);
		}
		else if(side==2 && server_or_client ==1) {
			g.setColor(Color.BLACK);
			g.drawString(player1, 40, 110);
			g.setColor(Color.RED);
			g.drawString(player2, 210, 110);
		}
		else if(side==1 && server_or_client ==2) {
			g.setColor(Color.RED);
			g.drawString(player2, 210, 110);
			g.setColor(Color.BLACK);
			g.drawString(player1, 40, 110);
		}
		else if(side==2 && server_or_client ==2) {
			g.setColor(Color.BLACK);
			g.drawString(player2, 210, 110);
			g.setColor(Color.RED);
			g.drawString(player1, 40, 110);
		}		
	}
	public void setContent() {
		if(!input.getText().equals("")) 
			refreshContent(input.getText(),server_or_client);
	}
	public void refreshContent(String message, int whichplayer) {
		String newstr;
		if(whichplayer == 1) {
			newstr = player2 + ": " + message + "\n";
		}
		else {
			newstr = player1 + ": " + message + "\n";
		}
		content += newstr;
		chat.setText(content);
	}
	
	
}
