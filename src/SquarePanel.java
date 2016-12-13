import javax.swing.*;
import java.awt.*;

/**
 * Created by danielmichaelson on 2/17/16.
 */
public class SquarePanel extends JPanel
{
    private static boolean secondClick = false;

    private int xCoord;
    private int yCoord;

    public int getXCoord()
    {
        return xCoord;
    }

    public void setXCoord(int x)
    {
        this.xCoord= x;
    }

    public int getYCoord()
    {
        return yCoord;
    }

    public void setYCoord(int y)
    {
        this.yCoord = y;
    }

    public void setCoords(int x, int y)
    {
        this.xCoord = x;
        this.yCoord = y;
    }

    SquarePanel(LayoutManager layout)
    {
        super(layout);
        this.xCoord = -1;
        this.yCoord = -1;
    }

    SquarePanel(LayoutManager layout, int x, int y)
    {
        super(layout);
        this.xCoord = x;
        this.yCoord = y;
    }

}
