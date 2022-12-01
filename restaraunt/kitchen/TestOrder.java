package restaraunt.kitchen;


import restaraunt.Tablet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestOrder extends Order {
    public TestOrder(Tablet tablet) throws IOException {
        super(tablet);
    }

    @Override
    protected List<Dish> initDishes() throws IOException {
        List<Dish> dishes = new ArrayList<>(){{
            add(Dish.FISH);
            add(Dish.SOUP);
            add(Dish.STEAK);
            add(Dish.JUICE);
            add(Dish.WATER);
        }};

        List<Dish> randomDishes = new ArrayList<>();
        Random random = new Random();
        int orderSize = random.nextInt(5);

        for (int i = 0; i < orderSize; i++) {
            randomDishes.add(dishes.get(random.nextInt(dishes.size())));
        }

        return randomDishes;
    }
}
