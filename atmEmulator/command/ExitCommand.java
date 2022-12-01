package atmEmulator.command;

import atmEmulator.ATM;
import atmEmulator.ConsoleHelper;
import atmEmulator.exception.InterruptOperationException;

import java.util.ResourceBundle;

class ExitCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(ATM.class.getPackage().getName() + ".resources.exit_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("exit.question"));
        String response = ConsoleHelper.readString();
        switch (response.toLowerCase()) {
            case "y" -> ConsoleHelper.writeMessage(res.getString("exit.message"));
            case "n" -> ConsoleHelper.writeMessage("");
            default -> execute();
        }
    }
}
