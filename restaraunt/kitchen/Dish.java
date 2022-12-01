package restaraunt.kitchen;

public enum Dish {
    FISH(25),
    STEAK(30),
    SOUP(15),
    JUICE(5),
    WATER(3);

    private final int timeNeededForCooking;

    Dish(int timeNeededForCooking) {
        this.timeNeededForCooking = timeNeededForCooking;
    }

    public static String allDishesToString() {
        StringBuilder stringBuilder = new StringBuilder();
        String allDishesString;
        for (Dish dish : Dish.values()) {
            stringBuilder.append(dish).append(", ");
        }
        allDishesString = stringBuilder.toString();
        return allDishesString.substring(0, allDishesString.length()-2);
    }

    public int getTimeNeededForCooking() {
        return timeNeededForCooking;
    }
}
