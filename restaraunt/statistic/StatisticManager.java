package restaraunt.statistic;


import restaraunt.kitchen.Cook;
import restaraunt.statistic.event.CookedOrder;
import restaraunt.statistic.event.EventDataRow;
import restaraunt.statistic.event.EventType;
import restaraunt.statistic.event.SelectedVideos;

import java.util.*;

public class StatisticManager {
    private StatisticStorage statisticStorage = StatisticStorage.getInstance();
    private Set<Cook> cooks = new HashSet<>();

    public void register(EventDataRow data) {
        statisticStorage.put(data);
    }

    public void register(Cook cook) {
        cooks.add(cook);
    }

    public Map<Date, Long> countTotalEarningsFromAd() {
        Map<Date, Long> earnings = new HashMap<>();
        List<SelectedVideos> videos = new ArrayList<>();

        for (EventDataRow event : statisticStorage.getStorage().get(EventType.SELECTED_VIDEOS)) {
            videos.add((SelectedVideos) event);
        }

        for (SelectedVideos event : videos) {
            earnings.put(event.getDate(), event.getAmount());
        }

        return earnings;
    }

    public Map<Date, Map<String, Integer>> countCookWorkingTime() {
        Map<Date, Map<String, Integer>> workingTime = new HashMap<>();
        List<CookedOrder> cooks = new ArrayList<>();

        for (EventDataRow event : statisticStorage.getStorage().get(EventType.COOKED_ORDER)) {
            cooks.add((CookedOrder) event);
        }

        for (CookedOrder cook : cooks) {
            Map<String, Integer> cookWorkingTime = new HashMap<>();
            cookWorkingTime.put(cook.getCookName(), cook.getTime());
            workingTime.put(cook.getDate(), cookWorkingTime);
        }

        return workingTime;
    }


    private static class StatisticStorage {
        private static StatisticStorage instance;
        private Map<EventType, List<EventDataRow>> storage = new HashMap<>();

        private StatisticStorage () {
            for (EventType eventType : EventType.values()) {
                storage.put(eventType, new ArrayList<EventDataRow>());
            }
        }

        private void put(EventDataRow data) {
            EventType type = data.getType();
            storage.get(type).add(data);
        }

        public Map<EventType, List<EventDataRow>> getStorage() {
            return storage;
        }

        public static StatisticStorage getInstance() {
            if (instance == null) {
                instance = new StatisticStorage();
            }
            return instance;
        }
    }

}
