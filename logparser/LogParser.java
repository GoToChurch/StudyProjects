package logparser;

import logparser.query.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser implements IPQuery, UserQuery, DateQuery, EventQuery, QLQuery {
    private Path logDir;
    private Set<Log> logs;
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static int coursor = 0;

    public LogParser(Path logDir) {
        this.logDir = logDir;
        logs = getAllLogsFromLogDir();
    }

    private Set<Log> getAllLogsFromLogDir() {
        Set<Log> logs = new HashSet<>();
        File file = logDir.toFile();

        for (File logFile : file.listFiles()) {
            try {
                Scanner scanner = new Scanner(new FileReader(logFile));
                while (scanner.hasNext()) {
                    String[] logString = scanner.nextLine().split(" ");
                    Log log = new Log();

                    setIp(log, logString);
                    setName(log, logString);
                    setDate(log, logString);
                    setEvent(log, logString);
                    setStatus(log, logString);

                    logs.add(log);
                    coursor = 0;
                }
            } catch (FileNotFoundException | ParseException e) {
                e.printStackTrace();
            }
        }
        return logs;
    }

    private static void setIp(Log log, String[] logString) {
        log.setIp(logString[coursor]);
        coursor++;
    }

    private static void setName(Log log, String[] logString) {
        Pattern namePattern = Pattern.compile("[a-zA-z]+");

        StringBuilder stringBuilder = new StringBuilder();

        while (namePattern.matcher(logString[coursor]).matches()) {
            stringBuilder.append(logString[coursor]).append(" ");
            coursor++;
        }

        log.setName(stringBuilder.toString().trim());

    }

    private static void setDate(Log log, String[] logString) throws ParseException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            stringBuilder.append(logString[coursor]).append(" ");
            coursor++;
        }
        log.setDate(simpleDateFormat.parse(stringBuilder.toString().trim()));
    }

    private static void setEvent(Log log, String[] logString) {
        String eventString = logString[coursor];
        if (eventString.equals("SOLVE_TASK") || eventString.equals("DONE_TASK")) {
            log.setEvent(Event.valueOf(eventString));
            coursor++;
            log.setTaskNumber(Integer.valueOf(logString[coursor]));
        }
        else {
            log.setEvent(Event.valueOf(eventString));
        }
        coursor++;
    }

    private static void setStatus(Log log, String[] logString) {
        log.setStatus(Status.valueOf(logString[coursor]));
    }

    @Override
    public int getNumberOfUniqueIPs(Date after, Date before) {
        return getUniqueIPs(after, before).size();
    }

    @Override
    public Set<String> getUniqueIPs(Date after, Date before) {
        Set<String> ips = new HashSet<>();
        Set<Log> logs = getAllLogsFromLogDir();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before)) {
                ips.add(String.valueOf(log.getIp()));
            }

        }

        return ips;
    }

    @Override
    public Set<String> getUIPsForUser(String user, Date after, Date before) {
        Set<String> ips = new HashSet<>();
        Set<Log> logs = getAllLogsFromLogDir();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getName().equals(user)) {
                ips.add(String.valueOf(log.getIp()));
            }
        }

        return ips;
    }

    @Override
    public Set<String> getUIPsForEvent(Event event, Date after, Date before) {
        Set<String> ips = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getEvent().equals(event)) {
                ips.add(String.valueOf(log.getIp()));
            }
        }

        return ips;
    }

    @Override
    public Set<String> getUIPsForStatus(Status status, Date after, Date before) {
        Set<String> ips = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getStatus().equals(status)) {
                ips.add(String.valueOf(log.getIp()));
            }
        }

        return ips;
    }

    @Override
    public Set<String> getAllUsers() {
        Set<String> users = new HashSet<>();

        for (Log log : logs) {
            users.add(log.getName());
        }

        return users;
    }

    @Override
    public int getNumberOfUsers(Date after, Date before) {
        return getAllUsers().size();
    }

    @Override
    public int getNumberOfUserEvents(String user, Date after, Date before) {
        Set<String> events = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getName().equals(user)) {
                events.add(String.valueOf(log.getEvent()));
            }
        }

        return events.size();
    }

    @Override
    public Set<String> getUsersForIP(String ip, Date after, Date before) {
        Set<String> users = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getIp().equals(ip)) {
                users.add(log.getName());
            }
        }

        return users;
    }

    @Override
    public Set<String> getLoggedUsers(Date after, Date before) {
        Set<String> users = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getEvent().equals(Event.LOGIN)) {
                users.add(log.getName());
            }
        }

        return users;
    }

    @Override
    public Set<String> getDownloadedPluginUsers(Date after, Date before) {
        Set<String> users = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getEvent().equals(Event.DOWNLOAD_PLUGIN)) {
                users.add(log.getName());
            }
        }

        return users;
    }

    @Override
    public Set<String> getWroteMessagesUsers(Date after, Date before) {
        Set<String> users = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getEvent().equals(Event.WRITE_MESSAGE)) {
                users.add(log.getName());
            }
        }

        return users;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before) {
        Set<String> users = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getEvent().equals(Event.SOLVE_TASK)) {
                users.add(log.getName());
            }
        }

        return users;
    }

    @Override
    public Set<String> getSolvedTaskUsers(Date after, Date before, int task) {
        Set<String> users = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getEvent().equals(Event.SOLVE_TASK)
                    && log.getTaskNumber() == task) {
                users.add(log.getName());
            }
        }

        return users;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before) {
        Set<String> users = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getEvent().equals(Event.DONE_TASK)) {
                users.add(log.getName());
            }
        }

        return users;
    }

    @Override
    public Set<String> getDoneTaskUsers(Date after, Date before, int task) {
        Set<String> users = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getEvent().equals(Event.DONE_TASK)
                    && log.getTaskNumber() == task) {
                users.add(log.getName());
            }
        }

        return users;
    }

    public Set<Date> getAllDates() {
        Set<Date> dates = new HashSet<>();

        for (Log log : logs) {
            dates.add(log.getDate());
        }

        return dates;
    }

    @Override
    public Set<Date> getDatesForUserAndEvent(String user, Event event, Date after, Date before) {
        Set<Date> dates = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getEvent().equals(event)
                    && log.getName().equals(user)) {
                dates.add(log.getDate());
            }
        }

        return dates;
    }

    @Override
    public Set<Date> getDatesWhenSomethingFailed(Date after, Date before) {
        Set<Date> dates = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getStatus().equals(Status.FAILED)) {
                dates.add(log.getDate());
            }
        }

        return dates;
    }

    @Override
    public Set<Date> getDatesWhenErrorHappened(Date after, Date before) {
        Set<Date> dates = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getStatus().equals(Status.ERROR)) {
                dates.add(log.getDate());
            }
        }

        return dates;
    }

    @Override
    public Set<Date> getDatesWhenUserWroteMessage(String user, Date after, Date before) {
        Set<Date> dates = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getName().equals(user)
            && log.getEvent().equals(Event.WRITE_MESSAGE)) {
                dates.add(log.getDate());
            }
        }

        return dates;
    }

    @Override
    public Set<Date> getDatesWhenUserDownoloadedPlugin(String user, Date after, Date before) {
        Set<Date> dates = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getName().equals(user)
                    && log.getEvent().equals(Event.DOWNLOAD_PLUGIN)) {
                dates.add(log.getDate());
            }
        }

        return dates;
    }

    @Override
    public Date getDateWhenUserLoggedFirstTime(String user, Date after, Date before) {
        Date firstTime = null;

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getName().equals(user)
                    && log.getEvent().equals(Event.LOGIN)) {
                Date newDate = log.getDate();
                if (newDate.before(firstTime) || firstTime == null) {
                    firstTime = newDate;
                }
            }
        }

        return firstTime;
    }

    @Override
    public Date getDateWhenUserSolvedTask(String user, int task, Date after, Date before) {
        Date dateOfSolvingTask = null;

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getName().equals(user)
                    && log.getEvent().equals(Event.SOLVE_TASK) && log.getTaskNumber() == task) {
                dateOfSolvingTask = log.getDate();
            }
        }

        return dateOfSolvingTask;
    }

    @Override
    public Date getDateWhenUserDoneTask(String user, int task, Date after, Date before) {
        Date dateDoneTask = null;

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getName().equals(user)
                    && log.getEvent().equals(Event.DONE_TASK) && log.getTaskNumber() == task) {
                dateDoneTask = log.getDate();
            }
        }

        return dateDoneTask;
    }

    @Override
    public int getNumberOfAllEvents(Date after, Date before) {
        return getAllEvents(after, before).size();
    }

    @Override
    public int gentNumberOfAttemptToSolveTask(int task, Date after, Date before) {
        int tries = 0;

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getEvent().equals(Event.SOLVE_TASK)
                    && log.getTaskNumber() == task) {
                tries++;
            }
        }

        return tries;
    }

    @Override
    public int gentNumberOfSuccessfullAttemptToSolveTask(int task, Date after, Date before) {
        int tries = 0;

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getEvent().equals(Event.DONE_TASK)
                    && log.getTaskNumber() == task) {
                tries++;
            }
        }

        return tries;
    }

    @Override
    public Set<Event> getAllEvents(Date after, Date before) {
        Set<Event> events = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before)) {
                events.add(log.getEvent());
            }
        }

        return events;
    }

    @Override
    public Set<Event> getEventsForIp(String IP, Date after, Date before) {
        Set<Event> events = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getIp().equals(IP)) {
                events.add(log.getEvent());
            }
        }

        return events;
    }

    @Override
    public Set<Event> getEventsForUser(String user, Date after, Date before) {
        Set<Event> events = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getName().equals(user)) {
                events.add(log.getEvent());
            }
        }

        return events;
    }

    @Override
    public Set<Event> getFailedEvents(Date after, Date before) {
        Set<Event> events = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getStatus().equals(Status.FAILED)) {
                events.add(log.getEvent());
            }
        }

        return events;
    }

    @Override
    public Set<Event> getErrorEvents(Date after, Date before) {
        Set<Event> events = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getStatus().equals(Status.ERROR)) {
                events.add(log.getEvent());
            }
        }

        return events;
    }

    @Override
    public Map<Integer, Integer> getAllSolvedTaskAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> solvedTasks = new HashMap<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getEvent().equals(Event.SOLVE_TASK)) {
                int tries = gentNumberOfAttemptToSolveTask(log.getTaskNumber(), after, before);
                solvedTasks.put(log.getTaskNumber(), tries);
            }
        }

        return solvedTasks;
    }

    @Override
    public Map<Integer, Integer> getAllDoneTaskAndTheirNumber(Date after, Date before) {
        Map<Integer, Integer> solvedTasks = new HashMap<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before) && log.getEvent().equals(Event.DONE_TASK)) {
                int tries = gentNumberOfSuccessfullAttemptToSolveTask(log.getTaskNumber(), after, before);
                solvedTasks.put(log.getTaskNumber(), tries);
            }
        }

        return solvedTasks;
    }

    public Set<Status> getAllStatuses(Date after, Date before) {
        Set<Status> statuses = new HashSet<>();

        for (Log log : logs) {
            if (dateBetweenDates(log.getDate(), after, before)) {
                statuses.add(log.getStatus());
            }
        }

        return statuses;
    }


    @Override
    public Set<?> execute(String query) {
        Set<Object> result = new HashSet<>();
        String field1;
        String field2 = null;
        String value1 = null;
        Date after = null;
        Date before = null;
        Pattern pattern = Pattern.compile("get (ip|user|date|event|status)" + "( for (ip|user|date|event|status) = \"(.*?)\")?"
        + "( and date between \"(.*?)\" and \"(.*?)\")?");
        Matcher matcher = pattern.matcher(query);
        matcher.find();
        field1 = matcher.group(1);
        if (matcher.group(2) != null) {
            field2 = matcher.group(3);
            value1 = matcher.group(4);
            if (matcher.group(5) != null) {
                try {
                    after = simpleDateFormat.parse(matcher.group(6));
                    before = simpleDateFormat.parse(matcher.group(7));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        if (field2 != null && value1 != null) {
            for (Log log : logs) {
                if (value1.equals(getValue(log, field2)) && dateBetweenDates(log.getDate(), after, before)) {
                    result.add(getValue(log, field1));
                }
            }
        }

        return result;
    }

    private boolean dateBetweenDates(Date current, Date after, Date before) {
        if (after == null) {
            after = new Date(0);
        }

        if (before == null) {
            before = new Date(Long.MAX_VALUE);
        }

        return current.after(after) && current.before(before);
    }

    private Object getValue(Log log, String field) {
        Object value = null;

        switch (field) {
            case "ip" -> {
                value = log.getIp();
            }

            case "user" -> {
                value = log.getName();
            }

            case "date" -> {
                value = log.getDate();
            }

            case "event" -> {
                value = log.getEvent();
            }

            case "status" -> {
                value = log.getStatus();
            }
        }

        return value;
    }
}
