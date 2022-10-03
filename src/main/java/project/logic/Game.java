package src.main.java.project.logic;

import java.util.Random;
import java.util.ArrayList;

public class Game {
	Random generator;
	Board board;
	Piece.Player player;
	Dices dices;
	
	public Game(){
		generator = new Random();
		board = new Board();
		player = Piece.Player.NONE;
		dices = new Dices();
	}
	
	public void roll(){
		if(player == Piece.Player.NONE){
			dices.rollOrder();
		}else{
			dices.rollDice();
		}
		
		switch(player){
		case WHITE:
			player = Piece.Player.BLACK;
			break;
		case BLACK:
			player = Piece.Player.WHITE;
			break;
		case NONE:
			if(dices.getDice_One() > dices.getDice_Two()){
				player = Piece.Player.WHITE;
			}else{
				player = Piece.Player.BLACK;
			}
			break;
		}
	}
	

	public boolean canMove(int from, int count){

		if(!dices.isRolled()){
			System.out.println("!dices.isRolled");
			return false;
		}
		if(!dices.Dice_Is_Number(count)) {
			System.out.println("!dices.Dice_Is_Number(" + +count + ")");
			return false;
		}
		if(!board.canMove(from, count)){
			System.out.println("!board.canMove(" + from + ", " + +count + ")");
			return false;
		}
		if(board.getStone(from).color() != player) {
			String color="";
			String color1="";
			if (player==Piece.Player.WHITE){
				color="White";
			}
			if (player==Piece.Player.BLACK){
				color="Black";
			}
			if (board.getStone(from).color() ==Piece.Player.WHITE){
				color1="White";
			}
			if (board.getStone(from).color() ==Piece.Player.BLACK){
				color1="Black";
			}

			System.out.println("wrong colour" + color + " " + color1);
			return false;
		}
		System.out.println("can move");
		return true;
	}
	
	public void move(int from, int count) throws WrongMoveException{
		System.out.println(from + " - from");
		System.out.println(count + " - count");
		if(!canMove(from,count)) throw new WrongMoveException();
		board.move(from,count);
		dices.Dice_Use_Both(+count);
	}

	public void moveTo(int from, int to) throws WrongMoveException{
		if (to>23) {
			this.move(from, to-from);
			return;
		}
		if (to<0){
			this.move(from, from-to);
			return;
		}

		this.move(from, Math.abs(to-from));
	}
	
	public boolean canPut(int number){
		if(!dices.isRolled()) return false;
		if(!dices.Dice_Is_Number(+number)) return false;
		if(!board.canPut(player, number)) return false;
		
		return true;
	}
	
	public void put(int number) throws WrongMoveException{
		if(!canPut(number)) throw new WrongMoveException();
		board.put(player,number);
		dices.Dice_Use_Both(+number);
	}

	public Integer[] getPossiblePointsToMoveTo(int start) {
		ArrayList<Integer> possible = new ArrayList<Integer>();
		int multiply = 1;
		if (player == Piece.Player.BLACK ){
			multiply=-1;
		}
		if(this.canMove(start, dices.getDice_One())) {
			possible.add(start + dices.getDice_One()*multiply);
			if(this.canMove(start , dices.getDice_One()+ dices.getDice_Two())){
				possible.add(start + dices.getDice_One()*multiply + dices.getDice_Two()*multiply);
			}
		}
		if(this.canMove(start, dices.getDice_Two())) {
			possible.add(start + dices.getDice_Two()*multiply);
			if(this.canMove(start , dices.getDice_One()+ dices.getDice_Two())){
				possible.add(start + dices.getDice_One()*multiply + dices.getDice_Two()*multiply);
			}
		}

		return possible.toArray(new Integer[possible.size()]);
	}
	public Integer[] getPossiblePointsToPut() {
		ArrayList<Integer> possible = new ArrayList<Integer>();

		if(this.canPut(dices.getDice_One())) {
			possible.add(dices.getDice_One());
			if(this.canPut( dices.getDice_One()+ dices.getDice_Two())){
				possible.add(dices.getDice_One()+ dices.getDice_Two());
			}
		}
		if(this.canPut(dices.getDice_Two())) {
			possible.add( dices.getDice_Two());
			if(this.canPut( dices.getDice_One()+ dices.getDice_Two())){
				possible.add( dices.getDice_One() + dices.getDice_Two());
			}
		}

		return possible.toArray(new Integer[possible.size()]);
	}
	public Board getBoard(){
		return board;
	}
	
	public Piece.Player getPlayer(){
		return player;
	}
	public void setPlayer(Piece.Player p){
		player = p;
	}
	
	public DiceShown getDice(){
		return new DiceShown(dices);
	}
	
	public boolean isEnded(){
		return board.getHome(Piece.Player.WHITE) == 15 || board.getHome(Piece.Player.BLACK) == 15;
	}
	
	public Piece.Player winner(){
		if(!isEnded()) return Piece.Player.NONE;
		if(board.getHome(Piece.Player.WHITE) == 15) return Piece.Player.WHITE;
		return Piece.Player.BLACK;
	}
}