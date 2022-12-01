package archive.command;

import archive.ConsoleHelper;
import archive.ZipFileManager;
import archive.exception.PathIsNotFoundException;

import java.nio.file.Path;

public class ZipExtractCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
        ConsoleHelper.writeMessage("Preparing for extracting");
        ZipFileManager zipFileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Enter the extraction folder:");
        zipFileManager.exctractAll(Path.of(ConsoleHelper.readString()));
        ConsoleHelper.writeMessage("Extraction completed");
        }
        catch (PathIsNotFoundException e) {
            ConsoleHelper.writeMessage("You entered incorrect directory or file name");
        }
    }
}
