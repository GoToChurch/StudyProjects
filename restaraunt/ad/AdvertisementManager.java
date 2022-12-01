package restaraunt.ad;


import restaraunt.ConsoleHelper;

import java.util.*;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private List<Advertisement> proceededVideos;
    private final int timeSeconds;
    private int value = 0;
    private int totalDuration;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() throws NoVideoAvailableException {

        if (storage.list().isEmpty()) {
            throw new NoVideoAvailableException();
        }
        else {
            List<Advertisement> videosToShow = new ArrayList<>();
            Map<Integer, List<Advertisement>> strategies = new HashMap<>();
            for (Advertisement ad : storage.list()) {
                int i = 0;
                int timeLeft = timeSeconds;
                while (i < storage.list().size()) {
                    ObtainMaxValue(ad, storage.list(), videosToShow, strategies, timeLeft, i);
                    i++;
                }
                ad.revalidate();
            }
            value = strategies.keySet().stream().max(Comparator.naturalOrder()).get();
            proceededVideos = strategies.get(value);
            for (Advertisement ad : proceededVideos) {
                totalDuration += ad.getDuration();
            }
            displayVideos(proceededVideos);
        }

    }

    private void ObtainMaxValue(Advertisement ad, List<Advertisement> videos, List<Advertisement> videosToShow, Map<Integer, List<Advertisement>> strategies, int timeLeft, int iteration) {
        int time = timeLeft;
        Advertisement adToAdd = videos.get(iteration);

        if (adToAdd.equals(ad)) {
            return;
        }

        time -= tryToPutVideo(ad, videosToShow, time);
        iteration++;

        if (time > 0 && iteration <= videos.size()-1) {
            ObtainMaxValue(adToAdd, videos, videosToShow, strategies, time, iteration);
        }
        else {
            strategies.put(value, new ArrayList<>(videosToShow));
            videosToShow.clear();
            value = 0;
        }
    }

    private int tryToPutVideo(Advertisement ad, List<Advertisement> videosToShow, int time) {
        if (ad.getDuration() <= time && !videosToShow.contains(ad)) {
            videosToShow.add(ad);
            value += ad.getAmountPerOneDisplaying();
            time = ad.getDuration();
            return time;
        }
        return 0;
    }

    private void displayVideos(List<Advertisement> videos) {
        Comparator<Advertisement> adComparator = new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement o1, Advertisement o2) {
                return (int) (o2.getAmountPerOneDisplaying() - o1.getAmountPerOneDisplaying());
            }
        };
        Collections.sort(videos, adComparator);
        for (Advertisement ad : videos) {
            String valuePerSecond = String.format("%.0f", ((double) ad.getAmountPerOneDisplaying() / (double) ad.getDuration()) * 1000);
            ConsoleHelper.writeMessage(ad.getName() + " is displaying... " + ad.getAmountPerOneDisplaying() + ", " + valuePerSecond);
        }
    }

    public List<Advertisement> getProceededVideos() {
        return proceededVideos;
    }

    public int getValue() {
        return value;
    }

    public int getTotalDuration() {
        return totalDuration;
    }
}
