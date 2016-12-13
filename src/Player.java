import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by danielmichaelson on 2/4/16.
 */
public class Player
{
    private Color color;
    private List<Piece> alivePieces;
    private String name;

    private int wins = 0;
    private int losses = 0;
    private int ties = 0;

    public int getWins()
    {
        return wins;
    }

    public void setWins(int wins)
    {
        this.wins = wins;
    }

    public void addWin()
    {
        wins++;
    }

    public int getLosses()
    {
        return losses;
    }

    public void setLosses(int losses)
    {
        this.losses = losses;
    }

    public void addLoss()
    {
        losses++;
    }

    public int getTies()
    {
        return ties;
    }

    public void setTies(int ties)
    {
        this.ties = ties;
    }

    public void addTie()
    {
        ties++;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Player(Color color, String name)
    {
        this.color = color;
        this.alivePieces = new ArrayList<>();
        this.name = name;
    }

    public Player(Color color, List<Piece> alivePieces, String name)
    {
        this.color = color;
        this.alivePieces = alivePieces;
        this.name = name;
    }

    public List<Piece> getAlivePieces()
    {
        return alivePieces;
    }

    public void setAlivePieces(List<Piece> alivePieces)
    {
        this.alivePieces = alivePieces;
    }

    public void addPiece(Piece piece)
    {
        alivePieces.add(piece);
    }

    // Removes the provided piece from the player's alivePieces list
    public void removePiece(Piece piece)
    {
        Iterator<Piece> it = alivePieces.iterator();
        while(it.hasNext())
        {
            Piece tempPiece = it.next();
            if(piece.equals(tempPiece))
            {
                it.remove();
                break;
            }
        }
    }

    public int getNumPieces()
    {
        return alivePieces.size();
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    // Returns this players King piece; null otherwise (should only be null in some testing scenarios)
    public King getKing()
    {
        King myKing = null;
        for(Piece p: alivePieces)
        {
            if(p instanceof King)
            {
                myKing = (King)p;
                break;
            }
        }
        return myKing;
    }

    public String toString()
    {
        return this.name;
    }

    public boolean equals(Player other)
    {
        return this.name.equals(other.getName()) && this.color.equals(other.getColor());
    }

    public void setScore(int w, int l, int t)
    {
        this.wins = w;
        this.losses = l;
        this.ties = t;
    }
}
