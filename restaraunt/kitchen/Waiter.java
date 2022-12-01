package restaraunt.kitchen;

import restaraunt.ConsoleHelper;
import restaraunt.Observable;
import restaraunt.Observer;

public class Waiter implements Observer {
    @Override
    public void update(Observable observable, Object arg) {
        ConsoleHelper.writeMessage(arg + " was cooked by " + observable);
    }
}
