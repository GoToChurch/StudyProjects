package restaraunt.kitchen;

import restaraunt.ConsoleHelper;
import restaraunt.Observable;
import restaraunt.Observer;
import restaraunt.Tablet;
import restaraunt.statistic.StatisticManager;
import restaraunt.statistic.event.CookedOrder;

import java.util.ArrayList;
import java.util.List;

public class Cook implements Observable {
    private final String name;
    private StatisticManager manager = new StatisticManager();
    private final List<Observer> observers = new ArrayList<>();

    public Cook(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void startCookingOrder(Order order, Tablet tablet) {
        ConsoleHelper.writeMessage("Start cooking - " + order.toString());
        manager.register(new CookedOrder(tablet.toString(), name, order.getTotalCookingTime(), order.getDishes()));
        notifyObservers(order);
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Object o) {
        for (Observer observer : observers) {
            observer.update(this, o);
        }
    }
}
