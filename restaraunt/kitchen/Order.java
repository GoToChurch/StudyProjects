package restaraunt.kitchen;

import restaraunt.ConsoleHelper;
import restaraunt.Tablet;

import java.io.IOException;
import java.util.List;

public class Order {
    private final Tablet tablet;
    protected List<Dish> dishes;

    public Order(Tablet tablet) throws IOException {
        this.tablet = tablet;
        this.dishes = initDishes();

    }

    protected List<Dish> initDishes() throws IOException {
        return ConsoleHelper.getAllDishesForOrder();
    }

    public int getTotalCookingTime() {
        int totalCookingTime = 0;

        for (Dish dish : dishes) {
            totalCookingTime += dish.getTimeNeededForCooking();
        }

        return totalCookingTime;
    }

    public boolean isEmpty() {
        return dishes.isEmpty();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    @Override
    public String toString() {
        if (dishes.isEmpty()) {
            return "";
        }
        else {
            StringBuilder stringBuilder = new StringBuilder();
            for (Dish dish : dishes) {
                stringBuilder.append(dish).append(", ");
            }
            String order = stringBuilder.toString();
            return "Your order: |" + order.substring(0, order.length()-2) + "| of " + tablet.toString() + ", cooking time " + getTotalCookingTime() + "min";
        }
    }
}
