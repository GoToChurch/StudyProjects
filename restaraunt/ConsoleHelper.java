package restaraunt;

import restaraunt.kitchen.Dish;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public static List<Dish> getAllDishesForOrder() throws IOException {
        String order = "";
        List<Dish> fullOrder = new ArrayList<>();
        writeMessage("Make your odrer. To exit type 'exit'");
        writeMessage(Dish.allDishesToString());
        while (true) {
            order = readString();
            if (order.toLowerCase().equals("exit")) {
                break;
            }
            try {
                fullOrder.add(Dish.valueOf(order.toUpperCase(Locale.ROOT)));
                writeMessage(order + " has been added to your order");
            } catch (Exception e) {
                writeMessage(order + " is not on menu");
            }
        }
        return fullOrder;
    }
}
