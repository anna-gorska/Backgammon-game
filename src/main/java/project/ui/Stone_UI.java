package src.main.java.project.ui;


import src.main.java.project.logic.Board;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;


/**
 * Class that creates objects of stones
 */
public class Stone_UI extends Ellipse2D.Double {
    private boolean isRed; // boolean that decides if the color of stone is red or not
    private ArrayList<Integer> cones_possible_to_move_to = new ArrayList<>(); // Array that for given stone in a given moment says to which cones this stone can be moved to
    private Board board;

    public Stone_UI(boolean isRed, double xCoords, double yCoords, Board board) {
        super(xCoords, yCoords, 50, 50);
        this.isRed = isRed;
        this.board = board;
    }

    public boolean isRed() {
        return isRed;
    }

    public ArrayList<Integer> getCones_possible_to_move_to() {
        return cones_possible_to_move_to;
    }

    public ArrayList<Integer> get_Possible_Moves(int currentCone, int dice_one, int dice_two) {
        ArrayList<Integer> possible_cones = new ArrayList<>();
        int logicPos = -1;
        if (0 <= currentCone && currentCone <= 11) {
            logicPos = currentCone + 12;
        } else if (12 <= currentCone && currentCone <= 23) {
            logicPos = 23 - currentCone;
        }
        if (board.canMove(logicPos, dice_one)) {
            int highlight = -1;
            if (0 <= logicPos && logicPos <= 11) {
                highlight = logicPos - 12;
            } else if (12 <= logicPos && logicPos <= 23) {
                highlight = 23 - logicPos;
            }
            possible_cones.add(highlight);
        }
        if (board.canMove(logicPos, dice_two)) {
            int highlight = -1;
            if (0 <= logicPos && logicPos <= 11) {
                highlight = logicPos - 12;
            } else if (12 <= logicPos && logicPos <= 23) {
                highlight = 23 - logicPos;
            }
            possible_cones.add(highlight);
        }
        return possible_cones;
    }
    

    public void setCones_possible_to_move_to(ArrayList<Integer> cone_ids) {
        cones_possible_to_move_to.clear();
        for (int i = 0; i < cone_ids.size(); i++) {
            cones_possible_to_move_to.add(cone_ids.get(i));
        }
    }
}
