package src.main.java.project.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;

import src.main.java.project.logic.*;

/**
 * Class that creates a dice that can be rolled
 */
public class Die extends JLabel{
    
    private Callback onRoll;
    private Game game;

    private Dice dice1;
    private Dice dice2;

    public JLabel currentPlayer;

    /**
     * Constructor without a parameter: in case we do want the rolled number to be a random integer in range 1-6
     */
    public Die(Callback onRoll, Game game) {
        this.onRoll = onRoll;
        this.dice1 = new Dice();
        this.dice2 = new Dice();
        this.currentPlayer = new JLabel("None");
        this.game = game;
    }

    private void updateLabel() {
        this.currentPlayer.setText(this.game.getPlayer() == Piece.Player.WHITE ? "WHITE" : "BLACK");
    }

    /**
     * A button with functionality to cause a dice to roll
     * @return button that rolls dice
     */
    private JButton rollButton(){
        JButton roll = new JButton("Roll");
        // Adds an animation that simulates the dice roll
        roll.addActionListener(e -> {
            roll.setEnabled(false);
            Timer timer = new Timer(120, new ActionListener() {
                private int counter = 0;
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Dice "rolling" animation
                    if (counter < 6) {
                        dice1.setRolledNumber((int) (Math.random() * 6 + 1));
                        dice2.setRolledNumber((int) (Math.random() * 6 + 1));
                    }
                    // Once animation complete, get actual numbers from logic
                    else if(counter == 6) {
                        onRoll.run();
                        updateLabel();
                        roll.setEnabled(true);
                    }
                    counter++;
                }
            });
            timer.start();
        });
        return roll;
    }

    public void setDice(int i1, int i2) {
        dice1.setRolledNumber(i1);
        dice2.setRolledNumber(i2);
    }

    public JPanel panel() {
        JPanel panel = new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        panel.setLayout(borderLayout);
        panel.add(this.dice1, borderLayout.WEST);
        panel.add(this.dice2, borderLayout.EAST);
        panel.add(this.rollButton(), borderLayout.NORTH);
        panel.add(currentPlayer);
        return panel;
    }

}