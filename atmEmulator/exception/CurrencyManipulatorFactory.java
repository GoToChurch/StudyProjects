package atmEmulator.exception;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulatorFactory {
    private static final Map<String, CurrencyManipulator> currencyManipulators = new HashMap<>();

    private CurrencyManipulatorFactory() {

    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) {

        if (!currencyManipulators.containsKey(currencyCode)) {
            currencyManipulators.put(currencyCode, new CurrencyManipulator(currencyCode));
        }

        return currencyManipulators.get(currencyCode);
    }

    public static Collection<CurrencyManipulator> getAllCurrencyManipulators() {
        return currencyManipulators.values();
    }
}
