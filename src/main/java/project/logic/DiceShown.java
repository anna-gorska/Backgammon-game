package src.main.java.project.logic;

public class DiceShown {

    Dices Dice;

    public DiceShown(Dices d){
        Dice = d;
    }

    public boolean Rollednumber(int number) {

        return Dice.Dice_Is_Number(number);
    }

    public boolean getRoll() {

        return Dice.isRolled();
    }

    public int getDice_One() {

        return Dice.getDice_One();
    }

    public int getDice_Two() {

        return Dice.getDice_Two();
    }

    public boolean isRolled(){
        return Dice.isRolled();
    }

}


