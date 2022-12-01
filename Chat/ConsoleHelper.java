package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            System.out.println("Try again");
            readString();
        }
        return null;
    }

    public static int readInt() {
        try {
            return Integer.parseInt(readString());
        }
        catch (NumberFormatException e) {
            System.out.println("Try again");
            readInt();
        }
        return 0;
    }

}
