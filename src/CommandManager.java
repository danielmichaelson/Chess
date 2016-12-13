/**
 * Created by danielmichaelson on 2/18/16.
 */
public class CommandManager
{
    private Command lastCommand;

    public CommandManager()
    {

    }

    public void executeCommand(Command c) {
        c.execute();
        lastCommand = c;
    }

    public boolean isUndoAvailable() {
        return lastCommand != null;
    }

    public void undo() {
        assert(lastCommand != null);
        lastCommand.undo();
        lastCommand = null;
    }
}
