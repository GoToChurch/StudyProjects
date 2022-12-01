package archive;

import archive.exception.PathIsNotFoundException;
import archive.exception.WrongZipFileException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileManager {
    private final Path zipFile;

    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }

    public void createZip(Path source) throws Exception {
        Path zipDirectory = zipFile.getParent();
        if (Files.notExists(zipDirectory)) {
            Files.createDirectories(zipDirectory);
        }
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile));) {
            if (Files.isRegularFile(source)) {
                addNewZipEntry(zipOutputStream,source.getParent(), source.getFileName());
            }
            else if (Files.isDirectory(source)) {
                FileManager fileManager = new FileManager(source);
                List<Path> fileNames = fileManager.getFileList();
                for (Path file : fileNames) {
                    addNewZipEntry(zipOutputStream, source, file);
                }
            }
            else {
                throw new PathIsNotFoundException();
            }
        }
    }

    private void addNewZipEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws Exception {
        try(InputStream inputStream = Files.newInputStream(filePath.resolve(fileName))) {
            ZipEntry zipEntry = new ZipEntry(fileName.toString());
            zipOutputStream.putNextEntry(zipEntry);
            copyData(inputStream, zipOutputStream);
        }
        zipOutputStream.closeEntry();
    }

    private void copyData(InputStream in, OutputStream out) throws IOException {
        while (in.available() > 0) {
            out.write(in.read());
        }
    }

    public List<FileProperties> getFilesList() throws Exception {
        if (!Files.isRegularFile(zipFile)) {
            throw new WrongZipFileException();
        }

        List<FileProperties> fileProperties = new ArrayList<>();
        try(ZipInputStream in = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry zipEntry = in.getNextEntry();
            while (zipEntry != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                copyData(in, byteArrayOutputStream);
                FileProperties fileProperty = new FileProperties(zipEntry.getName(), zipEntry.getSize(), zipEntry.getCompressedSize(), zipEntry.getMethod());
                fileProperties.add(fileProperty);
                zipEntry = in.getNextEntry();
                byteArrayOutputStream.close();
            }
        }

        return fileProperties;
    }

    public void exctractAll(Path outputFolder) throws Exception {
        if (Files.notExists(zipFile)) {
            throw new WrongZipFileException();
        }

        if (Files.notExists(outputFolder)) {
            Files.createDirectories(outputFolder);
        }

        try(ZipInputStream in = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry zipEntry = in.getNextEntry();
            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                Path fileFullName = outputFolder.resolve(fileName);

                Path parent = fileFullName.getParent();
                if (Files.notExists(parent)) {
                    Files.createDirectories(parent);
                }

                try(OutputStream out = Files.newOutputStream(fileFullName)) {
                    copyData(in, out);
                }
                zipEntry = in.getNextEntry();
            }
        }
    }

    public void removeFiles(List<Path> pathList) throws Exception {
        if (Files.notExists(zipFile)) {
            throw new WrongZipFileException();
        }

        Path tempZipFile = Files.createTempFile(null, null);

        try(ZipInputStream in = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry zipEntry = in.getNextEntry();
            while (zipEntry != null) {
                if (pathList.contains(Path.of(zipEntry.getName()))) {
                    ConsoleHelper.writeMessage(zipEntry.getName() + " was deleted");
                }
                else {
                    try(ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(tempZipFile))) {
                        out.putNextEntry(new ZipEntry(zipEntry.getName()));
                        copyData(in, out);
                        in.closeEntry();
                        out.closeEntry();
                    }
                }
                zipEntry = in.getNextEntry();
            }
        }

        Files.move(tempZipFile, zipFile, StandardCopyOption.REPLACE_EXISTING);
    }

    public void removeFile(Path path) throws Exception {
        removeFiles(Collections.singletonList(path));
    }

    public void addFiles(List<Path> pathList) throws Exception {
        if (Files.notExists(zipFile)) {
            throw new WrongZipFileException();
        }

        Path tempZipFile = Files.createTempFile(null, null);
        List<Path> filesInArchive = new ArrayList<>();

        try(ZipInputStream in = new ZipInputStream(Files.newInputStream(zipFile));
            ZipOutputStream out = new ZipOutputStream(Files.newOutputStream(tempZipFile))) {
            ZipEntry zipEntry = in.getNextEntry();
            while (zipEntry != null) {
                filesInArchive.add(Path.of(zipEntry.getName()));
                out.putNextEntry(new ZipEntry(zipEntry.getName()));
                copyData(in, out);
                in.closeEntry();
                out.closeEntry();

                zipEntry = in.getNextEntry();
            }

            for (Path file : pathList) {
                if (!Files.isRegularFile(file) || Files.notExists(file)) {
                    throw new PathIsNotFoundException();
                }

                if (!filesInArchive.contains(file)) {
                    addNewZipEntry(out, file.getParent(), file.getFileName());
                }
                else {
                    ConsoleHelper.writeMessage("File already in archive");
                }
            }

            Files.move(tempZipFile, zipFile, StandardCopyOption.REPLACE_EXISTING);
        }

    }

    public void addFile(Path path) throws Exception {
        addFiles(Collections.singletonList(path));
    }
}
