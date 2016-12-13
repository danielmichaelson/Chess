import java.awt.Color;

/**
 * Created by danielmichaelson on 2/1/16.
 */

public class Pawn extends Piece
{
    boolean isFirstMove;

    public Pawn(Color color, int x, int y)
    {
        super.setColor(color);
        super.setX(x);
        super.setY(y);
        this.isFirstMove = true;
    }

    public void setFirstMove(boolean bool)
    {
        isFirstMove = bool;
    }

    public boolean getIsFirstMove()
    {
        return isFirstMove;
    }

    @Override
    public boolean isValidMove(Board board, int fromX, int fromY, int toX, int toY)
    {
        int diffX = toX - fromX;
        int diffY = toY - fromY;
        int direction = Color.WHITE.equals(super.getColor()) ? 1 : -1;
        // Let the pawn move forward 2 spaces on first move

        if(isFirstMove && diffY == direction*2 && diffX == 0)
        {
            // Disallow if either square in front of pawn is occupied
            return !(board.getSquare(toX, toY - direction).isOccupied() || board.getSquare(toX, toY).isOccupied());
        }
        // Only allow diagonal capture
        if((diffX == 1 || diffX == -1) && diffY == direction && board.getSquare(toX, toY).isOccupied())
        {
            return !board.getPiece(fromX, fromY).getColor().equals(board.getPiece(toX, toY).getColor());
        }
        return diffX == 0 && diffY == direction && !board.getSquare(toX, toY).isOccupied();

    }

    @Override
    public String toString()
    {
        return "P";
    }
}
