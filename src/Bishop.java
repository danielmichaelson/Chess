import java.awt.Color;

/**
 * Created by danielmichaelson on 2/1/16.
 */
public class Bishop extends Piece
{
    public Bishop(Color color, int x, int y)
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

        // Not a diagonal = illegal
        if(Math.abs(diffX) != Math.abs(diffY))
        {
            return false;
        }
        // Check for collisions along the way
        return !this.anyDiagCollisions(board, toX, toY);

    }

    @Override
    public String toString()
    {
        return "B";
    }


}
