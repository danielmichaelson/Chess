import java.awt.*;

/**
 * Created by danielmichaelson on 2/11/16.
 */
public class Wazir extends Piece
{
    public Wazir(Color color, int x, int y)
    {
        super.setColor(color);
        super.setX(x);
        super.setY(y);
    }

    @Override
    public String toString()
    {
        return "W";
    }

    @Override
    boolean isValidMove(Board board, int fromX, int fromY, int toX, int toY)
    {
        int diffX = toX - fromX;
        int diffY = toY - fromY;

        // Only allow if destination is 1 square orthogonally
        return (Math.abs(diffX) + Math.abs(diffY) == 1);


    }
}
