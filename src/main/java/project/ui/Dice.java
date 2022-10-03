package src.main.java.project.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

/**
 * Class that creates a dice that can be rolled
 */
public class Dice extends JLabel{

    private BufferedImage[] walls; // Array that stores the images that represent the walls of the dices
    private int rolledNumber; // A number that is a result of dice roll

    /**
     * Constructor without a parameter: in case we do want the rolled number to be a random integer in range 1-6
     */
    public Dice() {
        this.rolledNumber = (int) Math.random() * 6 + 1;
        setWalls();
    }

    /**
     * Method to crop a sprite with dice walls and assign each wall to an output value
     */
    private void setWalls(){
        // System.out.println(this.rolledNumber);
        walls = new BufferedImage[6];
        try {
            BufferedImage image = ImageIO.read(new File("src/main/java/project/UI/graphics/dice1.png"));
            int width = image.getWidth() / 6;
            for (int index = 0; index < 6; index++) {
                walls[index] = image.getSubimage(width * index, 0, width, width);
            }
            this.setIcon(new ImageIcon(walls[(int)(this.rolledNumber - 1)]));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setRolledNumber(int rolledNumber) {
        this.rolledNumber = rolledNumber;
        setWalls();
    }
}