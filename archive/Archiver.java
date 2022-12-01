package archive;


import archive.exception.WrongZipFileException;

import java.nio.file.Path;
import java.util.Scanner;

public class Archiver {
    public static Operation operation;

    public static void main(String[] args) throws Exception {

        while (operation != Operation.EXIT) {
            try {
                operation = askOperation();
                CommandExecutor.execute(operation);
            }
            catch (WrongZipFileException e) {
                ConsoleHelper.writeMessage("Chosen archive file doesn't exist");
            }
        }

        ConsoleHelper.close();
    }

    public static Operation askOperation() {
        for (Operation operation : Operation.values()) {
            ConsoleHelper.writeMessage(operation.ordinal() + " - " + operation.getName());
        }
        ConsoleHelper.writeMessage("What operation do you want to execute?");
        try {
            return Operation.values()[ConsoleHelper.readInt()];
        }
        catch (Exception e) {
            ConsoleHelper.writeMessage("We don't support this operation");
        }

        return null;
    }
}
