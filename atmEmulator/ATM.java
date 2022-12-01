package atmEmulator;


import atmEmulator.command.CommandExecutor;
import atmEmulator.exception.InterruptOperationException;

public class ATM {
    public static void main(String[] args) {
        Operation operation = null;
        try {
            CommandExecutor.execute(Operation.LOGIN);
        } catch (InterruptOperationException e) {
            e.printStackTrace();
        }
        do {
            try {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            } catch (InterruptOperationException e) {
                ConsoleHelper.writeMessage("Good bye! I will miss you :(");
            }
        }
        while (operation != Operation.EXIT);
    }
}
