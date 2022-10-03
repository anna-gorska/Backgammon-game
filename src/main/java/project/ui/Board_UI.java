package src.main.java.project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import static javax.swing.JOptionPane.showMessageDialog;

import src.main.java.project.logic.*;

/**
 * Class that puts together a board
 */
public class Board_UI extends JPanel {
    private Polygon[] cones = new Polygon[24]; // Array that stores cone locations and sizes
    private int currentCone = -1; // ID of cone that selectedStone is on
    private ArrayList<Integer> cones_to_highlight = new ArrayList<>(); // Array that will keep the id's of cones that need to be visually highlighted (after clicking on a stone)
    private static final int WIDTH = 810;
    private static final int HEIGHT = 500;
    private static final int WIDTH_OF_PANELS = 70; // Width of middle and right panel
    private static final int DICE_PANEL = 200; // width of panel for dice
    int dice_one;
    int dice_two;
    Game game;
    private Die die;
    private Rectangle[] bars;


    /**
     * Testing the board
     *
     * @param
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(WIDTH + WIDTH_OF_PANELS*2 + DICE_PANEL, HEIGHT);
        frame.setTitle("Backgammon");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.getContentPane();

        Board_UI board_ui = new Board_UI(new Game());
        frame.add(board_ui.panel());
        frame.setVisible(true);
    }

    public Board_UI(Game game) {
        this.game = game;
        initBoard();
        class RollCallback implements Callback {
            public void run() {
                game.roll();
                die.setDice(game.getDice().getDice_One(), game.getDice().getDice_Two());
            }
        }
        this.die = new Die(new RollCallback(), this.game);
        this.setLayout(new BorderLayout());

        // Adding stones and cones to the board

        addMouseListener(new MouseAdapter() { // Mouse listener for the cones and stones
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                cones_to_highlight.clear();
                for(int i = 0; i < cones.length; i++) {
                    if(cones[i].contains(me.getPoint())) {
                        if(currentCone == i) {
                            currentCone = -1;
                        }
                        else if(currentCone != -1) {
                            if (currentCone<24) {
                                try {
                                    game.moveTo(currentCone, i);
                                    repaint();
                                    currentCone = -1;
                                } catch (WrongMoveException e) {
                                    System.out.println("Move error");
                                }
                                return;
                            }
                            else{
                                if (currentCone==25){
                                    i=i+1;
                                }
                                else if (currentCone==24){
                                    i=24-i;
                                }
                                try {
                                    game.put(i);
                                    currentCone = -1;
                                } catch(WrongMoveException e) {
                                    System.out.println("Move error from bar " + i);
                                }
                            }
                        }
                        else if(game.getPossiblePointsToMoveTo(i).length > 0) {
                            currentCone = i;
                            System.out.println("Possible moved: " + game.getPossiblePointsToMoveTo(i).length);
                            for(Integer possibleCone : game.getPossiblePointsToMoveTo(i)) {
                                System.out.println("Possible pos:" + possibleCone);
                                cones_to_highlight.add(possibleCone);
                            }
                         /*   int[] abstract_cones = new int[0];
                            int count =0;
                            if (game.getPlayer()== Piece.Player.BLACK){
                                abstract_cones = new int[]{-1, -2, -3, -4, -5, -6};
                            }
                            else if(game.getPlayer()== Piece.Player.WHITE){
                                abstract_cones = new int[]{24, 25, 26, 27, 28, 29};
                            }
                            for (int k = 0; k < abstract_cones.length; k++) {
                                if (game.getPlayer()== Piece.Player.BLACK){
                                    count = currentCone-k;
                                }
                                else if(game.getPlayer()== Piece.Player.WHITE){
                                    count = k-currentCone;
                                }

                                if (game.canMove(currentCone,count )){
                                    if (game.getPlayer()== Piece.Player.BLACK){
                                        cones_to_highlight.add(26);
                                    }
                                    else if(game.getPlayer()== Piece.Player.WHITE){
                                        cones_to_highlight.add(27);
                                    }

                                }

                            } */
                        }
                    }
                }
                for (int i = 24; i<26; i++){
                    if (bars[i-24].contains(me.getPoint())){
                        if(currentCone == i) {
                            currentCone = -1;
                        } /*else if(currentCone != -1) {
                            try {
                                game.put(i);
                                currentCone = -1;
                            } catch(WrongMoveException e) {
                                System.out.println("Move error");
                            }
                            return;
                        } */else if(game.getPossiblePointsToPut().length > 0) {
                            currentCone = i;
                            System.out.println("Possible moved: " + game.getPossiblePointsToPut().length);
                            for(Integer possibleCone : game.getPossiblePointsToPut()) {
                               // System.out.println("Possible pos:" + possibleCone);
                                if (i==25)
                                    cones_to_highlight.add(possibleCone-1);
                                if (i==24)
                                    cones_to_highlight.add(24-possibleCone);
                            }

                        }
                    }
                }
                for (int i = 26; i<28; i++) {
                    if (bars[i - 24].contains(me.getPoint())) {
                        if(currentCone == i) {
                            currentCone = -1;
                        }
                        else if (currentCone!=-1) {
                            int[] abstract_cones = new int[0];
                            if (i == 27) {
                                if (game.getPlayer()== Piece.Player.WHITE) {
                                    abstract_cones = new int[]{24, 25, 26, 27, 28, 29};
                                }
                            }
                            if (i == 26) {
                                if (game.getPlayer()== Piece.Player.BLACK) {
                                    abstract_cones = new int[]{-1, -2, -3, -4, -5, -6};
                                }
                            }
                            for (int k = 0; k < abstract_cones.length; k++) {
                                try {
                                    game.moveTo(currentCone, abstract_cones[k]);
                                    repaint();
                                    currentCone = -1;
                                    return;
                                } catch (WrongMoveException e) {
                                    System.out.println("Move error home");
                                }
                            }
                        }
                    }
                }


                repaint();
            }
        });
    }

    public JPanel panel() {
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JPanel finalPanel = new JPanel();
        finalPanel.setLayout(new BorderLayout());
        finalPanel.add(this, new BorderLayout().CENTER);
        finalPanel.add(die.panel(), new BorderLayout().EAST);
        return finalPanel;
    }

    /**
     * Method that takes care of painting things on BOARD
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, WIDTH + WIDTH_OF_PANELS * 2, HEIGHT);
        // Drawing the cones
        final BasicStroke stroke = new BasicStroke(6.0f);
        g2.setStroke(stroke);
        Color pointColor = Color.magenta;
        int width = (WIDTH - WIDTH_OF_PANELS) / 12;

        for(int i = 0; i < 24; i++) {
            g2.setColor(pointColor);
            g2.drawPolygon(cones[i].xpoints, cones[i].ypoints, 3);
            g2.fillPolygon(cones[i].xpoints, cones[i].ypoints, 3);




            StoneStacking stoneStacking = new StoneStacking(i, game.getBoard().getPiecesPerColumn()[i]);
            // System.out.println("STACKING " + i + "stones nr " + game.getBoard().getPiecesPerColumn()[i]);

            for (int j = 0; j < game.getBoard().getPiecesPerColumn()[i]; j++) {
                g2.setColor(Color.WHITE);
                if (game.getBoard().getPointColours()[i].equals(Piece.Player.BLACK)) {
                    g2.setColor(Color.DARK_GRAY);
                }
                // System.out.println("STACKING " + i + "stones nr " + j + "y coords  " + stoneStacking.getyCoordsOfStackedStones().get(j));
                g2.fillOval((int) cones[i].xpoints[0] + 7, (int) stoneStacking.getyCoordsOfStackedStones().get(j), (int) 50, (int) 50);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawOval(cones[i].xpoints[0] + 7, (int) stoneStacking.getyCoordsOfStackedStones().get(j), (int) 50, (int) 50);
            }

            // highlight selected cone
            if(currentCone == i) {
                // Drawing the cones
                final BasicStroke outline = new BasicStroke(4.0f);
                g2.setStroke(outline);
                g2.setColor(Color.green);
                g2.drawPolygon(cones[i].xpoints, cones[i].ypoints, 3);
                g2.setStroke(stroke);
                g2.setColor(pointColor);
            }

            // highlight possible moves
            if(cones_to_highlight.contains(new Integer(i))) {
                final BasicStroke outline = new BasicStroke(4.0f);
                g2.setStroke(outline);
                g2.setColor(Color.YELLOW);
                g2.drawPolygon(cones[i].xpoints, cones[i].ypoints, 3);
                g2.setStroke(stroke);
                g2.setColor(pointColor);
            }

            g2.setColor(Color.green);
            g2.drawString("  " + i, cones[i].xpoints[0], cones[i].ypoints[i <= 12 ? 0 : 1]);

        }

        // Drawing off-game panel
        g2.setColor(Color.pink);
        g2.fillRect(WIDTH, 0, WIDTH_OF_PANELS, HEIGHT);

        // Drawing middle
        g2.setColor(Color.pink);
        g2.fillRect((WIDTH - WIDTH_OF_PANELS) / 2, 0, 60, HEIGHT);

        // Drawing stones on bars
        for (int i = 24; i<=27; i++){
            int stones_no=0;
            int xCoords=0;
            boolean isBlack=false;
            switch(i) {
                case 24:
                    stones_no = game.getBoard().getBlackPiecesBar();
                    xCoords = (WIDTH - WIDTH_OF_PANELS) / 2 +5;
                    isBlack=true;
                    break;
                case 25:
                    stones_no = game.getBoard().getWhitePiecesBar();
                    xCoords = (WIDTH - WIDTH_OF_PANELS) / 2 +5;
                    break;
                case 26:
                    stones_no = game.getBoard().getBlackPiecesHome();
                    isBlack=true;
                    xCoords=WIDTH+5;
                    break;
                case 27:
                    stones_no = game.getBoard().getWhitePiecesHome();
                    xCoords=WIDTH+5;
                    break;
            }
            StoneStacking stoneStacking = new StoneStacking(i, stones_no);
            for (int j = 0; j < stones_no; j++) {
                g2.setColor(Color.WHITE);
                if (isBlack) {
                    g2.setColor(Color.DARK_GRAY);
                }
                // System.out.println("STACKING " + i + "stones nr " + j + "y coords  " + stoneStacking.getyCoordsOfStackedStones().get(j));
                g2.fillOval(xCoords, (int) stoneStacking.getyCoordsOfStackedStones().get(j), (int) 50, (int) 50);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawOval(xCoords, (int) stoneStacking.getyCoordsOfStackedStones().get(j), (int) 50, (int) 50);
            }
            if(cones_to_highlight.contains(new Integer(i))) {
                final BasicStroke outline = new BasicStroke(4.0f);
                g2.setStroke(outline);
                g2.setColor(Color.YELLOW);
          /*      if (i==24)
                    g2.drawRect((WIDTH - WIDTH_OF_PANELS) / 2, 0, 60, HEIGHT/2);
                if (i==25)
                    g2.drawRect((WIDTH - WIDTH_OF_PANELS) / 2, HEIGHT/2, 60, HEIGHT); */
                if (i==27)
                    g2.drawRect(WIDTH, 0, WIDTH_OF_PANELS, HEIGHT/2);
                if (i==26)
                    g2.drawRect(WIDTH, HEIGHT/2, WIDTH_OF_PANELS, HEIGHT);
                g2.setStroke(stroke);
                g2.setColor(pointColor);
            }

            winner();
        }

    }

    /**
     * Method to set up board with initial position of stones
     */
    public void initBoard() {

        int width = (WIDTH - WIDTH_OF_PANELS) / 12;

        for(int i = 0; i < 24; i++) {

            int middle_panel_width = ((i >= 6 && i < 12) || (i >= 18) ? WIDTH_OF_PANELS : 0);


            int[] xpoints = i < 12 ? new int[]{
                (12 - i % 12) * width - middle_panel_width, 
                (12 - i % 12) * width + width / 2 - middle_panel_width, 
                (12 - i % 12) * width + width - middle_panel_width
            } : new int[]{
                (i % 12) * width + middle_panel_width, 
                (i % 12) * width + width / 2 + middle_panel_width, 
                (i % 12) * width + width + middle_panel_width
            };
            int[] ypoints = i < 12 ? new int[]{
                250 + 200, 
                250, 
                250 + 200
            } : new int[]{
                0, 
                200, 
                0
            };
            cones[i] = new Polygon(xpoints, ypoints, 3);
        }
        bars =new Rectangle[4];
        bars[0] = new Rectangle((WIDTH - WIDTH_OF_PANELS) / 2, 0, 60, HEIGHT/2);
        bars[1] = new Rectangle((WIDTH - WIDTH_OF_PANELS) / 2, HEIGHT/2, 60, HEIGHT);
        bars[3] = new Rectangle(WIDTH, 0, WIDTH_OF_PANELS, HEIGHT/2);
        bars[2] = new Rectangle(WIDTH, HEIGHT/2, WIDTH_OF_PANELS, HEIGHT);

    }

    /**
     * Method to get the id of the cone according to the coordinates on board
     *
     * @param x - x coordinate
     * @param y - y coordinate
     * @return
     */
 /*   public int coordsToConeId(double x, double y) {
        int middle_panel_width = 0;
        if (x >= (6 * 60)) {
            middle_panel_width = WIDTH_OF_PANELS;
        }
        if (y > HEIGHT / 2 - 50) {
            return (int) ((x - middle_panel_width) / 60);
        } else {
            return (int) (((x - middle_panel_width) / 60) + 12);
        }

    } */

    public void winner(){
        if (game.isEnded()) {
            String win="";
            if (game.winner().equals(Piece.Player.WHITE)){
                win = "WHITE";
            }
            else if (game.winner().equals(Piece.Player.BLACK)){
                win = "BLACK";
            }
            showMessageDialog(null, "WINNER: " + win + " PLAYER");
        }
    }

}


