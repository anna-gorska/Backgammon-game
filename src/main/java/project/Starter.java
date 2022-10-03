import java.util.Random;

import src.main.java.project.logic.*;

public class Starter {
    Random rand;
    Board gameboard;
    Piece.Player player;

    Dices die;

    public static void main(String[] args) {
        new Starter();
    }


    public Starter(){
        rand = new Random();
        gameboard = new Board();
        player = Piece.Player.NONE;
        die = new Dices();
    }

    public void roll(){
        if(player == Piece.Player.NONE){
            die.rollOrder();
        }else{
            die.rollDice();
        }

        switch(player){
            case WHITE:
                player = Piece.Player.BLACK;
                break;
            case BLACK:
                player = Piece.Player.WHITE;
                break;
            case NONE:
                if(die.getDice_One() > die.getDice_Two()){
                    player = Piece.Player.WHITE;
                }else{
                    player = Piece.Player.BLACK;
                }
                break;
        }
    }


    public boolean canMove(int from, int count){
        if(!die.isRolled()) return false;
        if(!die.Dice_Is_Number(count)) return false;
        if(!gameboard.canMove(from, count)) return false;
        if(gameboard.getStone(from).color() != player) return false;

        return true;
    }

    public void move(int from, int count) throws WrongMoveException{
        if(!canMove(from,count)) throw new WrongMoveException();
        gameboard.move(from,count);
        die.Dice_Use_Both(count);
    }

    public boolean canPut(int number){
        if(!die.isRolled()) return false;
        if(!die.Dice_Is_Number(number)) return false;
        if(!gameboard.canPut(player, number)) return false;

        return true;
    }

    public void put(int number) throws WrongMoveException{
        if(!canPut(number)) throw new WrongMoveException();
        gameboard.put(player,number);
        die.Dice_Use_Both(number);
    }

    public Board getBoard(){
        return gameboard;
    }

    public Piece.Player getPlayer(){
        return player;
    }

    public DiceShown getDice(){
        return new DiceShown(die);
    }

    public boolean isEnded(){
        return gameboard.getHome(Piece.Player.WHITE) == 15 || gameboard.getHome(Piece.Player.BLACK) == 15;
    }

    public Piece.Player winner(){
        if(!isEnded()) return Piece.Player.NONE;
        if(gameboard.getHome(Piece.Player.WHITE) == 15) return Piece.Player.WHITE;
        return Piece.Player.BLACK;
    }
}


