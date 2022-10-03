package src.main.java.project.logic;

import java.util.Arrays;
import java.util.HashMap;

public class Board {

	private int whitePiecesHome; // Number of white pieces home
	private int blackPiecesHome; // Number of black pieces home
	private int whitePiecesBar; // How many white pieces have been sent off the board?
	private int blackPiecesBar; // How many black pieces have been sent off the board?
	private int[] piecesPerColumn; // How many stones are on each position of the board?
	private Piece.Player[] pointColours; // Which colour is each column (point) ?
	
	public Board(){
		init();
	}
	
	// Initialise the board with pieces in the right positions
	public void init(){
		whitePiecesHome = 0;
		blackPiecesHome = 0;
		whitePiecesBar = 0;
		blackPiecesBar = 0;
		piecesPerColumn = new int[24];
		pointColours = new Piece.Player[24];
		for(int i=0; i<24; i++){
			pointColours[i] = Piece.Player.NONE;
		}
		piecesPerColumn[0] = 2; pointColours[0] = Piece.Player.WHITE;
		piecesPerColumn[11] = 5; pointColours[11] = Piece.Player.WHITE;
		piecesPerColumn[16] = 5; pointColours[16] = Piece.Player.WHITE;
		piecesPerColumn[18] = 3; pointColours[18] = Piece.Player.WHITE;
		
		piecesPerColumn[23] = 2; pointColours[23] = Piece.Player.BLACK;
		piecesPerColumn[12] = 5; pointColours[12] = Piece.Player.BLACK;
		piecesPerColumn[7] = 5; pointColours[7] = Piece.Player.BLACK;
		piecesPerColumn[5] = 3; pointColours[5] = Piece.Player.BLACK;
	}



	public int getStoneCount(int i) {
		if(i < 0 || i > 24) return 0;

		return piecesPerColumn[i];
	}

	public Piece getStone(int i) {
		if(i < 0 || i > 24) return Piece.NONE;
		
		switch(pointColours[i]){
		case WHITE: return Piece.WHITE;
		case BLACK: return Piece.BLACK;
		default: return Piece.NONE;
		}
	}

	// Get count of stones on the bar per color
	public int getBarCount(Piece.Player color) {
		switch(color){
		case WHITE: return whitePiecesBar;
		case BLACK: return blackPiecesBar;
		default: return 0;
		}
	}

	// Check whether a given stone can move from initial position a number of steps
	public boolean canMove(int from, int count){
		if(from < 0 || from > 24) return false;
		if(piecesPerColumn[from] == 0) return false; // Cannot make move if "from" point doesn't have stone on it
		Piece.Player who = pointColours[from];
		int target;

		// Can't make the move if player still has stones in bar
		if(who == Piece.Player.WHITE){
			if(whitePiecesBar > 0) return false; 
			target = from + count;

		}else{
			if(blackPiecesBar > 0) return false;
			target = from - count;
		}


		// If can proceed with move
		if(target > 23 || target < 0){
		return hasAllInBase(who,from);
		}
		Piece.Player tarwho = pointColours[target];
		if(tarwho == who || tarwho == Piece.Player.NONE){
			return true;
		}else{
			return piecesPerColumn[target] == 1;
		}
	}

	
	public boolean canPut(Piece.Player color, int number){
		switch(color){
		case WHITE:
			if(whitePiecesBar == 0) return false;
			if(pointColours[number-1] == Piece.Player.BLACK) return false;
			break;
		case BLACK:
			if(blackPiecesBar == 0) return false;
			if(pointColours[24-number] == Piece.Player.WHITE) return false;
			break;
		default:	return false;
		}
		return true;
	}
	// Checks whether given color has all in base, 
	public boolean hasAllInBase(Piece.Player color, int except){
		int f;
		int t;
		switch(color){
		case WHITE:
			if(whitePiecesBar > 0) return false;
			f=0; t=18;
			break;
		case BLACK:
			if(blackPiecesBar > 0) return false;
			f=6; t=24;
			break;
		default: return false;
		}
		
		for(int i=f; i < t; i++){
			if(pointColours[i] == color && (i != except || piecesPerColumn[i] > 1)){
				return false;
			}
		}

		return true;
	}
	
	public void move(int from, int count) throws WrongMoveException{
		if(!canMove(from,count)) throw new WrongMoveException();
		if(pointColours[from] == Piece.Player.WHITE){
			int target = from + count;
			if(target >23){
				whitePiecesHome++;
			}else if(pointColours[target] == Piece.Player.BLACK){
				removeStone(target);
				blackPiecesBar++;
				addStone(target,Piece.Player.WHITE);
			}else{
				addStone(target,Piece.Player.WHITE);
			}
		}else{
			int target = from - count;
			if(target <0){
				blackPiecesHome++;
			}else if(pointColours[target] == Piece.Player.WHITE){
				removeStone(target);
				whitePiecesBar++;
				addStone(target,Piece.Player.BLACK);
			}else{
				addStone(target,Piece.Player.BLACK);
			}
		}
		removeStone(from);
	}
	
	public void put(Piece.Player color, int number) throws WrongMoveException{
		if(!canPut(color,number)) throw new WrongMoveException();
		
		switch(color){
		case WHITE:
			whitePiecesBar--;
			addStone(number-1,color);
			break;
		case BLACK:
			blackPiecesBar--;
			addStone(24-number,color);
			break;
		}		
	}

	private void removeStone(int from) {
		if(piecesPerColumn[from] <= 0)
			throw new IllegalArgumentException("Removing stone from zero at " + from);
		piecesPerColumn[from]--;
		if(piecesPerColumn[from] == 0){
			pointColours[from] = Piece.Player.NONE;
		}
	}

	private void addStone(int to, Piece.Player color) {
		if(pointColours[to] != Piece.Player.NONE && pointColours[to] != color)
			throw new IllegalArgumentException("Adding wrong color of stone to " + to);
		piecesPerColumn[to]++;
		if(pointColours[to] == Piece.Player.NONE){
			pointColours[to] = color;
		}
	}

	public int getHome(Piece.Player color) {
		switch(color){
		case WHITE: return whitePiecesHome;
		case BLACK: return blackPiecesHome;
		default: return 0;
		}
	}

	public int[] getPiecesPerColumn() {
		return piecesPerColumn;
	}

	public Piece.Player[] getPointColours() {
		return pointColours;
	}

	public int getWhitePiecesBar() {
		return whitePiecesBar;
	}

	public int getWhitePiecesHome() {
		return whitePiecesHome;
	}

	public int getBlackPiecesHome() {
		return blackPiecesHome;
	}

	public int getBlackPiecesBar() {
		return blackPiecesBar;
	}
}