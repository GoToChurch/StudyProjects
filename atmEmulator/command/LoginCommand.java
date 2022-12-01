package atmEmulator.command;

import atmEmulator.ATM;
import atmEmulator.ConsoleHelper;
import atmEmulator.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command {
    private ResourceBundle validCreditCard = ResourceBundle.getBundle(ATM.class.getPackage().getName() + ".resources.verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(ATM.class.getPackage().getName() + ".resources.login_en");


    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("enter.login"));
        String login = ConsoleHelper.readString();
        ConsoleHelper.writeMessage(res.getString("enter.password"));
        String password = ConsoleHelper.readString();
        if (login.length() != 12 || password.length() != 4) {
            ConsoleHelper.writeMessage(res.getString("invalid.data"));
            execute();
        }
        if (validCreditCard.containsKey(login) && validCreditCard.getString(login).equals(password)) {
            ConsoleHelper.writeMessage(res.getString("successfull.verification.message"));
        }
        else {
            ConsoleHelper.writeMessage(res.getString("invalid.data"));
            execute();
        }
    }
}
