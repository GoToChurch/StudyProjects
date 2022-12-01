package restaraunt;

import restaraunt.kitchen.Order;

import java.util.concurrent.LinkedBlockingDeque;

public class OrderManager implements Observer {
    private LinkedBlockingDeque<Order> orderQueue = new LinkedBlockingDeque<>();

    @Override
    public void update(Observable observable, Object arg) {
        orderQueue.add((Order) arg);
    }
}
