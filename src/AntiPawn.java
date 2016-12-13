import java.awt.*;

/**
 * Created by danielmichaelson on 2/10/16.
 */
public class AntiPawn extends Pawn
{
    public AntiPawn(Color color, int x, int y)
    {
        super(color, x, y);
    }


    @Override
    public String toString()
    {
        return "A";
    }

    @Override
    public boolean isValidMove(Board board, int fromX, int fromY, int toX, int toY)
    {
        int diffX = toX - fromX;
        int diffY = toY - fromY;
        int direction = Color.WHITE.equals(super.getColor()) ? 1 : -1;

        if(isFirstMove && diffY == direction*2 && Math.abs(diffX) == 2)
        {
            // Disallow if either square in diagonal path of movement is occupied
            if(diffX > 0)
            {
                return !(board.getSquare(toX - 1, toY - direction).isOccupied() || board.getSquare(toX, toY).isOccupied());
            }
            else
            {
                return !(board.getSquare(toX + 1, toY - direction).isOccupied() || board.getSquare(toX, toY).isOccupied());
            }
        }
        // Only allow forward capture
        if(diffX == 0 && diffY == direction && board.getSquare(toX, toY).isOccupied())
        {
            return !board.getPiece(fromX, fromY).getColor().equals(board.getPiece(toX, toY).getColor());
        }
        // allow standard move: 1 diagonal
        return Math.abs(diffX) == 1 && diffY == direction && !board.getSquare(toX, toY).isOccupied();

    }
}
