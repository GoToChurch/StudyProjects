package miniserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

public class Get {
    public static void main(String[] args) {
        try {
            getSite(new URL("http://javarush.ru/social.html"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void getSite(URL url) {
        String server = url.getHost();
        String path = url.getPath();

        try (Socket socket = new Socket(server, 80)) {
            PrintStream printStream = new PrintStream(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            printStream.println("GET " + path);
            printStream.println();

            String line = bufferedReader.readLine();
            while (line != null) {
                System.out.println(line);
                line = bufferedReader.readLine();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
