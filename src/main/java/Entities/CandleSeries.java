package Entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Class Overview:
An ordered, immutable sequence of finished candles for one symbol at one timeframe.
Candles are strictly ascending by timestamp (oldest first).
Every "modifying" method returns a new series; this one never changes.
 */

public final class CandleSeries {

    private final String symbol;
    private final TimeFrame timeframe;
    private final List<Candle> candles;

    public CandleSeries(String symbol, TimeFrame timeframe, List<Candle> candles) {
        if (symbol == null || symbol.isBlank()) {
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

        this.symbol = symbol.trim().toUpperCase();   // same normalization as Quote and Trade
        this.timeframe = timeframe;
        this.candles = Collections.unmodifiableList(copy);
    }

    /** Getters */
    public String getSymbol() { return symbol; }
    public TimeFrame getTimeFrame() { return timeframe; }

    /** Read-only view; modifying it throws UnsupportedOperationException. */
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
        if (n >= candles.size()) return this;   // already small enough; safe to share since immutable
        return new CandleSeries(symbol, timeframe,
                candles.subList(candles.size() - n, candles.size()));
    }

    /** A new series with the candle added at the end. It must be newer than the current last candle. */
    public CandleSeries append(Candle candle) {
        if (candle == null) {
            throw new IllegalArgumentException("Candle is required");
        }
        List<Candle> next = new ArrayList<>(candles.size() + 1);
        next.addAll(candles);
        next.add(candle);
        return new CandleSeries(symbol, timeframe, next);   // constructor re-checks the ordering
    }

    /** A new series without the last candle. */
    public CandleSeries withoutLast() {
        if (candles.isEmpty()) {
            throw new IllegalStateException("No candles for " + symbol);
        }
        return new CandleSeries(symbol, timeframe, candles.subList(0, candles.size() - 1));
    }

    // Convenience extractors, mainly for indicator calculations
    public List<Double> opens() {
        List<Double> out = new ArrayList<>(candles.size());
        for (Candle c : candles) out.add(c.getOpen());
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

    public List<Double> closes() {
        List<Double> out = new ArrayList<>(candles.size());
        for (Candle c : candles) out.add(c.getClose());
        return out;
    }

    public List<Long> volumes() {
        List<Long> out = new ArrayList<>(candles.size());
        for (Candle c : candles) out.add(c.getVolume());
        return out;
    }

    public List<Long> timestamps() {
        List<Long> out = new ArrayList<>(candles.size());
        for (Candle c : candles) out.add(c.getTimestamp());
        return out;
    }

    @Override
    public String toString() {
        return "CandleSeries[" + symbol + ", " + timeframe + ", " + candles.size() + " candles]";
    }
}