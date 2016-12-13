import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


/**
 * Created by danielmichaelson on 2/9/16.
 * Initial code based on provided GUI Example code
 * (https://wiki.cites.illinois.edu/wiki/pages/viewpage.action?title=Assignment+1.1&spaceKey=cs242)
 */
public class ChessGUI
{
    private boolean firstClick = true;
    private boolean customPieces = false;
    private ArrayList<ArrayList<SquarePanel>> listOfSquares = new ArrayList<>();
    private JFrame window = new JFrame("Chess");
    private Game game;
    private int selectedX, selectedY;

    private JButton player1Button;
    private JButton player2Button;
    private JButton optionsButton;

    private JPanel player1Info;
    private JPanel player2Info;

    /**
     * Creates a default gui with the given game
     */
    public ChessGUI(Game game)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        this.game = game;
        window.setSize(600, 650);
        window.setResizable(false);
        int size = game.getBoard().getSize();
        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        window.add(drawBoardComponent(), BorderLayout.CENTER);
        labelSquares();

        createInfoSection();
        refresh();
        window.setVisible(true);
    }

    /**
     * Completely redraws all pieces on the board then updates player and score information
     */
    public void refresh()
    {
        redrawBoard();
        updatePlayers();
        updateScore();
        highlightTurn();

    }

    /**
     * Set player's button focus on respective turn
     */
    public void highlightTurn()
    {
        if(game.getPlayerByColor(game.getTurnColor()).equals(game.getPlayer1()))
        {
            player2Button.setSelected(false);
            player1Button.setSelected(true);
        }
        else
        {
            player1Button.setSelected(false);
            player2Button.setSelected(true);
        }
    }

    /**
     * Assign x,y values to all SquarePanels
     */
    private void labelSquares()
    {
        for(int i = 0; i < listOfSquares.size(); i++)
        {
            ArrayList<SquarePanel> list = listOfSquares.get(i);
            for(int j = 0; j < list.size(); j++)
            {
                list.get(j).setCoords(i, j);
            }
        }
    }

    /**
     * Creates buttons and panels for top info bar
     */
    private void createInfoSection()
    {
        player1Button = new JButton();
        player2Button = new JButton();
        optionsButton = new JButton("Options");

        player1Info = new JPanel();
        player2Info = new JPanel();

        JPanel infoSection = new JPanel();
        window.add(infoSection, BorderLayout.NORTH);

        updateScore();
        createPopUpMenus();

        infoSection.add(player1Button);
        infoSection.add(player1Info);
        infoSection.add(optionsButton);
        infoSection.add(player2Info);
        infoSection.add(player2Button);

    }

    /**
     * Updates player button text
     */
    private void updatePlayers()
    {
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        player1Button.setText(p1.toString() + " (" + game.getColorAsString(p1.getColor()) + ")");
        player2Button.setText(p2.toString() + " (" + game.getColorAsString(p2.getColor()) + ")");
    }

    /**
     * Update score label text
     */
    private void updateScore()
    {
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        JLabel p1Label = new JLabel(p1.getWins() + " / " + p1.getLosses() + " / " + p1.getTies());
        JLabel p2Label = new JLabel(p2.getWins() + " / " + p2.getLosses() + " / " + p2.getTies());
        player1Info.removeAll();
        player2Info.removeAll();

        player1Info.add(p1Label);
        player2Info.add(p2Label);
    }

    /**
     * Attaching popupmenus to jbuttons with help from
     * https://stackoverflow.com/questions/1692677/how-to-create-a-jbutton-with-a-menu
     */
    private void createPopUpMenus()
    {
        final JPopupMenu p1Menu = new JPopupMenu();
        p1Menu.add(new JMenuItem(new AbstractAction("Draw") {
            public void actionPerformed(ActionEvent e) {
                confirmDraw(game.getPlayer1());
            }
        }));
        p1Menu.add(new JMenuItem(new AbstractAction("Forfeit") {
            public void actionPerformed(ActionEvent e) {
                game.getPlayer1().addLoss();
                game.getPlayer2().addWin();
                game.newGame(customPieces, false);
                refresh();
            }
        }));
        p1Menu.add(new JMenuItem(new AbstractAction("Change Name") {
            public void actionPerformed(ActionEvent e) {
                changeName(game.getPlayer1());
            }
        }));
        player1Button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                p1Menu.show(e.getComponent(), e.getComponent().getX(), e.getComponent().getY()+20);
            }
        });

        final JPopupMenu p2Menu = new JPopupMenu();
        p2Menu.add(new JMenuItem(new AbstractAction("Draw") {
            public void actionPerformed(ActionEvent e) {
                confirmDraw(game.getPlayer2());
            }
        }));
        p2Menu.add(new JMenuItem(new AbstractAction("Forfeit") {
            public void actionPerformed(ActionEvent e) {
                game.getPlayer2().addLoss();
                game.getPlayer1().addWin();
                game.newGame(customPieces, false);
                refresh();
            }
        }));
        p2Menu.add(new JMenuItem(new AbstractAction("Change Name") {
            public void actionPerformed(ActionEvent e) {
                changeName(game.getPlayer2());
            }
        }));
        player2Button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                p2Menu.show(e.getComponent(), e.getComponent().getX()-380, e.getComponent().getY()+20);
            }
        });

        final JPopupMenu optionsMenu = new JPopupMenu();
        optionsMenu.add(new JMenuItem(new AbstractAction("Undo") {
            public void actionPerformed(ActionEvent e) {
                if(game.canUndo())
                {
                    game.undo();
                    redrawBoard();
                }
            }
        }));
        optionsMenu.add(new JMenuItem(new AbstractAction("New Game") {
            public void actionPerformed(ActionEvent e) {
                game.newGame(customPieces, false);
                refresh();
            }
        }));
        optionsMenu.add(new JMenuItem(new AbstractAction("Restart Series") {
            public void actionPerformed(ActionEvent e) {
                game.getPlayer1().setScore(0, 0, 0);
                game.getPlayer2().setScore(0, 0, 0);
                game.newGame(customPieces, false);
                refresh();
            }
        }));
        optionsMenu.add(new JMenuItem(new AbstractAction("Toggle Custom Pieces") {
            public void actionPerformed(ActionEvent e) {
                customPieces = !customPieces;
                game.newGame(customPieces, false);
                refresh();
            }
        }));
        optionsButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                optionsMenu.show(e.getComponent(), e.getComponent().getX()-300, e.getComponent().getY()+20);
            }
        });

    }

    /**
     *
     * @param player: The player who initiated the draw
     */
    private void confirmDraw(Player player)
    {
        String message = game.getColorAsString(player.getColor()) + " would like to draw. Does " +
                game.getColorAsString(game.getOpponentByColor(player.getColor()).getColor()) + " agree?";
        int reply = JOptionPane.showConfirmDialog(window, message, "Draw?", JOptionPane.YES_NO_OPTION);
        if(reply == JOptionPane.YES_OPTION)
        {
            game.getPlayer1().addTie();
            game.getPlayer2().addTie();
            game.newGame(customPieces, false);
            refresh();
        }
    }

    /**
     * Prompts for new name of player and updates buttons
     *
     * @param player:   the player whose name we're changing
     */
    private void changeName(Player player)
    {
        String name = JOptionPane.showInputDialog(window, "Please enter your new name:", "Name Change", JOptionPane.QUESTION_MESSAGE);
        if(!name.isEmpty())
        {
            player.setName(name);
            updatePlayers();
        }
    }

    private JComponent drawBoardComponent()
    {
        JPanel pnl = new JPanel(new GridLayout(8,8));
        drawBoard(pnl, game.getBoard().getSize());
        return pnl;

    }

    /**
     * This sets up listOfSquares and rearranges it to matches board's x,y coordinates (origin in bottom left).
     * This allows us to maintain List.get(X-Coord).get(Y-Coord)
     */

    private void drawBoard(JPanel panel, int size)
    {
        for(int i = 0; i < size; i++)
        {
            listOfSquares.add(new ArrayList<SquarePanel>());
        }
        for(int j = 0; j < size; j++)
        {
            for(int i = 0; i < size; i++)
            {
                final SquarePanel squarePanel = new SquarePanel(new BorderLayout());

                // Even columns have even numbered black squares
                if(i % 2 == 0)
                {
                    squarePanel.setBackground((j % 2 == 0 ? Color.BLACK : Color.WHITE));

                }
                // Odd columns have even numbered white squares
                else
                {
                    squarePanel.setBackground((j % 2 == 1 ? Color.BLACK : Color.WHITE));
                }
                squarePanel.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
                        if(e.getComponent() instanceof SquarePanel)
                        {
                            clickHandler((SquarePanel)e.getComponent());
                        }

                    }
                });
                panel.add(squarePanel);
                listOfSquares.get(i).add(squarePanel);
            }
        }
        // Reverse Y's to maintain bottom left origin
        for(int i = 0; i < size; i++)
        {
            Collections.reverse(listOfSquares.get(i));
        }
    }

    /**
     * This function handles clicks on the board
     *
     * @param square:   the SquarePanel that was clicked
     */
    private void clickHandler(SquarePanel square)
    {
        if(game.isGameOver())
        {
            return;
        }
        int x = square.getXCoord();
        int y = square.getYCoord();
        if(firstClick)
        {
            // Don't select anything that isn't our piece
            if(!game.getBoard().getSquare(x, y).isOccupied() ||
                    !game.getTurnColor().equals(game.getBoard().getPiece(x, y).getColor()))
            {
                return;
            }
            selectPiece(x, y);
            firstClick = !firstClick;
        }
        else
        {
            // Clear selection if we reselect same piece
            if(selectedX == x && selectedY == y)
            {
                updateSquarePiece(x, y);
                firstClick = !firstClick;
                return;
            }
            // Successful move
            if(game.move(selectedX, selectedY, x, y))
            {
                firstClick = !firstClick;
                updateSquarePiece(selectedX, selectedY);
                updateSquarePiece(x, y);
                highlightTurn();
                if(game.isGameOver())
                {
                    Player winner = game.getOpponentByColor(game.getTurnColor());
                    winner.addWin();
                    game.getOpponentByColor(winner.getColor()).addLoss();
                    updateScore();
                }
            }
        }

    }

    /**
     * Complete refresh of the board, redrawing currently alive pieces
     */
    public void drawAlivePieces()
    {
        clearBoard();
        List<Piece> pieces = new ArrayList<>(game.getPlayer1().getAlivePieces());
        pieces.addAll(game.getPlayer2().getAlivePieces());

        for(Piece p: pieces)
        {
            ImageIcon img = new ImageIcon("images/" + game.getColorAsString(p.getColor()) + p + ".png");
            JLabel lbl = new JLabel("", img, JLabel.CENTER);
            listOfSquares.get(p.getX()).get(p.getY()).add(lbl, BorderLayout.CENTER);
        }
        window.setVisible(true);
    }

    /**
     * Removes all borders and images from the board
     */
    private void clearBoard()
    {
        for(ArrayList<SquarePanel> list: listOfSquares)
        {
            for(SquarePanel sp: list)
            {
                sp.setBorder(BorderFactory.createEmptyBorder());
                sp.removeAll();
                sp.revalidate();
                sp.repaint();
            }
        }
    }

    private void redrawBoard()
    {
        drawAlivePieces();
    }

    /**
     * Refresh border and image for SquarePanel at (x,y)
     *
     * @param x
     * @param y
     */
    public void updateSquarePiece(int x, int y)
    {
        JPanel square = listOfSquares.get(x).get(y);
        square.setBorder(BorderFactory.createEmptyBorder());
        square.removeAll();
        square.revalidate();
        square.repaint();

        Piece p = game.getBoard().getPiece(x, y);
        if(p != null)
        {
            ImageIcon img = new ImageIcon("images/" + game.getColorAsString(p.getColor()) + p + ".png");
            JLabel lbl = new JLabel("", img, JLabel.CENTER);
            square.add(lbl, BorderLayout.CENTER);

        }

    }

    /**
     * Highlights the SquarePanel at (x,y)
     *
     * @param x
     * @param y
     */
    public void selectPiece(int x, int y)
    {
        SquarePanel currSquare = listOfSquares.get(x).get(y);

        currSquare.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
        selectedX = currSquare.getXCoord();
        selectedY = currSquare.getYCoord();
    }

}
