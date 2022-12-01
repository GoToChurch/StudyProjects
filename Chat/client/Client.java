package Chat.client;

import Chat.Connection;
import Chat.ConsoleHelper;
import Chat.Message;
import Chat.MessageType;

import java.io.IOException;
import java.net.Socket;

public class Client {
    protected Connection connection;
    private volatile boolean clientConnected = false;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    protected String getServerAdress() {
        ConsoleHelper.writeMessage("Enter server adress: ");
        return ConsoleHelper.readString();
    }

    protected int getServerPort() {
        ConsoleHelper.writeMessage("Enter server port");
        return ConsoleHelper.readInt();
    }

    protected String getUserName() {
        ConsoleHelper.writeMessage("Enter your name: ");
        return ConsoleHelper.readString();
    }

    protected boolean shouldSendTextFromConsole() {
        return true;
    }

    protected SocketThread getSocketThread() {
        return new SocketThread();
    }

    public void sendTextMessage(String text) {
        try {
            connection.send(new Message(MessageType.TEXT, text));
        } catch (IOException e) {
            ConsoleHelper.writeMessage("ERROR!");
            clientConnected = false;
        }
    }

    public void run() {
        SocketThread socketThread = getSocketThread();
        socketThread.setDaemon(true);
        socketThread.start();
        try {
            synchronized (this) {
                wait();
            }
        } catch (InterruptedException e) {
            ConsoleHelper.writeMessage("Connection was interrupted");
        }
        if (clientConnected) {
            ConsoleHelper.writeMessage("Connection is succesfully established. To exit type 'exit' in console.");
        }
        else {
            ConsoleHelper.writeMessage("Connection is failed");
        }
        while (clientConnected) {
            String clientMessage = ConsoleHelper.readString();
            if (clientMessage.toLowerCase().equals("exit")) {
                break;
            }
            if (shouldSendTextFromConsole()) {
                sendTextMessage(clientMessage);
            }
        }
    }

    public class SocketThread extends Thread {
        public void run() {
            try (Socket socket = new Socket(getServerAdress(), getServerPort())) {
                Connection connection = new Connection(socket);
                clientHandshake();
                clientMainLoop();
            } catch (IOException | ClassNotFoundException e) {
                notifyConnectionStatusChanged(false);
            }

        }
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
        }

        protected void informAboutAddingNewUser(String userName) {
            ConsoleHelper.writeMessage(String.format("%s is connected to chat", userName));
        }

        protected void informAboutDeletingNewUser(String userName) {
            ConsoleHelper.writeMessage(String.format("%s leaved from chat", userName));
        }

        protected void notifyConnectionStatusChanged(boolean client) {
            clientConnected = client;
            synchronized (Client.this) {
                Client.this.notify();
            }
        }

        protected void clientHandshake() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.recieve();
                switch (message.getType()) {
                    case NAME_REQUEST -> {
                        String userName = getUserName();
                        connection.send(new Message(MessageType.USER_NAME, userName));
                    }
                    case NAME_ACCEPTED -> {
                        notifyConnectionStatusChanged(true);
                    }
                }

                if (this.isInterrupted()) {
                    break;
                }
            }
        }

        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            while (true) {
                Message message = connection.recieve();
                switch (message.getType()) {
                    case TEXT -> {
                        processIncomingMessage(message.getData());
                    }
                    case USER_ADDED -> {
                        informAboutAddingNewUser(message.getData());
                    }
                    case USER_REMOVED -> {
                        informAboutDeletingNewUser(message.getData());
                    }
                }

                if (this.isInterrupted()) {
                    break;
                }
            }
        }
    }
}
