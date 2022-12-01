package archive.command;

import archive.ConsoleHelper;
import archive.FileProperties;
import archive.ZipFileManager;

import java.util.List;

public class ZipContentCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Content of archive watchmode");
        ZipFileManager zipFileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Content of archive:");
        zipFileManager.getFilesList().forEach(System.out::println);
        ConsoleHelper.writeMessage("That's it");
    }
}
