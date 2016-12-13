import javax.swing.*;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by danielmichaelson on 2/1/16.
 */

public class Game
{
    private Board board;
    private Player player1;
    private Player player2;
    private Color turnColor;
    private boolean gameOver;
    private CommandManager commandManager;

    public Player getPlayer2()
    {
        return player2;
    }

    public Player getPlayer1()
    {
        return player1;
    }

    public Game(boolean customPieces, boolean testing)
    {
        player1 = new Player(Color.WHITE, "Player 1");
        player2 = new Player(Color.BLACK, "Player 2");
        board = new Board(8);

        newGame(customPieces, testing);

    }

    /**
     * Resets all pieces on the board
     *
     * @param customPieces: should custom pieces be placed?
     * @param testing:  is this just a test board?
     */
    public void newGame(boolean customPieces, boolean testing)
    {
        removeAllPieces();

        commandManager = new CommandManager();
        turnColor = Color.WHITE;
        gameOver = false;
        if(!testing)
        {
            createPieces();
        }
        if(customPieces)
        {
            placeCustomPieces();
        }
        board.printBoard();
        System.out.println(getColorAsString(getTurnColor()) + "'s Turn");
    }

    /**
     * Removes all pieces from the board and players' alive lists
     */
    private void removeAllPieces()
    {
        player1.setAlivePieces(new ArrayList<Piece>());
        player2.setAlivePieces(new ArrayList<Piece>());

        for(List<Square> list: getBoard().getBoard())
        {
            for(Square sq: list)
            {
                sq.setPiece(null);
            }
        }

    }

    /**
     * Puts our custom pieces on the board
     */
    private void placeCustomPieces()
    {
        player1.removePiece(board.getSquare(3, 1).removePiece());
        player1.removePiece(board.getSquare(4, 1).removePiece());
        player2.removePiece(board.getSquare(3, 6).removePiece());
        player2.removePiece(board.getSquare(4, 6).removePiece());

        AntiPawn whiteAP = new AntiPawn(Color.WHITE, 3, 1);
        player1.addPiece(whiteAP);
        board.setPiece(whiteAP);
        whiteAP = new AntiPawn(Color.WHITE, 4, 1);
        player1.addPiece(whiteAP);
        board.setPiece(whiteAP);

        AntiPawn blackAP = new AntiPawn(Color.BLACK, 3, 6);
        player1.addPiece(blackAP);
        board.setPiece(blackAP);
        blackAP = new AntiPawn(Color.BLACK, 4, 6);
        player1.addPiece(blackAP);
        board.setPiece(blackAP);

        Wazir whiteWazir = new Wazir(Color.WHITE, 4, 2);
        player1.addPiece(whiteWazir);
        board.setPiece(whiteWazir);

        Wazir blackWazir = new Wazir(Color.BLACK, 4, 5);
        player2.addPiece(blackWazir);
        board.setPiece(blackWazir);



    }

    public void printBoard()
    {
        board.printBoard();
    }

    public boolean isGameOver()
    {
        return gameOver;
    }

    public Player getPlayerByColor(Color color)
    {
        return Color.WHITE.equals(color) ? player1 : player2;
    }

    public Player getOpponentByColor(Color color)
    {
        return Color.WHITE.equals(color) ? player2 : player1;
    }

    public Color getTurnColor()
    {
        return turnColor;
    }

    public Board getBoard()
    {
        return board;
    }

    public String getColorAsString(Color color)
    {
        return (Color.WHITE.equals(color)) ? "White" : "Black";
    }

    public void printPieces()
    {
        System.out.print(player1.getAlivePieces().toString());
        System.out.println(player1.getNumPieces());
        System.out.print(player2.getAlivePieces().toString());
        System.out.println(player2.getNumPieces());

    }

    public void createPieces()
    {
        createPieces(Color.WHITE);
        createPieces(Color.BLACK);
    }

    /**
     * Creates a standard chess setup
     *
     * @param color: the color of the pieces (Black or White)
     */
    private void createPieces(Color color)
    {
        int pawn_row = 1;
        int main_row = 0;
        if (Color.BLACK.equals(color))
        {
            pawn_row = 6;
            main_row = 7;
        }
        for (int i = 0; i < board.getSize(); i++)
        {
            Pawn newPawn = new Pawn(color, i, pawn_row);
            board.getSquare(i, pawn_row).setPiece(newPawn);
            Piece newPiece;
            switch (i)
            {
                case 0:
                case 7:
                    newPiece = new Rook(color, i, main_row);
                    break;
                case 1:
                case 6:
                    newPiece = new Knight(color, i, main_row);
                    break;
                case 2:
                case 5:
                    newPiece = new Bishop(color, i, main_row);
                    break;
                case 3:
                    newPiece = new Queen(color, i, main_row);
                    break;
                case 4:
                    newPiece = new King(color, i, main_row);
                    break;
                default:
                    newPiece = null;
            }
            board.setPiece(newPiece);
            getPlayerByColor(color).addPiece(newPiece);
            getPlayerByColor(color).addPiece(newPawn);
        }
    }

    public boolean move(int fromX, int fromY, int toX, int toY)
    {
        return move(fromX, fromY, toX, toY, false);
    }

    /**
     * Attempt to make a move
     *
     * @param fromX:    starting x location
     * @param fromY:    starting y location
     * @param toX:      ending x location
     * @param toY:      ending y location
     * @param simulated:    is this just a trial move?
     * @return  true if successful move, false if failed for any reason
     */
    public boolean move(int fromX, int fromY, int toX, int toY, boolean simulated)
    {
        int size = board.getSize() - 1;
        // Do nothing if any variables are outside of board
        if(fromX < 0 || toX < 0 || fromY < 0 || toY < 0 || fromX > size || toX > size || fromY > size || toY > size)
        {
            return false;
        }
        Piece currPiece = board.getPiece(fromX, fromY);
        // Do nothing if no piece on starting location
        if(currPiece == null)
        {
            return false;
        }
        // Don't move pieces we don't own (Don't check if we're simulating a move)
        if(!simulated && !turnColor.equals(currPiece.getColor()))
        {
            return false;
        }
        // Don't allow a move to a square we already occupy
        if(board.getSquare(toX, toY).isOccupied() && board.getPiece(toX, toY).getColor().equals(currPiece.getColor()))
        {
            return false;
        }
        // Do nothing if move is not allowed by the piece
        if (!currPiece.isValidMove(board, fromX, fromY, toX, toY))
        {
            return false;
        }
        Square toSquare = board.getSquare(toX, toY);
        Piece removedPiece = null;

        // Remove captured piece from opponents's alive list and from the board
        if (toSquare.isOccupied())
        {
            removedPiece = board.getPiece(toX, toY);
            getPlayerByColor(removedPiece.getColor()).removePiece(removedPiece);
            toSquare.removePiece();
        }

        // update's piece location internally and on board
        currPiece.setX(toX);
        currPiece.setY(toY);
        toSquare.setPiece(currPiece);
        board.getSquare(fromX, fromY).removePiece();

        // can't put yourself in check, undo everything and return false
        if(isPlayerInCheck(getPlayerByColor(currPiece.getColor())) != null)
        {
            System.out.println("IN CHECK");
            undoMove(currPiece, fromX, fromY, toX, toY, removedPiece);
            return false;
        }

        if(simulated)
        {
            undoMove(currPiece, fromX, fromY, toX, toY, removedPiece);
            return true;
        }

        // Only now do we commit to the move

        if(currPiece instanceof Pawn)
        {
            if(((Pawn) currPiece).getIsFirstMove())
            {
                ((Pawn) currPiece).setFirstMove(false);
            }
        }

        commandManager.executeCommand(new moveCommand(currPiece, fromX, fromY, toX, toY, removedPiece));


        Player opponent = getOpponentByColor(currPiece.getColor());
        Piece threat = isPlayerInCheck(opponent);
        if(threat != null)
        {
            if(isPlayerInMate(opponent, threat))
            {
                gameOver = true;
                JOptionPane.showMessageDialog(null, getColorAsString(opponent.getColor()) + " is in mate!");
            }
            else
            {
                JOptionPane.showMessageDialog(null, getColorAsString(opponent.getColor()) + " is in check!");
            }
        }

        switchTurn();
        getBoard().printBoard();
        System.out.println(getColorAsString(getTurnColor()) + "'s Turn");
        return true;

    }

    public boolean canUndo()
    {
        return commandManager.isUndoAvailable();
    }

    public void undo()
    {
        commandManager.undo();
    }

    /**
     * Checks if provided Player is in check by seeing if any of the opponent's pieces can capture our King
     */
    public Piece isPlayerInCheck(Player player)
    {
        Piece king = player.getKing();

        // This should only happen in some test cases
        if(king == null)
        {
            return null;
        }

        for(Piece p: getOpponentByColor(player.getColor()).getAlivePieces())
        {
            if(p == null){}
            else if(move(p.getX(), p.getY(), king.getX(), king.getY(), true))
            {
                return p;
            }
        }
        return null;
    }

    /**
     * Only check for mate if player is in check
     */
    public boolean isPlayerInMate(Player player, Piece threateningPiece)
    {
        King king = player.getKing();
        // Can we simply move the king out of check?
        if(canKingMove(king))
        {
            return false;
        }
        // Can we capture the threatening piece?
        for(Piece p: player.getAlivePieces())
        {
            if(move(p.getX(), p.getY(), threateningPiece.getX(), threateningPiece.getY(), true))
            {
                return false;
            }
        }

        // Can't move king and can't capture, can we intercept?
        // Can't block knights
        if(threateningPiece instanceof Knight)
        {
            return true;
        }

        int diffX = king.getX() - threateningPiece.getX();
        int diffY = king.getY() - threateningPiece.getY();

        List<Square> squares;
        // Both non-zero diffs mean we're going diagonal
        if(diffX != 0 && diffY != 0)
        {
            squares = board.getDiagSquares(king.getX(), king.getY(), threateningPiece.getX(), threateningPiece.getY());
        }
        // Otherwise we're in a line
        else
        {
            squares = board.getLineSquares(king.getX(), king.getY(), threateningPiece.getX(), threateningPiece.getY());
        }

        for(Piece p: player.getAlivePieces())
        {
            for(Square s: squares)
            {
                System.out.println("Checking Square (" + s.getX() + "," +s.getY() + ") Piece: " + p);
                if(move(p.getX(), p.getY(), s.getX(), s.getY(), true))
                {
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * Checks whether or not the provided King has a valid move (to any adjacent square)
     */
    public boolean canKingMove(King king)
    {
        int currX = king.getX();
        int currY = king.getY();
        return (move(currX, currY, currX+1, currY, true) || move(currX, currY, currX+1, currY+1, true)
                || move(currX, currY, currX, currY+1, true) || move(currX, currY, currX-1, currY+1, true)
                || move(currX, currY, currX-1, currY, true) || move(currX, currY, currX-1, currY-1, true)
                || move(currX, currY, currX, currY-1, true));
    }

    public void switchTurn()
    {
        turnColor = (turnColor.equals(Color.WHITE) ? Color.BLACK : Color.WHITE);
    }


    /**
     * Places currPiece back on from location and returns capturedPiece to board and opponent's alive list
     *
     * @param currPiece:    The piece that made the move
     * @param fromX:        X coord currPiece came from
     * @param fromY:        Y coord currPiece came from
     * @param toX:          X coord we moved to
     * @param toY:          Y coord we moved to
     * @param capturedPiece:    the piece we captured at (toX, toY) if it exists, null otherwise
     */
    public void undoMove(Piece currPiece, int fromX, int fromY, int toX, int toY, Piece capturedPiece)
    {
        Player currPlayer = getPlayerByColor(currPiece.getColor());
        if(capturedPiece != null)
        {
            getOpponentByColor(currPlayer.getColor()).addPiece(capturedPiece);
        }
        board.getSquare(toX, toY).setPiece(capturedPiece);
        board.getSquare(fromX, fromY).setPiece(currPiece);
        currPiece.setX(fromX);
        currPiece.setY(fromY);
    }

    private class moveCommand implements Command
    {
        private Piece movedPiece, capturedPiece;
        private int fromX, fromY, toX, toY;

        public moveCommand(Piece movedPiece, int fromX, int fromY, int toX, int toY, Piece capturedPiece)
        {
            this.movedPiece = movedPiece;
            this.capturedPiece = capturedPiece;
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }


        @Override
        public void execute()
        {

        }

        @Override
        public void undo()
        {
            undoMove(movedPiece, fromX, fromY, toX, toY, capturedPiece);
            switchTurn();

        }
    }
}

