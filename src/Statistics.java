import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Statistics {
    private long totalTraffic = 0;
    private LocalDateTime minTime = LocalDateTime.MAX;
    private LocalDateTime maxTime = LocalDateTime.MIN;


    public void addEntry(LogEntry logEntry) {
        totalTraffic += logEntry.getResponseSize();
        LocalDateTime currentDateTime = logEntry.getTime();
        if (currentDateTime.isBefore(minTime)) minTime = currentDateTime;
        if (currentDateTime.isAfter(maxTime)) maxTime = currentDateTime;
    }

    public double getTrafficRate() {
        long time = ChronoUnit.SECONDS.between(minTime, maxTime);
        return Math.round(((double) totalTraffic / time) * 1000) / 1000D;
    }
}
