import java.util.Locale;

public class UserAgent {
    public String string;
    private final String osType;
    private final String browser;

    public UserAgent(String string) {
        if (string.startsWith("X11;")) {
            osType = string.split(" ")[1].replace(";", "").trim();
        } else {
            osType = string.split(" ")[0].replace(";", "").trim();
        }

        if (string.contains("Gecko/")) browser = "Firefox";
        else if (string.contains("GranParadiso/")) browser = "Mozilla";
        else if (string.contains("OPR/")) browser = "Opera";
        else if (string.contains("Edg/")) browser = "Edge";
        else if (string.contains("Mobile")) browser = "Safari";
        else if (string.contains("Trident/")) browser = "Internet Explorer";
        else if (string.contains("KHTML, like Gecko") || string.contains("Chrome/")) browser = "Chrome";
        else browser = "Unknown";
    }

    public String getOsType() {
        return osType;
    }

    public String getBrowser() {
        return browser;
    }
}
