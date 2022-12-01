package restaraunt.ad;

import java.util.ArrayList;
import java.util.List;

public class StatisticAdvertisementManager {
    private static StatisticAdvertisementManager instance;
    private AdvertisementStorage advertisementStorage = AdvertisementStorage.getInstance();

    private StatisticAdvertisementManager () {
    }

    public static StatisticAdvertisementManager getInstance() {
        if (instance == null) {
            instance = new StatisticAdvertisementManager();
        }
        return instance;
    }

    public List<Advertisement> getActiveOrArchiveVideoSet(String keyword) {
        List<Advertisement> activeVideoSet = new ArrayList<>();
        List<Advertisement> archiveVideoSet = new ArrayList<>();
        for (Advertisement ad : advertisementStorage.list()) {
            if (ad.getHits() > 0) {
                activeVideoSet.add(ad);
            }
            else {
                archiveVideoSet.add(ad);
            }
        }
        return keyword.equals("active") ? activeVideoSet : archiveVideoSet;
    }
}
