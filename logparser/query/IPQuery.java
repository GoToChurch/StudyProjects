package logparser.query;

import logparser.Event;
import logparser.Status;

import java.util.Date;
import java.util.Set;

public interface IPQuery {
    int getNumberOfUniqueIPs (Date after, Date before);

    Set<String> getUniqueIPs (Date after, Date before);

    Set<String> getUIPsForUser (String user, Date after, Date before);

    Set<String> getUIPsForEvent (Event event, Date after, Date before);

    Set<String> getUIPsForStatus (Status status, Date after, Date before);

}
