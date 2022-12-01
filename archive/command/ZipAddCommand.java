package archive.command;

import archive.ConsoleHelper;
import archive.ZipFileManager;
import archive.exception.PathIsNotFoundException;

import java.nio.file.Path;

public class ZipAddCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Preparing for add some files in archive");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("What file do you want to add in archive?:");
            zipFileManager.addFile(Path.of(ConsoleHelper.readString()));
            ConsoleHelper.writeMessage("File was added");
        }
        catch (PathIsNotFoundException e) {
            ConsoleHelper.writeMessage("You entered incorrect directory or file name");
        }
    }
}
