package src.main.java.project.logic;

public class Piece {
    public enum Player {
        WHITE,
        BLACK,
        NONE
    }
    public static Piece WHITE = new Piece(Player.WHITE);
    public static Piece BLACK = new Piece(Player.BLACK);
    public static Piece NONE = new Piece(Player.NONE);

    private Player color;


    public Piece(Player color) {
        this.color = color;
    }

    public Piece(boolean color) {
        this.color = (color?Player.WHITE:Player.BLACK);
    }

    public Piece() {
        this.color = Player.NONE;
    }

    public Player getColor() {
        return color;
    }

    public void setColor(Player color) {
        this.color = color;
    }

    @Override
    public int hashCode() {
        final int p = 31;
        int result = 1;
        result = p * result + ((color == null) ? 0 : color.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Piece other = (Piece) o;
        if (color != other.color)
            return false;
        return true;
    }

    @Override
    public String toString() {
        switch(color){
            case NONE: return " ";
            case WHITE: return "O";
            case BLACK: return "#";
            default: return "$";
        }
    }

    public Player color() {
        return color;
    }



}
