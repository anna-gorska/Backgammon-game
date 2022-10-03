package project;

import project.UI.Dice;

import javax.swing.*;
import java.awt.*;

public class DiceTest {
    public static void main(String[] args) {

        JFrame frame = new JFrame("Dice roll");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //////////
        Dice dice = new Dice();
        /////////
        JPanel panel=new JPanel();
        BorderLayout borderLayout = new BorderLayout();
        panel.setLayout(borderLayout);
        panel.setMinimumSize(new Dimension(200,200));
        /////////
        panel.add(dice, borderLayout.CENTER);
        JButton rollButton = dice.getRollButton();
        panel.add(rollButton,borderLayout.NORTH);
        /////////
        frame.add(panel);
        frame.setMaximumSize(new Dimension(200, 200));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
