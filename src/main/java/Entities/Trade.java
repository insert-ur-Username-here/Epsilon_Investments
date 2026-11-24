package Entities;

/* Class Overview:
One executed trade from the live stream. Immutable.
Timestamp is in seconds (unix time), matching Candle and Quote.
 */
public final class Trade {
    private final String symbol;
    private final double price;
    private final long volume;
    private final long timestamp;   // in seconds

    public Trade(String symbol, double price, long volume, long timestamp) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol is required");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (volume < 0) {
            throw new IllegalArgumentException("Volume cannot be negative");
        }
        if (timestamp < 0) {
            throw new IllegalArgumentException("Timestamp cannot be negative");
        }

        this.symbol = symbol.trim().toUpperCase();
        this.price = price;
        this.volume = volume;
        this.timestamp = timestamp;
    }

    /** Getters */
    public String getSymbol() { return symbol; }
    public double getPrice()  { return price; }
    public long getVolume()   { return volume; }
    public long getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format(java.util.Locale.US, "%s %d @ %.2f (t=%d)",
                symbol, volume, price, timestamp);
    }
}