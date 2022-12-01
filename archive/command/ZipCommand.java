package archive.command;

import archive.ConsoleHelper;
import archive.ZipFileManager;

import java.nio.file.Path;

public abstract class ZipCommand implements Command {
    protected ZipFileManager getZipFileManager() throws Exception {
        ConsoleHelper.writeMessage("Enter archive absolute path:");
        String s = ConsoleHelper.readString();
        Path archivePath = Path.of(s);
        return new ZipFileManager(archivePath);
    }
}
