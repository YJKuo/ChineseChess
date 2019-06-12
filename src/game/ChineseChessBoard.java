package game;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.Random;
import javax.imageio.*;
import javax.swing.*;

import music.SoundPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChineseChessBoard extends JPanel implements MouseListener, MouseMotionListener,KeyListener{
    private Image backgroundImage;
    private Image selectedImageRed;
	private Image selectedImageBlack;
    
    private static Chess chess = new Chess();
    private final int CHESS_SIZE = 55;
    private final int SELECTED_BLOCK_SIZE = 58;
	private final int OFFSET_X = 25;
	private final int OFFSET_Y = 24;
	private final int OFFSET_SELECTED_X = 1;
	private final int OFFSET_SELECTED_Y = 4;
	private Dimension[][] chessDimensionBlock = new Dimension[10][9];
	private Dimension closestToMouseDimension;
	private Dimension selectedDimension;
	public static int WhosTurn = 1;
	private static int side = 1;
	private static int server_or_client=1;
	static String msg;
	static Server server;
	static Client client;
	static String name;
	static ThinkTimer timer = new ThinkTimer(); 
	private static JFrame frame;
	private JFrame frame2;
	private JFrame frame3;
	private static JButton reset, regret, AdmitDefeat;
	private static JTextField in;
	private static ChatUI panel;
	private static JFrame nameIn;
	
	public static boolean GameIsPlaying = false;
	boolean selected = false;
	int selectedX;
	int selectedY;
	public ChineseChessBoard(){
		try {
		this.backgroundImage = ImageIO.read(new File("chessboard.png"));
		this.selectedImageRed = ImageIO.read(new File("selected_red.png"));
		this.selectedImageBlack = ImageIO.read(new File("selected_black.png"));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		setupDimensionBlock();
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage,10,10,null);
		try {
			drawAllChesses(g);
			drawMouseBlock(g);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
    public static void main(String[] args) {
    	SoundPlayer.getSoundManager().playLoopMusic("media/BGM.wav");
        frame = buildFrame();
        panel = new ChatUI();
        //frame.addWindowListener(new AdapterDemo());
        ChineseChessBoard pane = new ChineseChessBoard();
        //frame.add(pane);
        frame.setLayout(null);
        
        panel.add(timer.returnLabel());
        
        
        
        
        reset = new JButton("Reset Board");
        ButtonListener bl1 = pane.new ButtonListener();
        reset.addActionListener(bl1);
        reset.setLocation(0, 170);
        reset.setSize(130, 30);
        panel.add(reset);
        reset.setFocusable(true);
        reset.setEnabled(false);
        
        regret = new JButton("Regret");
        ButtonListener bl2 = pane.new ButtonListener();
        regret.addActionListener(bl2);
        regret.setLocation(260, 170);
        regret.setSize(100, 30);
        panel.add(regret);
        regret.setFocusable(true);
        regret.setEnabled(false);
        
        AdmitDefeat = new JButton("Admit Defeat");
        ButtonListener bl3 = pane.new ButtonListener();
        AdmitDefeat.addActionListener(bl3);
        AdmitDefeat.setLocation(130, 170);
        AdmitDefeat.setSize(130, 30);
        panel.add(AdmitDefeat);
        AdmitDefeat.setFocusable(true);
        
        JButton sendMsg = panel.getSendBtn();
        ButtonListener bl4 = pane.new ButtonListener();
        sendMsg.addActionListener(bl4);
        
        nameIn = new JFrame();
        nameIn.setSize(300, 250);
        nameIn.setLocation(350, 185);
        nameIn.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        nameIn.setLayout(null);
        
        in = new JTextField();
        in.setLocation(50,90);
		in.setSize(130, 30);
		nameIn.add(in);
		JLabel label = new JLabel("Please Enter Your Name:");
    	label.setLocation(70, 50);
    	label.setSize(200, 30);
    	nameIn.add(label);
		JButton enter = new JButton("Enter");
		ButtonListener bl5 = pane.new ButtonListener();
		enter.addActionListener(bl5);
		enter.setLocation(190,90);
		enter.setSize(80, 30);
		nameIn.add(enter);
		
		panel.setLocation(620,0);
        pane.setLocation(0, 0);
        pane.setSize(620, 720);
        frame.add(pane);
        frame.add(panel);
        frame.setVisible(true);
        frame.setResizable(false);
        nameIn.setVisible(true);
        nameIn.toFront();
        
        pane.setFocusable(true);
        pane.requestFocusInWindow();

        
        client = new Client();
        boolean HasServer =  client.creatClient();
        if(HasServer==false) {
        	
        	server = new Server();
        
        }
        else {
        	server_or_client=2;
        	Random ran = new Random();       	
            side=ran.nextInt(2)+1;
            System.out.println("client:"+side);
            
            if(side==1)
            	client.sentMSG("1,2");
            else
            	client.sentMSG("1,1");
            
            
        }
    

        pane.listenMSG();
        
    }
        	
    
    
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode()==KeyEvent.VK_CONTROL) {
         Chess.reset();
         repaint();
       }
       else if (e.getKeyCode() == KeyEvent.VK_R) {
    	Chess.regret(WhosTurn);
		repaint();
       }
    }

    public static int getTurn() {
   		return WhosTurn;
   	}
    public static void changeTurn() {
    	if (WhosTurn == 1) {
			WhosTurn = 2;
		} 
		else {
			WhosTurn = 1;
		}
	}
    private static JFrame buildFrame() {
        JFrame frame = new JFrame("Chinese Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 720);
        return frame;
    }
    private void setupDimensionBlock() {
		final int ORG_X = 52;  
		final int ORG_Y = 37;
        
        for (int y = 0 ; y < 10 ; y ++)
        	for (int x = 0 ; x < 9 ; x ++)
        		chessDimensionBlock[y][x] = new Dimension(ORG_X + x*59  , ORG_Y + y*61);
	}
    @Override
	public void mouseClicked(MouseEvent e) {}
    
    @Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(GameIsPlaying) {
			Dimension dimension = getClosestDimension(e.getX(), e.getY());
			int x = 0, y = 0;
			
			OutLoop:
			for (int i = 0 ; i < 10 ; i ++)
				for (int j = 0 ; j < 9 ; j ++)
					if (dimension == chessDimensionBlock[i][j])
					{
						y = i;
						x = j;
						break OutLoop;
					}
			if(selected) {
				
				if(Rule.CanGo(selectedX, selectedY, x, y)) {
					if((side==1&&Chess.getPos(selectedX, selectedY)==1)||(side==2&&Chess.getPos(selectedX, selectedY)==2)){
					Chess.remember(selectedX, selectedY, x, y,Chess.getPos(x, y),Chess.getName(x, y));
					chess.moveChess(selectedX, selectedY, x, y);
					msg = "0,"+selectedX+","+selectedY+","+x+","+y;
					if(server_or_client==1)
						server.sentMSG(msg);
					else if(server_or_client==2)
						client.sentMSG(msg);
					
					if (WhosTurn == side) {
						regret.setEnabled(true);
					}
					else {
						regret.setEnabled(false);
					}
					
					chess.checkWin();
					
					
			
					if (WhosTurn == 1) {
						WhosTurn = 2;					
					} 
					else {
						WhosTurn = 1;
					}
				}
				}
				else {
					SoundPlayer.getSoundManager().playSound("media/error.wav");
				}
				selected = false;
			}
			else {				
				if(Chess.getPos(x,y)!=0 && Chess.getPos(x, y) == WhosTurn) {  ////////////////////
					this.selectedX = x;
					this.selectedY = y;
					selected = true;
				}
			}
			repaint();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {
		closestToMouseDimension = null;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		closestToMouseDimension = getClosestDimension(e.getX(), e.getY());
		if(!selected) {
			selectedDimension = getClosestDimension(e.getX(), e.getY());
		}
		repaint();
	}
	
    private void drawAllChesses(Graphics g) throws IOException{
    	for(int y=0; y<10; ++y) {
			for(int x=0; x<9;++x) {
				Dimension dimension = chessDimensionBlock[y][x];
				if(chess.getStatus(x, y)) {
					g.drawImage(chess.getImage(Chess.getName(x, y), x, y), (int)(dimension.getWidth()-OFFSET_X), (int)(dimension.getHeight()-OFFSET_Y), CHESS_SIZE, CHESS_SIZE, null);
				}
			}
		}
    }
   
    private void drawMouseBlock(Graphics g){
    	if (closestToMouseDimension != null)
        {
        	g.drawImage(selectedImageBlack, (int)(closestToMouseDimension.getWidth() - OFFSET_SELECTED_X - OFFSET_X), 
        			(int)(closestToMouseDimension.getHeight() - OFFSET_SELECTED_Y - OFFSET_Y), 
        				SELECTED_BLOCK_SIZE, SELECTED_BLOCK_SIZE, null);
        }
		if(selected && selectedDimension != null) {
			/* draw selected block */
			g.drawImage(selectedImageRed, (int)(selectedDimension.getWidth() - OFFSET_SELECTED_X - OFFSET_X), 
        			(int)(selectedDimension.getHeight() - OFFSET_SELECTED_Y - OFFSET_Y), 
        				SELECTED_BLOCK_SIZE, SELECTED_BLOCK_SIZE, null);
		}
	}
  
    private Dimension getClosestDimension(int x, int y){
		double closedOffset = 1000;
		Dimension closestDimension = new Dimension(1000, 1000);
		for (int i = 0 ; i < 10 ; i ++)
        	for (int j = 0 ; j < 9 ; j ++)
        	{
        		Dimension dimension = chessDimensionBlock[i][j];
        		int offsetX = Math.abs(x - (int)dimension.getWidth());
        		int offsetY = Math.abs(y - (int)dimension.getHeight());
        		double offsetZ = Math.sqrt(offsetX*offsetX + offsetY*offsetY);

        		if (offsetZ < closedOffset)
        		{
        			closedOffset = offsetZ;
        			closestDimension = dimension;
        		}
        	}
		
		return closestDimension;
	}
    public static void GameEnd() {
		GameIsPlaying = false;
		regret.setEnabled(false);
		reset.setEnabled(true);
		AdmitDefeat.setEnabled(false);
		//System.out.println("Game End");
	}
    public void BlackWin() {
		JFrame frame = new JFrame();
		frame.setSize(300, 250);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(null);
		
		JLabel label = new JLabel("Black Side Win!");
		label.setLocation(100, 100);
		label.setSize(100, 50);
		frame.add(label);
		
		frame.setVisible(true);
    	frame.toFront();
    	SoundPlayer.getSoundManager().playSound("media/checkmate.wav");
	}
    public void RedWin() {
		JFrame frame = new JFrame();
		frame.setSize(300, 250);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(null);
		
		JLabel label = new JLabel("Red Side Win!");
		label.setLocation(100, 100);
		label.setSize(100, 30);
		frame.add(label);
		
		frame.setVisible(true);
    	frame.toFront();
    	SoundPlayer.getSoundManager().playSound("media/checkmate.wav");
	}
    public static void connectSucess() {
    	if(server_or_client==1)
    		server.sentMSG("7"+panel.getName(server_or_client));
    	else if(server_or_client==2)
    		client.sentMSG("7"+panel.getName(server_or_client));
    	
    }
    public class ButtonListener implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		if (e.getActionCommand() == "Regret") {
    			regret.setEnabled(false);
    			if (side == 1) {
					msg = "5,1";
					if(server_or_client==1)
						server.sentMSG(msg);
					else if(server_or_client==2)
						client.sentMSG(msg);
				}
				else if (side == 2) {
					msg = "5,2";
					if(server_or_client==1)
						server.sentMSG(msg);
					else if(server_or_client==2)
						client.sentMSG(msg);
				}
    		}
    		else if (e.getActionCommand() == "Reset Board") {
    			Chess.reset();
    			repaint();
    			GameIsPlaying = true;
    			timer.timeReset();
    			reset.setEnabled(false);
    			regret.setEnabled(false);
    			AdmitDefeat.setEnabled(true);
    			msg = "4";
    			if(server_or_client==1)
					server.sentMSG(msg);
				else if(server_or_client==2)
					client.sentMSG(msg);
    		}
    		else if (e.getActionCommand() == "Admit Defeat") {
				if (side == 1) {
					msg = "2,1";
					if(server_or_client==1)
						server.sentMSG(msg);
					else if(server_or_client==2)
						client.sentMSG(msg);
					//System.out.println("Black WIN !!");
				}
				else if (side == 2) {
					msg = "2,2";
					if(server_or_client==1)
						server.sentMSG(msg);
					else if(server_or_client==2)
						client.sentMSG(msg);
					//System.out.println("Red WIN !!");
				}
			}
    		else if (e.getActionCommand() == "Allow") {
				msg = "3,1";
				if(server_or_client==1)
					server.sentMSG(msg);
				else if(server_or_client==2)
					client.sentMSG(msg);
				frame2.dispose();
				GameEnd();
				if (side == 1) {
					BlackWin();
				} else {
					RedWin();
				}
			}
    		else if (e.getActionCommand() == "Refuse") {
				msg = "3,2";
				if(server_or_client==1)
					server.sentMSG(msg);
				else if(server_or_client==2)
					client.sentMSG(msg);
				frame2.dispose();
			}
    		else if (e.getActionCommand() == "Allow regret") {
    			Chess.regret(ChineseChessBoard.getTurn());
				repaint();
				msg = "6,1";
				if(server_or_client==1)
					server.sentMSG(msg);
				else if(server_or_client==2)
					client.sentMSG(msg);
				frame3.dispose();
			}
    		else if (e.getActionCommand() == "Refuse regret") {
				msg = "6,2";
				if(server_or_client==1)
					server.sentMSG(msg);
				else if(server_or_client==2)
					client.sentMSG(msg);
				frame3.dispose();
			}
    		else if(e.getActionCommand() == "Enter") {
    			name = in.getText();
    			msg = "7,"+in.getText();
    			panel.setName(in.getText(), server_or_client,2);
    			nameIn.dispose();
    			if(server_or_client==2)
    				client.sentMSG(msg);
    		}
    		else if(e.getActionCommand() == "Send") {
    			panel.setContent();
    			if(!panel.input.getText().equals("")) {
    				msg = "8,"+panel.input.getText();
	    			if(server_or_client==1)
						server.sentMSG(msg);
					else if(server_or_client==2)
						client.sentMSG(msg);
    			}
    			panel.input.setText("");
    		}
    	}
    }
    public void AllowSurrender() {
    	frame2 = new JFrame();
    	frame2.setSize(300, 250);
    	frame2.setLocation(100, 100);
    	frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	frame2.setLayout(null);
    	
    	JButton allow = new JButton("Allow");
    	ButtonListener buttonListener = new ButtonListener();
    	allow.addActionListener(buttonListener);
    	allow.setLocation(20, 150);
    	allow.setSize(100, 30);
    	frame2.add(allow);
    	
    	JButton refuse = new JButton("Refuse");
    	refuse.addActionListener(buttonListener);
    	refuse.setLocation(180, 150);
    	refuse.setSize(100, 30);
    	frame2.add(refuse);
    	
    	JLabel label = new JLabel("Do you allow enemy to surrender?");
    	label.setLocation(50, 50);
    	label.setSize(200, 30);
    	frame2.add(label);
    	
    	
    	frame2.setVisible(true);
    	frame2.toFront();
	}
    public void AllowRegret() {
    	frame3 = new JFrame();
    	frame3.setSize(300, 250);
    	frame3.setLocation(100, 100);
    	frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	frame3.setLayout(null);
    	
    	JButton allow = new JButton("Allow regret");
    	ButtonListener buttonListener = new ButtonListener();
    	allow.addActionListener(buttonListener);
    	allow.setLocation(20, 150);
    	allow.setSize(130, 30);
    	frame3.add(allow);
    	
    	JButton refuse = new JButton("Refuse regret");
    	refuse.addActionListener(buttonListener);
    	refuse.setLocation(150, 150);
    	refuse.setSize(130, 30);
    	frame3.add(refuse);
    	
    	JLabel label = new JLabel("Do you allow enemy to regret?");
    	label.setLocation(50, 50);
    	label.setSize(200, 30);
    	frame3.add(label);
    	
    	
    	frame3.setVisible(true);
    	frame3.toFront();
	}
    public static int returnSide() {
    	
    	return side;
    	
    }
    private void decodeMSG(String msg) {
    	String[] token=msg.split(",");
    	if(token[0].equals("0")) {
    		
    		int fx=Integer.parseInt(token[1]);
    		int fy=Integer.parseInt(token[2]);
    		int tx=Integer.parseInt(token[3]);
    		int ty=Integer.parseInt(token[4]);
    		
    		System.out.println(fx+","+fy+","+tx+","+ty);
    		Chess.remember(fx, fy, tx, ty,Chess.getPos(tx, ty),Chess.getName(tx, ty));
    		chess.moveChess(fx, fy, tx, ty);
    		if (WhosTurn == side) {
				regret.setEnabled(true);
			}
			else {
				regret.setEnabled(false);
			}
    		repaint();
    		
    		chess.checkWin();
    		
    		if(side==1)
    			WhosTurn = 1;
    		else if(side==2)
    			WhosTurn = 2;
    	}
    	else if(token[0].equals("1")) {
    		side = Integer.parseInt(token[1]);	
    		System.out.println("server:"+side);
    	}
    	else if (token[0].equals("2")) {
    		System.out.println(token[0]);
			if (side != Integer.parseInt(token[1])) {
				AllowSurrender();
			}
		}
    	else if (token[0].equals("3")) {
    		System.out.println(token[0]);
    		if (token[1].equals("1")) {
    			if (side == 1) {
        			System.out.println("Red WIN !!");
        			GameEnd();
        			RedWin();
        			regret.setEnabled(false);
        		} 
        		else if(side == 2){
        			System.out.println("Black WIN !!");
        			GameEnd();
        			BlackWin();
        			regret.setEnabled(false);
        		}
			}
    		else if (token[1].equals("2")) {
				System.out.println("Surrender failed");
			}
		}
    	else if (token[0].equals("4")) {
    		System.out.println(token[0]);
			Chess.reset();
			timer.timeReset();
			repaint();
			GameIsPlaying = true;
			reset.setEnabled(false);
			AdmitDefeat.setEnabled(true);
		}
    	else if (token[0].equals("5")) {
    		System.out.println(token[0]);
    		if (side != Integer.parseInt(token[1])) {
				AllowRegret();
			}
		}
    	else if (token[0].equals("6")) {
			System.out.println("receive"+token[0]+","+token[1]);
			if (token[1].equals("1")) {
				Chess.regret(ChineseChessBoard.getTurn());
				repaint();
				System.out.println("regret");
			}
			else if(token[1].equals("2")){
				System.out.println("Regret failed");
			}
		}
    	else if(token[0].equals("7")) {
    		
    		if(server_or_client==1 && side == 1) {
    			server.sentMSG("7,"+name);
    			panel.setName(token[1],2,2);
    			GameIsPlaying = true;
    			timer.start();
    		}
    		else if(server_or_client==1 && side == 2) {
    			server.sentMSG("7,"+name);
    			panel.setName(token[1],2,1);
    			GameIsPlaying = true;
    			timer.start();
    		}
    		else if(server_or_client==2 && side ==1) {
    			
    			panel.setName(token[1],1,2);
    			GameIsPlaying = true;
    			timer.start();
    		}
    		else if(server_or_client==2 && side ==2) {
    			
    			panel.setName(token[1],1,1);
    			GameIsPlaying = true;
    			timer.start();
    		}   		
    	}
    	else if(token[0].equals("8")) {
    		panel.refreshContent(token[1],server_or_client);
    	}
    }
    
    private void listenMSG() {
    	
    	while(true) {
    		if(server_or_client==1)
    			msg = server.receiveMSG();
    		else if(server_or_client==2)
    			msg = client.receiveMSG();
    		
        	decodeMSG(msg);
        	
        	}
    	
    }
}

