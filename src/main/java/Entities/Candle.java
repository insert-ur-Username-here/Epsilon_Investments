package Entities;

/* Class Overview:
The entity responsible for each particular candle in a chart. Immutable.
Requires certain conditions (ie. high >= low, vol >= 0)
 */

public final class Candle {
    private final long timestamp, volume; // timestamp in seconds
    private final double open, high, low, close;

    public Candle(long timestamp, double open, double high,
                  double low, double close, long volume) {

        if (!(open > 0) || !(high > 0) || !(low > 0) || !(close > 0)
                || Double.isInfinite(open) || Double.isInfinite(high)
                || Double.isInfinite(low) || Double.isInfinite(close)) {
            throw new IllegalArgumentException("Prices must be positive and finite");
        }
        if (high < Math.max(open, close) || low > Math.min(open, close)) {
            throw new IllegalArgumentException(
                    "High/low inconsistent with open/close: o=" + open + " h=" + high
                            + " l=" + low + " c=" + close);
        }
        if (volume < 0) {
            throw new IllegalArgumentException("Volume cannot be negative");
        }

        this.timestamp = timestamp; this.volume = volume;

        this.open = open; this.high = high; this.low = low; this.close = close;
    }

    /** Getters */
    public long getTimestamp() {
        return timestamp;
    }
    public long getVolume() {
        return volume;
    }
    public double getOpen() {
        return open;
    }
    public double getClose() {
        return close;
    }
    public double getHigh() {
        return high;
    }
    public double getLow() {
        return low;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Candle)) return false;
        Candle c = (Candle) o;
        return timestamp == c.timestamp && volume == c.volume
                && Double.compare(open, c.open) == 0 && Double.compare(high, c.high) == 0
                && Double.compare(low, c.low) == 0 && Double.compare(close, c.close) == 0;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(timestamp, open, high, low, close, volume);
    }

    @Override
    public String toString() {
        return String.format(java.util.Locale.US,
                "Candle[t=%d o=%.2f h=%.2f l=%.2f c=%.2f v=%d]",
                timestamp, open, high, low, close, volume);
    }

}
