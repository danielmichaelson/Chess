/**
 * Created by danielmichaelson on 2/3/16.
 */
import java.awt.Color;

public class Square
{
    private Color squareColor;
    private Piece presentPiece;
    private int x;
    private int y;

    public Square()
    {
        this.squareColor = null;
        this.presentPiece = null;
    }

    public Square(Color color, int x, int y)
    {
        this.squareColor = color;
        this.presentPiece = null;
        this.x = x;
        this.y = y;
    }

    public Square(Color color, Piece piece)
    {
        this.squareColor = color;
        this.presentPiece = piece;
        this.x = piece.getX();
        this.y = piece.getY();
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public boolean isOccupied()
    {
        return presentPiece != null;
    }

    public Piece getPiece()
    {
        return this.presentPiece;
    }

    public void setPiece(Piece piece)
    {
        this.presentPiece = piece;
    }

    public Piece removePiece()
    {
        Piece p = presentPiece;
        presentPiece = null;
        return p;
    }

    public Color getSquareColor()
    {
        return this.squareColor;
    }
}
