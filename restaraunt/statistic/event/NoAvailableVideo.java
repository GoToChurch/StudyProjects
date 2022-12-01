package restaraunt.statistic.event;

import java.util.Date;

public class NoAvailableVideo implements EventDataRow {
    private final Date date;
    private int totalDuration;

    public NoAvailableVideo(int totalDuration) {
        this.totalDuration = totalDuration;
        this.date = new Date();
    }

    @Override
    public EventType getType() {
        return EventType.NO_AVAILABLE_VIDEO;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public int getTime() {
        return totalDuration;
    }
}
