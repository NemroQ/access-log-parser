import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    private long totalTraffic = 0;
    private int visitCount = 0;
    private int errorResponseCount = 0;
    private LocalDateTime minTime = LocalDateTime.MAX;
    private LocalDateTime maxTime = LocalDateTime.MIN;
    private ArrayList<String> ipList = new ArrayList<>();
    private HashMap<LocalDateTime, Integer> requestMap = new HashMap<>();
    private HashSet<String> domainSet = new HashSet<>();


    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        LocalDateTime currentDateTime = logEntry.getTime();
        if (currentDateTime.isBefore(minTime)) minTime = currentDateTime;
        if (currentDateTime.isAfter(maxTime)) maxTime = currentDateTime;
        if (logEntry.getUserAgent() != null && !logEntry.getUserAgent().isBot()) {
            visitCount++;
            if (!requestMap.containsKey(logEntry.getTime())) {
                requestMap.put(logEntry.getTime(), 1);
            } else {
                int t = requestMap.get(logEntry.getTime());
                requestMap.replace(logEntry.getTime(), t++);
            }
        }
        if (logEntry.getResponseCode() > 399) errorResponseCount++;

        if (ipList == null) ipList.add(logEntry.getIp());
        else {
            if (!ipList.contains(logEntry.getIp())) ipList.add(logEntry.getIp());
        }
        String domain = logEntry.getReferer();
        if (domain.split("//").length > 2) {
            if (!domainSet.contains(logEntry.getReferer().trim().split("//")[1].trim().split("/")[0]))
                domainSet.add(logEntry.getReferer().trim().split("//")[1].trim().split("/")[0].trim().replace("www.", ""));
        }
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }

    public HashSet<String> getDomainSet() {
        return domainSet;
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

    public int maxRequestPerSecond() {
        return requestMap.values().stream().max(Integer::compare).get();
    }
}
