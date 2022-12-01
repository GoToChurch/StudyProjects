package atmEmulator.command;


import atmEmulator.ATM;
import atmEmulator.ConsoleHelper;
import atmEmulator.exception.CurrencyManipulator;
import atmEmulator.exception.CurrencyManipulatorFactory;
import atmEmulator.exception.InterruptOperationException;

import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(ATM.class.getPackage().getName() + ".resources.withdraw_en");

    @Override
    public void execute() throws InterruptOperationException {
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        while (true) {
            ConsoleHelper.writeMessage(res.getString("before"));
            int amount = Integer.parseInt(ConsoleHelper.readString());
            if (manipulator.isAmountAvailable(amount)) {
                Map<Integer, Integer> denominations = manipulator.withdrawAmount(amount);
                if (denominations != null) {
                    for (Integer denomination : denominations.keySet()) {
                        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), denomination, denominations.get(denomination)));
                    }
                    ConsoleHelper.writeMessage(res.getString("succes.message"));
                }
            }
            else {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                break;
            }
        }
    }
}
