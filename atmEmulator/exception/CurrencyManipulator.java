package atmEmulator.exception;

import atmEmulator.ConsoleHelper;

import java.util.*;

public class CurrencyManipulator {
    private String currencyCode;
    private final Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (!denominations.containsKey(denomination)) {
            denominations.put(denomination, count);
        }
        else {
            denominations.put(denomination, denominations.get(denomination) + count);
        }
    }

    public int getTotalAmount() {
        int sum = 0;

        for (int cash : denominations.keySet()) {
            sum += cash * denominations.get(cash);
        }

        return sum;
    }

    public int getTotalAmount(Map<Integer, Integer> map) {
        int sum = 0;

        for (int cash : map.keySet()) {
            sum += cash * map.get(cash);
        }

        return sum;
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return getTotalAmount() >= expectedAmount;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) {
        Map<Integer, Integer> result = new HashMap<>();

        List<Integer> allDenominations = createAllDenominationsList();

        if (checkIfGivenAmountIsAvailableToWithdraw(expectedAmount, 0, allDenominations)) {
            for (Integer denomination : allDenominations) {
                if (expectedAmount >= denomination && denominations.containsKey(denomination) && getTotalAmount(result) <= expectedAmount) {
                    if (getTotalAmount(result) + denomination <= expectedAmount) {
                        if (!result.containsKey(denomination)) {
                            result.put(denomination, 1);
                        }
                        else {
                            result.put(denomination, result.get(denomination) + 1);
                        }
                        denominations.put(denomination, denominations.get(denomination) - 1);
                        checkIfDenomitionIsEmpty(denomination);
                    }
                }
            }
            return result;
        }
        else {
            ConsoleHelper.writeMessage("This amount is not available to withdraw");
            return null;
        }
    }


    private void checkIfDenomitionIsEmpty(int denomination) {
        if (denominations.get(denomination) == 0) {
            denominations.remove(denomination);
        }
    }

    private boolean checkIfGivenAmountIsAvailableToWithdraw(int expectedAmount, int sum, List<Integer> denominations) {
        for (Integer integer : denominations) {
            if (expectedAmount >= integer && sum <= expectedAmount) {
                sum += integer;
                //System.out.println(String.format("Expected amount: %d, integer now: %d, sum: %d", expectedAmount, integer, sum));
                if (sum > expectedAmount) {
                    sum -= integer;
                }
                checkIfGivenAmountIsAvailableToWithdraw(expectedAmount - integer, sum, denominations);
            }
        }
        return sum == expectedAmount;
    }

    private List<Integer> createAllDenominationsList() {
        List<Integer> allDenominations = new ArrayList<>();

        for (Integer denomination : denominations.keySet()) {
            for (int i = 0; i < denominations.get(denomination); i++) {
                allDenominations.add(denomination);
            }
        }

        Collections.sort(allDenominations, Collections.reverseOrder());

        return allDenominations;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public boolean hasMoney() {
        return getTotalAmount() > 0;
    }
}
