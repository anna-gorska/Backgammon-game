package src.main.java.project.logic;

import java.util.Random;

public class Dices {
	private int dice_One;
	private int dice_Two;
	
	private int dice_One_Turn;
	private int dice_Two_Turn;
	
	private Random random;
	
	public Dices(){
		random = new Random();
	}
	
	public Dices(Dices d) {
		dice_One = d.dice_One;
		dice_Two = d.dice_Two;
		dice_One_Turn = d.dice_One_Turn;
		dice_Two_Turn = d.dice_Two_Turn;
		random = new Random();
	}

	/**
	 * 
	 *
	 */
	public void rollDice(){
		dice_One = random.nextInt(6) + 1;
		dice_Two = random.nextInt(6) + 1;
		if(dice_One == dice_Two){
			dice_One_Turn = 2;
			dice_Two_Turn = 2;
		}else{
			dice_One_Turn = 1;
			dice_Two_Turn = 1;
		}
		System.out.println("Rolled numbers: " + dice_One + ", " + dice_Two);
	}
	
	public boolean Dice_Is_Number(int number){
		if(dice_One_Turn > 0 && dice_One == number) {
			return true;
		}
		if(dice_Two_Turn > 0 && dice_Two == number) {
			return true;
		}
		if(dice_One_Turn > 0 && dice_Two_Turn > 0 && dice_One+dice_Two == number){
            return true;
		}
		return false;
	}
	
	public void rollOrder(){
		do{
			Random generator = new Random();
			dice_One = generator.nextInt(6) + 1;
			dice_Two = generator.nextInt(6) + 1;
			System.out.println("Rolled numbers: " + dice_One + ", " + dice_Two);
		}while(dice_One == dice_Two);
		dice_One_Turn = dice_Two_Turn = 1;
	}
	
	public void Dice_Use_Both(int number){
		if(dice_One_Turn > 0 && dice_One == number){
			dice_One_Turn--;
		}else if(dice_Two_Turn > 0 && dice_Two == number) {
            dice_Two_Turn--;
        }else if(dice_One_Turn > 0 && dice_Two_Turn > 0 && dice_One+dice_Two == number){
		    dice_One_Turn--;
		    dice_Two_Turn--;
		}else{
			throw new IllegalArgumentException("Trying to take invalid dice " + number);
		}
	}
	
	public int Dice_Use_First(){
		if(dice_One_Turn == 0) return 0;
		dice_One_Turn--;
		return dice_One;
	}
	
	public int Dice_Use_Second(){
		if(dice_Two_Turn == 0) return 0;
		dice_Two_Turn--;
		return dice_Two;
	}
	
	public boolean isRolled(){
		return dice_One_Turn > 0 || dice_Two_Turn > 0;
	}
	
	public int getDice_One(){
		return dice_One;
	}
	
	public int getDice_Two(){
		return dice_Two;
	}

}