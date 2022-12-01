package restaraunt;

import restaraunt.ad.Advertisement;
import restaraunt.ad.StatisticAdvertisementManager;
import restaraunt.statistic.StatisticManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class DirectorTablet {
    StatisticManager statisticManager = new StatisticManager();
    StatisticAdvertisementManager statisticAdvertisementManager = StatisticAdvertisementManager.getInstance();

    public void printAdvertisementProfit() {
        Map<Date, Long> earnings = statisticManager.countTotalEarningsFromAd();
        long total = 0;

        for (Date key : earnings.keySet()) {
            long amount = earnings.get(key);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH);
            ConsoleHelper.writeMessage(simpleDateFormat.format(key) + " - " + amount);
            total += amount;
        }

        ConsoleHelper.writeMessage("Total" + " - " + total);
        ConsoleHelper.writeMessage("");
    }

    public void printCookWorkLoading() {
        Map<Date, Map<String, Integer>> workingTime = statisticManager.countCookWorkingTime();
        SimpleDateFormat oldFormatString = null;
        for (Date key : workingTime.keySet()) {
            SimpleDateFormat newFormatString = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH);
            if (!newFormatString.equals(oldFormatString)) {
                ConsoleHelper.writeMessage(newFormatString.format(key));
                oldFormatString = newFormatString;
            }
            for (String name : workingTime.get(key).keySet()) {
                int time = workingTime.get(key).get(name);
                if (time > 0) {
                    ConsoleHelper.writeMessage(name + " - " + time + " min");
                }
            }
        }
        ConsoleHelper.writeMessage("");
    }

    public void printActiveVideoSet() {
        List<Advertisement> activeVideoSet = statisticAdvertisementManager.getActiveOrArchiveVideoSet("active");
        for (Advertisement ad : activeVideoSet) {
            ConsoleHelper.writeMessage(ad.getName() + " - " + ad.getHits());
        }
    }

    public void printArchivedVideoSet() {
        List<Advertisement> archiveVideoSet = statisticAdvertisementManager.getActiveOrArchiveVideoSet("archive");
        for (Advertisement ad : archiveVideoSet) {
            ConsoleHelper.writeMessage(ad.getName());
        }
    }
}
