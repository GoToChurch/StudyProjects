package Chat.client;

import Chat.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BotClient extends Client {
    public static void main(String[] args) {
        BotClient bot = new BotClient();
        bot.run();
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        return "Bot_" + (int) Math.random() * 99;
    }

    public class BotSocketThread extends SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Hello there i am a bot.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            String[] strings = message.split(":");
            SimpleDateFormat format = null;
            if (strings.length != 2) {
                return;
            }

            if (strings[1].equals("date")) {
                format = new SimpleDateFormat("d.MM.yyyy");
            }
            else if (strings[1].equals("day")) {
                format = new SimpleDateFormat("d");
            }
            else if (strings[1].equals("month")) {
                format = new SimpleDateFormat("MM");
            }
            else if (strings[1].equals("year")) {
                format = new SimpleDateFormat("yyyy");
            }
            else if (strings[1].equals("time")) {
                format = new SimpleDateFormat("H:mm:ss");
            }
            else if (strings[1].equals("hour")) {
                format = new SimpleDateFormat("H");
            }
            else if (strings[1].equals("minutes")) {
                format = new SimpleDateFormat("mm");
            }
            else if (strings[1].equals("seconds")) {
                format = new SimpleDateFormat("ss");
            }

            BotClient.this.sendTextMessage("Information for " + strings[0] + ": " + format.format(Calendar.getInstance().getTime()));
        }
    }
}
