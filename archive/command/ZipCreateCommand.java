package archive.command;

import archive.ConsoleHelper;
import archive.ZipFileManager;
import archive.exception.PathIsNotFoundException;

import java.nio.file.Path;

public class ZipCreateCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Archive creation");
            ZipFileManager zipFileManager = getZipFileManager();
            ConsoleHelper.writeMessage("Enter file's or directory's to zip absolute path:");
            String s = ConsoleHelper.readString();
            Path absPath = Path.of(s);
            zipFileManager.createZip(absPath);
            ConsoleHelper.writeMessage("Archive is created");
        }
        catch (PathIsNotFoundException e) {
            ConsoleHelper.writeMessage("You entered incorrect directory or file name");
        }
    }
}
