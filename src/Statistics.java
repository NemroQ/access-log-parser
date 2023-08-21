import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.time.temporal.ChronoUnit;

public class Statistics {
    private long totalTraffic = 0;
    private HashSet<String> pageList = new HashSet<>();
    private HashSet<String> unknownPageList = new HashSet<>();
    private HashMap<String, Integer> osList = new HashMap<>();
    private HashMap<String, Integer> browserList = new HashMap<>();
    private HashMap<String, Long> osPart = new HashMap<>();
    private HashMap<String, Long> browserPart = new HashMap<>();
    private LocalDateTime minTime = LocalDateTime.MAX;
    private LocalDateTime maxTime = LocalDateTime.MIN;

    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        LocalDateTime currentDateTime = logEntry.getTime();
        if (currentDateTime.isBefore(minTime)) minTime = currentDateTime;
        if (currentDateTime.isAfter(maxTime)) maxTime = currentDateTime;

        checkPageStatus(logEntry);

        if (logEntry.getUserAgent() != null) {
            int osCount, browserCount = 0;
            if (!osList.containsKey(logEntry.getUserAgent().getOsType())) osCount = 1;
            else osCount = osList.get(logEntry.getUserAgent().getOsType()) + 1;
            osList.put(logEntry.getUserAgent().getOsType(), osCount);

            if (!browserList.containsKey(logEntry.getUserAgent().getBrowser())) browserCount = 1;
            else browserCount = browserList.get(logEntry.getUserAgent().getBrowser()) + 1;
            browserList.put(logEntry.getUserAgent().getBrowser(), browserCount);
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

    public HashMap<String, Double> getBrowserPart() {
        return calcBrowserPart(browserList);
    }

    public HashMap<String, Integer> getBrowserList() {
        return browserList;
    }

    public HashSet<String> getUnknownPageList() {
        return unknownPageList;
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

    private HashMap<String, Double> calcBrowserPart(HashMap<String, Integer> browserList) {
        HashMap<String, Double> browserPart = new HashMap<>();
        int browserAllCount = 0;
        for (int i : browserList.values()) {
            browserAllCount += i;
        }
        for (String os : browserList.keySet()) {
            browserPart.put(os, (double) browserList.get(os) / browserAllCount);
        }
        return browserPart;
    }

    private void checkPageStatus(LogEntry logEntry) {
        switch (logEntry.getResponseCode()) {
            case (200) -> pageList.add(logEntry.getPath());
            case (404) -> unknownPageList.add(logEntry.getPath());
        }
    public double getTrafficRate() {
        long time = ChronoUnit.SECONDS.between(minTime, maxTime);
        return Math.round(((double) totalTraffic / time) * 1000) / 1000D;
    }
}
