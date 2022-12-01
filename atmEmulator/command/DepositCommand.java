package atmEmulator.command;

import atmEmulator.ATM;
import atmEmulator.ConsoleHelper;
import atmEmulator.exception.CurrencyManipulator;
import atmEmulator.exception.CurrencyManipulatorFactory;
import atmEmulator.exception.InterruptOperationException;

import java.util.ResourceBundle;

class DepositCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(ATM.class.getPackage().getName() + ".resources.deposit_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(ConsoleHelper.askCurrencyCode());
        String[] twoDigits = ConsoleHelper.getValidTwoDigits(currencyManipulator.getCurrencyCode());
        currencyManipulator.addAmount(Integer.parseInt(twoDigits[0]), Integer.parseInt(twoDigits[1]));
        ConsoleHelper.writeMessage(res.getString("success.string"));
    }
}
