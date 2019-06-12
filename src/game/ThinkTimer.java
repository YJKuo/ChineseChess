package game;

import java.awt.Font;

import javax.swing.JLabel;


public class ThinkTimer extends Thread{
	JLabel timer = new JLabel();
	public int count =60;
	Font f1 = new Font("",Font.PLAIN,16);
	int lastWhosTurn=ChineseChessBoard.WhosTurn;
	ThinkTimer(){
		timer.setFont(f1);
        timer.setText("Thinking Time: "+count);
        timer.setLocation(10, 15);
        timer.setSize(500, 30);
       
		
		
	}
	public JLabel returnLabel() {
		
		return timer;
	}
	public void run() {
		
		while(true) {
			
			try {
				
				this.sleep(1000);
				if(ChineseChessBoard.WhosTurn!=lastWhosTurn)
					timeReset();
				count--;
				if(ChineseChessBoard.GameIsPlaying==false)
					timer.setText("Thinking Time: 0");
				else {
				if(ChineseChessBoard.returnSide()==1 && ChineseChessBoard.WhosTurn==2)
					timer.setText("Thinking Time: 0");
				else if(ChineseChessBoard.returnSide()==2 && ChineseChessBoard.WhosTurn==1)
					timer.setText("Thinking Time: 0");
				else
					timer.setText("Thinking Time: "+count);
				if(count==0) {
					if(ChineseChessBoard.WhosTurn==1)
						ChineseChessBoard.WhosTurn=2;
					else if(ChineseChessBoard.WhosTurn==2)
						ChineseChessBoard.WhosTurn=1;
	
					timeReset();
				}
				lastWhosTurn=ChineseChessBoard.WhosTurn;
				}
			}catch (InterruptedException e) {e.printStackTrace();}
			
		}
		
		
	}
	public void timeReset() {
		
		count =60;
		
	}
	
	
}
