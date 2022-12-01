package archive.command;

import archive.ConsoleHelper;
import archive.ZipFileManager;
import archive.exception.PathIsNotFoundException;

import java.nio.file.Path;

public class ZipRemoveCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Preparing for removing some files from archive");
        ZipFileManager zipFileManager = getZipFileManager();
        ConsoleHelper.writeMessage("What file do you prefer to remove?");
        zipFileManager.removeFile(Path.of(ConsoleHelper.readString()));
        ConsoleHelper.writeMessage("Removing completed");
    }
}
