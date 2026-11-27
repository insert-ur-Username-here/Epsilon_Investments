package Entities;

public enum TimeFrame {

    ONE_MINUTE("1 Minute", 60),
    FIVE_MINUTES("5 Minutes", 300),
    FIFTEEN_MINUTES("15 Minutes", 900),
    ONE_HOUR("1 Hour", 3600),
    ONE_DAY("1 Day", 86400),
    ONE_WEEK("1 Week", 604800);

    private final String displayName;
    private final long seconds;

    TimeFrame(String displayName, long seconds) {
        this.displayName = displayName;
        this.seconds = seconds;
    }

    /** Length of one candle, in seconds. */
    public long getSeconds() {
        return seconds;
    }

    /** Length of the candle, as a String. */
    public String getDisplayName() {
        return displayName;
    }

    /** Controls how the value appears in a JComboBox or when printed. */
    @Override
    public String toString() {
        return displayName;
    }

}
