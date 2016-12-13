import java.awt.Color;

/**
 * Created by danielmichaelson on 2/1/16.
 */
public class King extends Piece
{
    public King(Color color, int x, int y)
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
        return !(Math.abs(diffX) > 1 || Math.abs(diffY) > 1);
    }

    @Override
    public String toString()
    {
        return "K";
    }
}
