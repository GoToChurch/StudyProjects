package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ConsoleHelper.writeMessage("Write port number: ");
        int port = ConsoleHelper.readInt();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is on fire");
            while (true) {
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                handler.start();
            }
        } catch (Exception e) {
            ConsoleHelper.writeMessage("An error occured");
        }

    }

    public static void sendBroadcastMessage(Message message) {
        for (Connection connection : connectionMap.values()) {
            try {
                connection.send(message);
            } catch (IOException e) {
                System.out.println("Can't send your message");
            }
        }
    }

    private static class Handler extends Thread {
        private Socket socket;

        public Handler (Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            ConsoleHelper.writeMessage("Connected to " + socket.getRemoteSocketAddress());
            String username = null;
            try (Connection connection = new Connection(socket)) {
                username = serverHandshake(connection);
                sendBroadcastMessage(new Message(MessageType.USER_ADDED, username));
                notifyUsers(connection, username);
                serverMainLoop(connection, username);
            } catch (IOException | ClassNotFoundException e) {
                ConsoleHelper.writeMessage("Oops");
            }

            if (username != null) {
                connectionMap.remove(username);
                sendBroadcastMessage(new Message(MessageType.USER_REMOVED, username));
            }
            ConsoleHelper.writeMessage("Connection is closed");
        }

        private String serverHandshake(Connection connection) throws IOException, ClassNotFoundException {
            while (true) {
            connection.send(new Message(MessageType.NAME_REQUEST));
            Message message = connection.recieve();

            if (message.getType() != MessageType.USER_NAME) {
                ConsoleHelper.writeMessage("Message type is incorrect. Try again");
                serverHandshake(connection);
            }

            String clientName = message.getData();

            if (clientName.isEmpty()) {
                ConsoleHelper.writeMessage("Username is empty. Try again");
                serverHandshake(connection);
            }

            if (connectionMap.containsKey(clientName)) {
                ConsoleHelper.writeMessage("This user is already connected. Try again");
                serverHandshake(connection);
            }

            connectionMap.put(clientName, connection);
            connection.send(new Message(MessageType.NAME_ACCEPTED));
            return clientName;
            }
        }

        private void notifyUsers(Connection connection, String userName) throws IOException {
            for (String user : connectionMap.keySet()) {
                if (!user.equals(userName)) {
                    Message message = new Message(MessageType.USER_ADDED, user);
                }
            }
        }

        private void serverMainLoop(Connection connection, String userName) throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.recieve();
                if (message.getType() == MessageType.TEXT) {
                    Message textMessage = new Message(MessageType.TEXT, String.format("%s: %s%n",userName, message.getData()));
                    sendBroadcastMessage(textMessage);
                }
                else {
                    ConsoleHelper.writeMessage("Your message is not a text message");
                }
            }
        }
    }

}
