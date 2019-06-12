package game;
import javax.imageio.ImageIO;

import music.SoundPlayer;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Chess {
	private static int[][] pos= new int[10][9];                     //
	private static String[][] name = new String[10][9];                //
	private static int lxf,lyf,lxt,lyt,lpp;//l->last f->from t->to lpp->last place Pos
	private static String lpn = new String();//last place name
	private static boolean CanRegret;
	private boolean initial;

	ChineseChessBoard chineseChessBoard = new ChineseChessBoard();
	
	public Chess() {
		for(int y=0; y<10; ++y) {
			for(int x=0; x<9;++x) {
				this.pos[y][x] = 0;
			}
		}
		createChess("ROOK", 1, 0, 0);
		createChess("KNIGHT", 1, 1, 0);
		createChess("ELEPHANT", 1, 2, 0);
		createChess("ADVISOR", 1, 3, 0);
		createChess("GENERAL", 1, 4, 0);
		createChess("ADVISOR", 1, 5, 0);
		createChess("ELEPHANT", 1, 6, 0);
		createChess("KNIGHT", 1, 7, 0);
		createChess("ROOK", 1, 8, 0);
		createChess("SOLDIER", 1, 0, 3);
		createChess("SOLDIER", 1, 2, 3);
		createChess("SOLDIER", 1, 4, 3);
		createChess("SOLDIER", 1, 6, 3);
		createChess("SOLDIER", 1, 8, 3);
		createChess("CANNON", 1, 1, 2);
		createChess("CANNON", 1, 7, 2);
		
		createChess("ROOK", 2, 0, 9);
		createChess("KNIGHT", 2, 1, 9);
		createChess("ELEPHANT", 2, 2, 9);
		createChess("ADVISOR", 2, 3, 9);
		createChess("GENERAL", 2, 4, 9);
		createChess("ADVISOR", 2, 5, 9);
		createChess("ELEPHANT", 2, 6, 9);
		createChess("KNIGHT", 2, 7, 9);
		createChess("ROOK", 2, 8, 9);
		createChess("SOLDIER", 2, 0, 6);
		createChess("SOLDIER", 2, 2, 6);
		createChess("SOLDIER", 2, 4, 6);
		createChess("SOLDIER", 2, 6, 6);
		createChess("SOLDIER", 2, 8, 6);
		createChess("CANNON", 2, 1, 7);
		createChess("CANNON", 2, 7, 7);
		
	}
	public boolean getStatus(int x, int y) {
		return pos[y][x]==0? false:true;
	}
	public static String getName(int x, int y) {
		return name[y][x];
	}
	public Image getImage(String s, int x, int y) throws IOException{
		Image image;
		switch(s) {
		case "GENERAL":
			if(this.pos[y][x]==1)
				image = ImageIO.read(new File("chess/red_general.png"));
			else if(this.pos[y][x]==2)
				image = ImageIO.read(new File("chess/black_general.png"));
			else
				image = null;
			break;
		case "ROOK":
			if(this.pos[y][x]==1)
				image = ImageIO.read(new File("chess/red_rook.png"));
			else if(this.pos[y][x]==2)
				image = ImageIO.read(new File("chess/black_rook.png"));
			else
				image = null;
			break;
		case "ELEPHANT":
			if(this.pos[y][x]==1)
				image = ImageIO.read(new File("chess/red_elephant.png"));
			else if(this.pos[y][x]==2)
				image = ImageIO.read(new File("chess/black_elephant.png"));
			else
				image = null;
			break;
		case "ADVISOR":
			if(this.pos[y][x]==1)
				image = ImageIO.read(new File("chess/red_advisor.png"));
			else if(this.pos[y][x]==2)
				image = ImageIO.read(new File("chess/black_advisor.png"));
			else
				image = null;
			break;
		case "KNIGHT":
			if(this.pos[y][x]==1)
				image = ImageIO.read(new File("chess/red_knight.png"));
			else if(this.pos[y][x]==2)
				image = ImageIO.read(new File("chess/black_knight.png"));
			else
				image = null;
			break;
		case "SOLDIER":
			if(this.pos[y][x]==1)
				image = ImageIO.read(new File("chess/red_soildier.png"));
			else if(this.pos[y][x]==2)
				image = ImageIO.read(new File("chess/black_soildier.png"));
			else
				image = null;
			break;
		case "CANNON":
			if(this.pos[y][x]==1)
				image = ImageIO.read(new File("chess/red_cannon.png"));
			else if(this.pos[y][x]==2)
				image = ImageIO.read(new File("chess/black_cannon.png"));
			else
				image = null;
			break;
		default:
			image = null;
		}
		return image;
		
	}
	public static void createChess(String name, int color, int x, int y) {    ///private改public
		if(color == 1)//1:red, 2:black
			pos[y][x] = 1;
		else
			pos[y][x] = 2;
		Chess.name[y][x] = name;
	}
	public void moveChess(int fromX, int fromY, int toX, int toY) {
		SoundPlayer.getSoundManager().playSound("media/put.wav");
		pos[toY][toX] = pos[fromY][fromX];
		name[toY][toX] = name[fromY][fromX];
		pos[fromY][fromX] = 0;
		name[fromY][fromX] = null;
		CanRegret = true;
	}
	
	public static void remember(int fromX, int fromY, int toX, int toY,int targetPos, String targetName) {
		lxf = fromX;
		lyf = fromY;
		lxt = toX;
		lyt = toY;
		lpp = targetPos;
		lpn = targetName;
	}
	
	public static void regret(int WhosTurn) {
		if(CanRegret) {
		pos[lyf][lxf] = pos[lyt][lxt];
		name[lyf][lxf] = name[lyt][lxt];
		pos[lyt][lxt] = lpp;
		name[lyt][lxt] = lpn;
		CanRegret = false;
		ChineseChessBoard.changeTurn();
		}
		else {
			System.out.println("不能再悔棋了");
		}
	}
	
	public static int getPos(int x, int y) {   //要能抓到位置
		
		return pos[y][x];
	}
	public static void reset() {
		for(int y=0; y<10; ++y) {
			for(int x=0; x<9;++x) {
				pos[y][x] = 0;
			}
		}
		for(int y=0; y<10; ++y) {
			for(int x=0; x<9;++x) {
				name[y][x] = null;
			}
		}
		createChess("ROOK", 1, 0, 0);
		createChess("KNIGHT", 1, 1, 0);
		createChess("ELEPHANT", 1, 2, 0);
		createChess("ADVISOR", 1, 3, 0);
		createChess("GENERAL", 1, 4, 0);
		createChess("ADVISOR", 1, 5, 0);
		createChess("ELEPHANT", 1, 6, 0);
		createChess("KNIGHT", 1, 7, 0);
		createChess("ROOK", 1, 8, 0);
		createChess("SOLDIER", 1, 0, 3);
		createChess("SOLDIER", 1, 2, 3);
		createChess("SOLDIER", 1, 4, 3);
		createChess("SOLDIER", 1, 6, 3);
		createChess("SOLDIER", 1, 8, 3);
		createChess("CANNON", 1, 1, 2);
		createChess("CANNON", 1, 7, 2);
		
		createChess("ROOK", 2, 0, 9);
		createChess("KNIGHT", 2, 1, 9);
		createChess("ELEPHANT", 2, 2, 9);
		createChess("ADVISOR", 2, 3, 9);
		createChess("GENERAL", 2, 4, 9);
		createChess("ADVISOR", 2, 5, 9);
		createChess("ELEPHANT", 2, 6, 9);
		createChess("KNIGHT", 2, 7, 9);
		createChess("ROOK", 2, 8, 9);
		createChess("SOLDIER", 2, 0, 6);
		createChess("SOLDIER", 2, 2, 6);
		createChess("SOLDIER", 2, 4, 6);
		createChess("SOLDIER", 2, 6, 6);
		createChess("SOLDIER", 2, 8, 6);
		createChess("CANNON", 2, 1, 7);
		createChess("CANNON", 2, 7, 7);
	}
	public void checkWin() {
		
		int i,j;
		
		boolean red_king=false,black_king=false;
		if (initial) {
			red_king = false;
			black_king = false;
		}
		for(i=0;i<=2;i++) {
			for(j=3;j<=5;j++) {
				if(name[i][j]=="GENERAL")
					red_king=true;
				if(name[i+7][j]=="GENERAL")
					black_king=true;
			}
		}
		if (KingMeetKing()) {
			if (ChineseChessBoard.getTurn() == 1) {
				red_king = false;
			}
			else if (ChineseChessBoard.getTurn() == 2) {
				black_king = false;
			}
		}
		if(red_king==false) {
			System.out.println("Black WIN !!");
			ChineseChessBoard.GameEnd();
			chineseChessBoard.BlackWin();
		}
		if(black_king==false) {
			System.out.println("Red WIN !!");
			ChineseChessBoard.GameEnd();
			chineseChessBoard.RedWin();
		}
	}
	private boolean KingMeetKing() {
		boolean red_king_in_this_row = false,two_kings_in_same_row = false;
		int king_x = 4,red_king_y = 0,black_king_y = 9;
		boolean blocked = false;
		for(int i=3;i<=5;i++) {
			for(int j=0;j<=2;j++) {
				if(name[j][i]=="GENERAL") {
					red_king_in_this_row = true;
					red_king_y = j;
				}
			}
			for (int j = 0; j <= 2; j++) {
				if(name[j+7][i]=="GENERAL" && red_king_in_this_row) {
					two_kings_in_same_row = true;
					king_x = i;
					black_king_y = j+7;
				}
			}
			red_king_in_this_row = false;
		}
		if (two_kings_in_same_row) {
			for (int i = red_king_y+1; i < black_king_y; i++) {
				if (name[i][king_x] != null) {
					blocked = true;
				}
			}
			return (!blocked)&&two_kings_in_same_row;
		}
		else {
			blocked = true;
			return (!blocked)&&two_kings_in_same_row;
		}
		
	}

}
