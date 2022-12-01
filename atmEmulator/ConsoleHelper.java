package atmEmulator;

import atmEmulator.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(ATM.class.getPackage().getName() + ".resources.common_en");

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        writeMessage(res.getString("get.currency.code"));
        String currnecyCode = readString();

        if (currnecyCode.length() != 3) {
            writeMessage(res.getString("try.again"));
        }
        else {
            return currnecyCode.toUpperCase();
        }
        return askCurrencyCode();
    }

    public static Operation askOperation() {
        try {
            writeMessage(res.getString("choose.operation"));
            int operationCode = Integer.parseInt(readString());

            return Operation.getAllowableOperationByOrdinal(operationCode);
        } catch (Exception e) {
            e.printStackTrace();
            writeMessage(res.getString("try.again"));
            askOperation();
        }
        return null;
    }

    public static String[] getValidTwoDigits(String currencyCode) {
        try {
            writeMessage(res.getString("get.denomination.and.amount"));
            String twoDigits = readString();
            return twoDigits.split(" ");
        } catch (Exception e) {
            getValidTwoDigits(currencyCode);
        }

        return null;
    }


    public static String readString() throws InterruptOperationException {
        try {
            String input = bufferedReader.readLine();
            if (input.toLowerCase().equals("exit")) {
                throw new InterruptOperationException();
            }
            return input;
        } catch (IOException e) {
            System.out.println(res.getString("oops"));
        }
        return null;
    }
}
