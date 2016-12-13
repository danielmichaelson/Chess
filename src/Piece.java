import java.awt.Color;
import java.util.List;

/**
 * Created by danielmichaelson on 1/31/16.
 */

public abstract class Piece
{
    //private List moveList;
    private Color color;
    private int x;
    private int y;

    // Console text colors thanks to
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    // Checks squares diagonally between (start, end)
    public boolean anyDiagCollisions(Board board, int toX, int toY)
    {
        List<Square> squares = board.getDiagSquares(this.x, this.y, toX, toY);
        for(Square s: squares)
        {
            if(s.isOccupied())
            {
                return true;
            }
        }
        return false;
    }

    // Check for collisions in a line from (start, end)
    public boolean anyLineCollisions(Board board, int toX, int toY)
    {
        List<Square> squares = board.getLineSquares(this.x, this.y, toX, toY);
        for(Square s: squares)
        {
            if(s.isOccupied())
            {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Piece other)
    {
        return (this.x == other.getX() && this.y == other.getY());
    }

    public boolean isValidMove(Board board, int toX, int toY)
    {
        return isValidMove(board, this.x, this.y, toX, toY);
    }

    abstract boolean isValidMove(Board board, int fromX, int fromY, int toX, int toY);

    public String toString(boolean colorized)
    {
        if(colorized && Color.BLACK.equals(this.getColor()))
        {
            return ANSI_RED + toString() + ANSI_RESET;
        }
        else
        {
            return toString();
        }
    }
}
