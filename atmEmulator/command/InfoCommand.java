package atmEmulator.command;

import atmEmulator.ATM;
import atmEmulator.ConsoleHelper;
import atmEmulator.exception.CurrencyManipulator;
import atmEmulator.exception.CurrencyManipulatorFactory;

import java.util.ResourceBundle;

class InfoCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(ATM .class.getPackage().getName() + ".resources.info_en");

    @Override
    public void execute() {
        ConsoleHelper.writeMessage(res.getString("before"));
        boolean hasMoney = false;
        for (CurrencyManipulator manipulator : CurrencyManipulatorFactory.getAllCurrencyManipulators()) {
            if (manipulator.hasMoney()) {
                hasMoney = true;
                ConsoleHelper.writeMessage(
                        String.format("%s - %d", manipulator.getCurrencyCode(), manipulator.getTotalAmount())
                );
            }
        }
        if (!hasMoney) {
            ConsoleHelper.writeMessage(res.getString("no.money"));
        }
    }
}
