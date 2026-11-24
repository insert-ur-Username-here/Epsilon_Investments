package Entities;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Class Overview:
An ordered, immutable sequence of candles for one symbol at one timeframe.
Candles are strictly ascending by timestamp (oldest first).
 */

public class CandleSeries {

    private final String symbol;
    private final TimeFrame timeframe;
    private final List<Candle> candles;

    public CandleSeries(String symbol, TimeFrame timeframe, List<Candle> candles) {
        if (symbol == null) {
            throw new IllegalArgumentException("Symbol not found");
        }
        if (symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol is required");
        }
        if (timeframe == null) {
            throw new IllegalArgumentException("Timeframe is required");
        }
        if (candles == null) {
            throw new IllegalArgumentException("Candles are required");
        }

        // Defensive copy, so later changes to the caller's list can't alter this series
        List<Candle> copy = new ArrayList<>(candles);

        for (int i = 0; i < copy.size(); i++) {
            if (copy.get(i) == null) {
                throw new IllegalArgumentException("Null candle at index " + i);
            }
            if (i > 0 && copy.get(i).getTimestamp() <= copy.get(i - 1).getTimestamp()) {
                throw new IllegalArgumentException(
                        "Candles must be strictly ascending by timestamp (problem at index " + i + ")");
            }
        }

        this.symbol = symbol;
        this.timeframe = timeframe;
        this.candles = Collections.unmodifiableList(copy); // can I use this?
    }

    public String getSymbol() { return symbol; }
    public TimeFrame getTimeframe() { return timeframe; }

    /** Getters. Read only. */
    public List<Candle> getCandles() { return candles; }

    public int size() { return candles.size(); }
    public boolean isEmpty() { return candles.isEmpty(); }
    public Candle get(int index) { return candles.get(index); }

    public Candle latest() {
        if (candles.isEmpty()) {
            throw new IllegalStateException("No candles for " + symbol);
        }
        return candles.get(candles.size() - 1);
    }

    /** A new series containing only the most recent n candles. */
    public CandleSeries lastN(int n) {
        if (n < 0) throw new IllegalArgumentException("n must be >= 0");
        int from = Math.max(0, candles.size() - n);
        return new CandleSeries(symbol, timeframe, candles.subList(from, candles.size()));
    }

    // Convenience extractors, mainly for indicator calculations
    public List<Double> closes() {
        List<Double> out = new ArrayList<>(candles.size());
        for (Candle c : candles) out.add(c.getClose());
        return out;
    }

    public List<Double> highs() {
        List<Double> out = new ArrayList<>(candles.size());
        for (Candle c : candles) out.add(c.getHigh());
        return out;
    }

    public List<Double> lows() {
        List<Double> out = new ArrayList<>(candles.size());
        for (Candle c : candles) out.add(c.getLow());
        return out;
    }

    public List<Long> timestamps() {
        List<Long> out = new ArrayList<>(candles.size());
        for (Candle c : candles) out.add(c.getTimestamp());
        return out;
    }

}
