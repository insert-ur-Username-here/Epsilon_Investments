package Entities;

/* Class Overview:
The entity responsible for each particular candle in a chart. Immutable.
Requires certain conditions (ie. high >= low, vol >= 0)
 */

public final class Candle {
    private final long timestamp, volume;   // in seconds
    private final double open, high, low, close;

    public Candle(long timestamp, double open, double high,
                  double low, double close, long volume) {

        if (high < Math.max(open, close) || low > Math.min(open, close) || volume < 0) {
            throw new IllegalArgumentException("Inconsistent candle");
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

}
