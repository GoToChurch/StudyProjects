package archive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleHelper {
    private static final BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws IOException {
        return scanner.readLine();
    }

    public static int readInt() throws IOException {
        return Integer.parseInt(scanner.readLine());
    }

    public static void close() throws IOException {
        scanner.close();
    }
}
