package restaraunt.ad;

import java.util.ArrayList;
import java.util.List;

public class AdvertisementStorage {
    private static AdvertisementStorage instance;
    private final List<Advertisement> videos = new ArrayList<>();
    private AdvertisementStorage () {
        Object content = new Object();
        videos.add(new Advertisement(content, "Fonbet AD", 10000, 100, 10*60));
        videos.add(new Advertisement(content, "Parimatch AD", 12000, 100, 15*60));
        videos.add(new Advertisement(content, "Winline AD", 9000, 100, 3*60));
    }

    public static AdvertisementStorage getInstance() {
        if (instance == null) {
            instance = new AdvertisementStorage();
        }
        return instance;
    }

    public List<Advertisement> list() {
        return videos;
    }

    public void add(Advertisement advertisement) {
        videos.add(advertisement);
    }
}
