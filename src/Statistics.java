import java.time.LocalDateTime;
import java.util.ArrayList;

public class Statistics {
    private long totalTraffic = 0;
    private int visitCount = 0;
    private int errorResponseCount = 0;
    private LocalDateTime minTime = LocalDateTime.MAX;
    private LocalDateTime maxTime = LocalDateTime.MIN;
    private ArrayList<String> ipList = new ArrayList<>();


    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        LocalDateTime currentDateTime = logEntry.getTime();
        if (currentDateTime.isBefore(minTime)) minTime = currentDateTime;
        if (currentDateTime.isAfter(maxTime)) maxTime = currentDateTime;
        if (logEntry.getUserAgent() != null && !logEntry.getUserAgent().isBot()) visitCount++;
        if (logEntry.getResponseCode() > 399) errorResponseCount++;

        if (ipList == null) ipList.add(logEntry.getIp());
        else {
            if (!ipList.contains(logEntry.getIp())) ipList.add(logEntry.getIp());
        }
    }

    public long getTrafficRate() {
        return (long) totalTraffic / maxTime.getHour() - minTime.getHour();
    }

    public ArrayList<String> getIpList() {
        return ipList;
    }

    public int averageVisitPerHour() {
        return visitCount / maxTime.getHour() - minTime.getHour();
    }

    public int averageErrorResponsePerHour() {
        return errorResponseCount / maxTime.getHour() - minTime.getHour();
    }

    public int averageVisitPerUser() {
        return visitCount / ipList.size();
    }
}
