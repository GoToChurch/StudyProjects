package restaraunt;

import restaraunt.kitchen.Cook;
import restaraunt.kitchen.Order;
import restaraunt.kitchen.Waiter;
import restaraunt.statistic.StatisticManager;

import java.util.ArrayList;
import java.util.List;

public class Restaraunt {
    private static int ORDER_CREATING_INTERVAL = 100;

    public static void main(String[] args) throws InterruptedException {
        List<Tablet> tablets = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            tablets.add(new Tablet(i));
        }

        Cook cook = new Cook("Chef Ivlev");
        Cook cook2 = new Cook("Gordon Ramsey");

        StatisticManager statisticManager = new StatisticManager();
        statisticManager.register(cook);
        statisticManager.register(cook2);

        Waiter waiter = new Waiter();
        cook.registerObserver(waiter);
        cook2.registerObserver(waiter);

        OrderManager orderManager = new OrderManager();

        for (Tablet tablet : tablets) {
            tablet.registerObserver(orderManager);
        }
        Thread thread = new Thread(new RandomOrderGeneratorTask(tablets, ORDER_CREATING_INTERVAL));
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();

        DirectorTablet directorTablet = new DirectorTablet();
        directorTablet.printAdvertisementProfit();
        directorTablet.printCookWorkLoading();
        directorTablet.printActiveVideoSet();
        directorTablet.printArchivedVideoSet();
    }

}
