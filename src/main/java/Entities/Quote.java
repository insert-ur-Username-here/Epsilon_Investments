package Entities;

/* Class Overview:
A snapshot of an asset's current price and the day's key figures. Immutable.
Timestamp is in seconds (unix time), matching Candle.
 */
public final class Quote {
    private final String symbol;
    private final double price;          // current / last traded price
    private final double previousClose;
    private final double open, high, low; // today's figures
    private final long timestamp;

    public Quote(String symbol, double price, double previousClose,
                 double open, double high, double low, long timestamp) {

        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol is required");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (previousClose < 0) {
            throw new IllegalArgumentException("Previous close cannot be negative");
        }

        this.symbol = symbol.trim().toUpperCase();
        this.price = price;
        this.previousClose = previousClose;
        this.open = open;
        this.high = high;
        this.low = low;
        this.timestamp = timestamp;
    }

    /** Getters */
    public String getSymbol()        { return symbol; }
    public double getPrice()         { return price; }
    public double getPreviousClose() { return previousClose; }
    public double getOpen()          { return open; }
    public double getHigh()          { return high; }
    public double getLow()           { return low; }
    public long getTimestamp()       { return timestamp; }

    /** Gives the price differential from previous close*/
    public double getChange() {
        return price - previousClose;
    }

    /** Percent change since the previous close; 0 if there is no previous close. */
    public double getPercentChange() {
        return previousClose == 0 ? 0.0 : (price - previousClose) / previousClose * 100.0;
    }

    public Quote withTrade(Trade trade) {
        if (!symbol.equals(trade.getSymbol())) {
            throw new IllegalArgumentException("Trade is for a different symbol");
        }
        if (trade.getTimestamp() < timestamp) {
            return this; // older than current trade
        }
        double p = trade.getPrice();

        // Treat a missing (<= 0) day high/low/open as "not set yet" so it doesn't pin the range
        double newHigh = high <= 0 ? p : Math.max(high, p);
        double newLow  = low  <= 0 ? p : Math.min(low, p);
        double newOpen = open <= 0 ? p : open;

        return new Quote(symbol, p, previousClose, newOpen, newHigh, newLow, trade.getTimestamp());
    }

    @Override
    public String toString() {
        return String.format("%s %.2f (%+.2f, %+.2f%%)",
                symbol, price, getChange(), getPercentChange());
    }
}