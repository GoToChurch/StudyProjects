package restaraunt.statistic.event;

import restaraunt.ad.Advertisement;

import java.util.Date;
import java.util.List;

public class SelectedVideos implements EventDataRow {
    private final List<Advertisement> videosToShow;
    private final long amount;
    private final int totalDuration;
    private final Date date;

    public SelectedVideos(List<Advertisement> videosToShow, long amount, int totalDuration) {
        this.videosToShow = videosToShow;
        this.amount = amount;
        this.totalDuration = totalDuration;
        this.date = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.SELECTED_VIDEOS;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public int getTime() {
        return totalDuration;
    }

    public long getAmount() {
        return amount;
    }
}
