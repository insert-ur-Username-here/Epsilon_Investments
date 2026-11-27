package Entities;

/* Class Overview:
The candle that is still forming. MUTABLE and NOT thread-safe: it is owned by a single
object (LiveChartSession) that guards it with a lock and never hands it out.
Use toCandle() to get an immutable snapshot.
Timestamps are in seconds (unix time), matching Candle, Quote and Trade.
 */

public final class CurrentCandle {
    private final String symbol;
    private final TimeFrame timeframe;
    private final long startTime;      // start of this candle's interval, in seconds

    private final double open;         // fixed once set
    private double high, low, close;
    private long volume;
    private long lastTradeTime;        // time of the trade that set `close`

    /** Start a fresh candle whose first price is `price`. Volume starts at 0. */
    public CurrentCandle(String symbol, TimeFrame timeframe, long startTime, double price) {
        this(symbol, timeframe, startTime, price, price, price, price, 0, startTime);
    }

    private CurrentCandle(String symbol, TimeFrame timeframe, long startTime,
                          double open, double high, double low, double close,
                          long volume, long lastTradeTime) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol is required");
        }
        if (timeframe == null) {
            throw new IllegalArgumentException("Timeframe is required");
        }
        if (!(open > 0) || !(high > 0) || !(low > 0) || !(close > 0)
                || Double.isInfinite(open) || Double.isInfinite(high)
                || Double.isInfinite(low) || Double.isInfinite(close)) {
            throw new IllegalArgumentException("Prices must be positive and finite");
        }
        if (volume < 0) {
            throw new IllegalArgumentException("Volume cannot be negative");
        }
        this.symbol = symbol.trim().toUpperCase();
        this.timeframe = timeframe;
        this.startTime = startTime;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.lastTradeTime = lastTradeTime;
    }

    /** Resume a partially formed candle fetched from the API, keeping its real OHLC and volume. */
    public static CurrentCandle from(String symbol, TimeFrame timeframe, Candle c) {
        return new CurrentCandle(symbol, timeframe, c.getTimestamp(),
                c.getOpen(), c.getHigh(), c.getLow(), c.getClose(),
                c.getVolume(), c.getTimestamp());
    }

    /** True if the timestamp falls inside this candle's interval [start, start + length). */
    public boolean covers(long timestamp) {
        return timestamp >= startTime && timestamp < getEndTime();
    }

    /** Fold a trade into this candle. The caller should check covers() first. */
    public void update(Trade trade) {
        if (!symbol.equals(trade.getSymbol())) {
            throw new IllegalArgumentException("Trade is for a different symbol");
        }
        if (!covers(trade.getTimestamp())) {
            throw new IllegalArgumentException("Trade is outside this candle's interval");
        }

        double p = trade.getPrice();
        high = Math.max(high, p);
        low = Math.min(low, p);
        volume += trade.getVolume();

        // Only a trade at least as recent as the last one may set the close.
        // (Equal timestamps are allowed: many trades share the same second.)
        if (trade.getTimestamp() >= lastTradeTime) {
            close = p;
            lastTradeTime = trade.getTimestamp();
        }
    }

    /** The next candle, on the interval boundary that contains this trade. Does not apply the trade. */
    public CurrentCandle rollOver(Trade trade) {
        long length = timeframe.getSeconds();
        long steps = (trade.getTimestamp() - startTime) / length;
        if (steps < 1) {
            throw new IllegalStateException("Trade does not belong to a later interval");
        }
        return new CurrentCandle(symbol, timeframe, startTime + steps * length, trade.getPrice());
    }

    /** An immutable snapshot, safe to hand to the view or append to a CandleSeries. */
    public Candle toCandle() {
        return new Candle(startTime, open, high, low, close, volume);
    }

    /** Getters */
    public String getSymbol()       { return symbol; }
    public TimeFrame getTimeFrame() { return timeframe; }
    public long getStartTime()      { return startTime; }
    public long getEndTime()        { return startTime + timeframe.getSeconds(); }
}