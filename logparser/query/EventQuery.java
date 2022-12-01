package logparser.query;

import logparser.Event;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface EventQuery {
    int getNumberOfAllEvents(Date after, Date before);

    int gentNumberOfAttemptToSolveTask(int task, Date after, Date before);

    int gentNumberOfSuccessfullAttemptToSolveTask(int task, Date after, Date before);

    Set<Event> getAllEvents(Date after, Date before);

    Set<Event> getEventsForIp(String IP, Date after, Date before);

    Set<Event> getEventsForUser(String user, Date after, Date before);

    Set<Event> getFailedEvents(Date after, Date before);

    Set<Event> getErrorEvents(Date after, Date before);

    Map<Integer, Integer> getAllSolvedTaskAndTheirNumber(Date after, Date before);

    Map<Integer, Integer> getAllDoneTaskAndTheirNumber(Date after, Date before);
}
