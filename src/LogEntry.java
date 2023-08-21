import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

public class LogEntry {
    private final String ip;
    private final LocalDateTime time;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int responseSize;
    private final String referer;
    private final UserAgent userAgent;

    public LogEntry(String string) {
        String[] substr = string.replace("\"", "").split("\\[");
        ip = substr[0].split(" ")[0];
        String[] substr1 = substr[1].split(" ");
        String strtime = substr1[0].concat(substr1[1]).trim().replace("]", "");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ssX", Locale.US);
        time = LocalDateTime.parse(strtime, formatter).truncatedTo(ChronoUnit.SECONDS);
        method = HttpMethod.valueOf(substr1[2].trim());
        path = substr1[3].trim();
        responseCode = Integer.parseInt(substr1[5].trim());
        responseSize = Integer.parseInt(substr1[6].trim());
        referer = substr1[7].trim();
        if (string.contains("Mozilla/5.0 (") && !string.split("Mozilla/5.0 \\(")[1].startsWith("compatible")) {
            userAgent = new UserAgent(string.split("Mozilla/5.0 \\(")[1]);
        } else userAgent = null;
    }

    public String getIp() {
        return ip;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getResponseSize() {
        return responseSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }
}
