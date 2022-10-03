package src.main.java.project.ui;

import java.util.ArrayList;

/**
 * Class that stacks on stones when they don't fit on one cone
 */
public class StoneStacking {
    private int no_stones_to_stack;
    private int cone_id;
    private boolean isConeOnBottom;


    /**
     * Constructor
     * @param cone_id - id of cone where stones will be stacked
     * @param stones_to_stack - number of cones to stack
     */
    public StoneStacking(int cone_id, int stones_to_stack) {
        this.no_stones_to_stack = stones_to_stack;
        this.cone_id = cone_id;
        if (cone_id==24 || cone_id == 27){ //24 means middle panel, 27 - home
            isConeOnBottom=false;
        }
        if (cone_id==25 || cone_id == 26){//25 means middle panel, 26 - home
            isConeOnBottom=true;
        }
        else if (cone_id < 12) {
            isConeOnBottom = true;
        } else {
            isConeOnBottom = false;
        }
    }

    /**
     * Method that returns an array of y coordinates adjusted so that stones fit nicely on a cone
     * @return
     */
    public ArrayList<Integer> getyCoordsOfStackedStones() {
        ArrayList<Integer> yCoordsOfStackedStones = new ArrayList<>();
       /*
       Use the fact that height of cone is 200, and height of a stone is 50:
       Maximum number of stones that fit without stacking is 4, as 50*4=200
       Formula of how much we need to "squish" stones is: 50*x*n=200 -> x=4/n, where n is number of stones
        */
        int HEIGHT = 500;
        int number_of_stones = no_stones_to_stack;
        double x = 4.0 / number_of_stones;
        if (x > 1) {
            x = 1;
        }
        double height_delta = 50 * x; // 50 is height of 1 stone

        for (int i = 0; i < number_of_stones; i++) {
            if (isConeOnBottom) {
                yCoordsOfStackedStones.add((int) (400 - height_delta * i));
            } else {
                yCoordsOfStackedStones.add((int) (height_delta * i));
            }

        }
        return yCoordsOfStackedStones;
    }
}
