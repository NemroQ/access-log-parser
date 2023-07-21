import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    private long totalTraffic = 0;
    private HashSet<String> pageList = new HashSet<>();
    private HashMap<String, Integer> osList = new HashMap<>();
    private HashMap<String, Long> osPart = new HashMap<>();
    private LocalDateTime minTime = LocalDateTime.MAX;
    private LocalDateTime maxTime = LocalDateTime.MIN;

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        LocalDateTime currentDateTime = logEntry.getTime();
        if (currentDateTime.isBefore(minTime)) minTime = currentDateTime;
        if (currentDateTime.isAfter(maxTime)) maxTime = currentDateTime;

        if (logEntry.getResponseCode() == 200) pageList.add(logEntry.getPath());

        if (logEntry.getUserAgent() != null) {
            if (!osList.containsKey(logEntry.getUserAgent().getOsType())) {
                osList.put(logEntry.getUserAgent().getOsType(), 1);
            } else {
                osList.put(logEntry.getUserAgent().getOsType(), osList.get(logEntry.getUserAgent().getOsType()) + 1);
            }
        }
    }

    public HashSet<String> getPageList() {
        return pageList;
    }

    public HashMap<String, Integer> getOsList() {
        return osList;
    }

    public double getTrafficRate() {
        return (double) (totalTraffic / (maxTime.getHour() - minTime.getHour()));
    }

    public HashMap<String, Double> getOsPart() {
        return calcOsPart(osList);
    }

    private HashMap<String, Double> calcOsPart(HashMap<String, Integer> osList) {
        HashMap<String, Double> osPart = new HashMap<>();
        int osAllCount = 0;
        for (int i : osList.values()) {
            osAllCount += i;
        }
        for (String os : osList.keySet()) {
            osPart.put(os, (double) osList.get(os) / osAllCount);
        }
        return osPart;
    }


}
