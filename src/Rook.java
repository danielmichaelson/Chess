import java.awt.Color;

/**
 * Created by danielmichaelson on 2/1/16.
 */
public class Rook extends Piece
{
    public Rook(Color color, int x, int y)
    {
        super.setColor(color);
        super.setX(x);
        super.setY(y);
    }

    @Override
    boolean isValidMove(Board board, int fromX, int fromY, int toX, int toY)
    {
        int diffX = toX - fromX;
        int diffY = toY - fromY;
        if((diffX == 0 && diffY != 0) || (diffX != 0 && diffY == 0))
        {
            if(!this.anyLineCollisions(board, toX, toY))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString()
    {
        return "R";
    }
}
