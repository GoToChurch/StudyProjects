package restaraunt.statistic.event;

import restaraunt.kitchen.Dish;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CookedOrder implements EventDataRow {
    private final Date date;
    private final String tabletName;
    private final String cookName;
    private final int cookingTimeSeconds;
    private final List<Dish> cookingDishes;

    public CookedOrder(String tabletName, String cookName, int cookingTimeSeconds, List<Dish> cookingDishes) {
        this.tabletName = tabletName;
        this.cookName = cookName;
        this.cookingTimeSeconds = cookingTimeSeconds;
        this.cookingDishes = cookingDishes;
        this.date = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.COOKED_ORDER;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public int getTime() {
        return cookingTimeSeconds;
    }

    public String getCookName() {
        return cookName;
    }
}
