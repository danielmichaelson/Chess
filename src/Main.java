import java.util.Scanner;

/**
 * Created by danielmichaelson on 2/8/16.
 */
public class Main
{
    public static void main(String args[])
    {
        Game game = new Game(false, false);
        Scanner console = new Scanner(System.in);
        String input;

        ChessGUI gui = new ChessGUI(game);

        while(true)
        {
            input = console.next();
            if(input == null || input.equals("\n"))
            {
                continue;
            }
            if(input.equals("exit"))
            {
                gui.drawAlivePieces();
            }
            if(input.equals("print"))
            {
                game.getBoard().printBoard();
                game.printPieces();
                continue;
            }
            game.move(Character.getNumericValue(input.charAt(0)), Character.getNumericValue(input.charAt(1)),
                    Character.getNumericValue(input.charAt(2)), Character.getNumericValue(input.charAt(3)));
            gui.refresh();
            // Use "current" turn color since move switches turn
            if(game.isPlayerInCheck(game.getPlayerByColor(game.getTurnColor())) != null)
            {
                System.out.println(game.getColorAsString(game.getTurnColor()) + " is in Check!");
            }
        }

    }
}
