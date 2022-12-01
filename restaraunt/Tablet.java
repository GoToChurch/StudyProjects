package restaraunt;

import restaraunt.ad.AdvertisementManager;
import restaraunt.ad.NoVideoAvailableException;
import restaraunt.kitchen.Order;
import restaraunt.kitchen.TestOrder;
import restaraunt.statistic.StatisticManager;
import restaraunt.statistic.event.SelectedVideos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tablet implements Observable{

    private final int tabletID;
    private final List<Observer> observers = new ArrayList<>();
    private StatisticManager statisticManager = new StatisticManager();
    private static Logger logger = Logger.getLogger(Tablet.class.getName());

    public Tablet(int tabletID) {
        this.tabletID = tabletID;
    }

    public Order createOrder() {
        Order order = null;
        try {
            order = new Order(this);
            OrderTypeCode(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable");
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for " + order);
        }
        return order;
    }

    private void OrderTypeCode(Order order) {
        if (!order.isEmpty()) {
            ConsoleHelper.writeMessage(order.toString());
            int totalTimeCooking = order.getTotalCookingTime();
            AdvertisementManager adManager = new AdvertisementManager(totalTimeCooking * 60);
            adManager.processVideos();
            statisticManager.register(new SelectedVideos(adManager.getProceededVideos(), adManager.getValue(), adManager.getTotalDuration()));
            notifyObservers(order);
        }
    }

    public TestOrder createTestOrder() {
        TestOrder order = null;
        try {
            order = new TestOrder(this);
            OrderTypeCode(order);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Console is unavailable");
        } catch (NoVideoAvailableException e) {
            logger.log(Level.INFO, "No video is available for " + order);
        }
        return order;
    }

    @Override
    public String toString() {
        return "Tablet{" +
                "ID=" + tabletID +
                '}';
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
