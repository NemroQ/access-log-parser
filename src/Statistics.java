import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    public BigDecimal getTrafficRate() {
        BigDecimal bigDecimal = new BigDecimal(totalTraffic);
        return bigDecimal.divide(new BigDecimal(maxTime.getHour() - minTime.getHour()));
    }
}
