import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by danielmichaelson on 2/4/16.
 */
public class Board
{
    private List<List<Square>> board;
    private int size;

    public Board(int size)
    {
        this.size = size;
        board = new ArrayList<>();
        createBoard();
    }

    // Returns piece occupying x,y location - null otherwise
    public Piece getPiece(int x, int y)
    {
        return board.get(x).get(y).getPiece();
    }

    public void setPiece(Piece piece)
    {
        getSquare(piece.getX(), piece.getY()).setPiece(piece);
    }

    // Returns requested square at x,y
    public Square getSquare(int x, int y)
    {
        return board.get(x).get(y);
    }

    public List<List<Square>> getBoard()
    {
        return board;
    }

    public void setBoard(List<List<Square>> board)
    {
        this.board = board;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    // Creates a standard 8x8 chess board
    private void createBoard()
    {
        // Instantiates all squares on board
        for(int i = 0; i < size; i++)
        {
            board.add(new ArrayList<Square>());
            for(int j = 0; j < size; j++)
            {
                // Even columns have even numbered black squares
                if(i % 2 == 0)
                {
                    if(j % 2 == 0)
                    {
                        board.get(i).add(new Square(Color.BLACK, i, j));
                    }
                    else
                    {
                        board.get(i).add(new Square(Color.WHITE, i, j));
                    }
                }
                // Odd columns have even numbered white squares
                else
                {
                    if (j % 2 == 0)
                    {
                        board.get(i).add(new Square(Color.WHITE, i, j));
                    }
                    else
                    {
                        board.get(i).add(new Square(Color.BLACK, i, j));
                    }
                }
            }
        }
    }

    public void printBoard()
    {
        for(int j = size - 1; j >= 0; j--)
        {
            for(int i = 0; i < size; i++)
            {
                if(board.get(i).get(j).isOccupied())
                {
                    System.out.print(board.get(i).get(j).getPiece().toString(true));
                }
                else
                {
                    System.out.print('-');
                }
            }
            System.out.print('\n');
        }
    }

    public List<Square> getDiagSquares(int fromX, int fromY, int toX, int toY)
    {
        int diffX = toX - fromX;
        int diffY = toY - fromY;
        List<Square> squares = new ArrayList<>();
        // Not a diagonal = illegal
        if(Math.abs(diffX) != Math.abs(diffY))
        {
            return null;
        }
        if(diffX > 0 && diffY > 0)
        {
            for(int i = 1; i < diffX; i++)
            {
                squares.add(getSquare(fromX + i, fromY + i));
            }
        }
        else if(diffX > 0 && diffY < 0)
        {
            for(int i = 1; i < diffX; i++)
            {
                squares.add(getSquare(fromX + i, fromY - i));
            }
        }
        else if(diffX < 0 && diffY > 0)
        {
            for(int i = 1; i < diffY; i++)
            {
                squares.add(getSquare(fromX - i, fromY + i));
            }
        }
        else if(diffX < 0 && diffY < 0)
        {
            for(int i = 1; i < Math.abs(diffY); i++)
            {
                squares.add(getSquare(fromX - i, fromY - i));
            }
        }
        return  squares;
    }

    public List<Square> getLineSquares(int fromX, int fromY, int toX, int toY)
    {
        int diffX = toX - fromX;
        int diffY = toY - fromY;
        List<Square> squares = new ArrayList<>();

        // Not a line = illegal
        if(diffX != 0 && diffY != 0)
        {
            return null;
        }
        if(diffX > 0)
        {
            for(int i = 1; i < diffX; i++)
            {
                squares.add(getSquare(fromX + i, fromY));
            }
        }
        else if(diffX < 0)
        {
            for(int i = -1; i > diffX; i--)
            {
                squares.add(getSquare(fromX + i, fromY));
            }
        }
        else if(diffY > 0)
        {
            for(int j = 1; j < diffY; j++)
            {
                squares.add(getSquare(fromX, fromY + j));
            }
        }
        else if(diffY < 0)
        {
            for(int j = -1; j > diffY; j--)
            {
                squares.add(getSquare(fromX, fromY + j));
            }
        }
        return squares;
    }
}
