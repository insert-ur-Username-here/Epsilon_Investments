package Entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* Class Overview:
The result of an Indicator: one or more named lines of values, aligned index-by-index
with a list of timestamps (the candles it was computed from). Immutable.
Positions with insufficient history (still in "warmup") hold NaN.
Single-line indicators (SMA, EMA, RSI) use the line name VALUE; multi-line indicators
(MACD, Bollinger Bands) use their own names, in the order they were supplied.
 */

public final class IndicatorSeries {

    public static final String VALUE = "value";

    private final String name; // type of indicator (ex. MACD, SMA, RSI)
    private final List<Long> timestamps; // seconds, strictly ascending (old -> new)
    private final Map<String, List<Double>> lines; // insertion order preserved

    /** Pass a LinkedHashMap if the order of the lines matters. */
    public IndicatorSeries(String name, List<Long> timestamps, Map<String, List<Double>> lines) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (timestamps == null) {
            throw new IllegalArgumentException("Timestamps are required");
        }
        if (lines == null || lines.isEmpty()) {
            throw new IllegalArgumentException("At least one line is required");
        }

        // Defensive copies
        List<Long> timeCopy = new ArrayList<>(timestamps);
        for (int i = 0; i < timeCopy.size(); i++) {
            if (timeCopy.get(i) == null) {
                throw new IllegalArgumentException("Null timestamp at index " + i);
            }
            if (i > 0 && timeCopy.get(i) <= timeCopy.get(i - 1)) {
                throw new IllegalArgumentException(
                        "Timestamps must be strictly ascending (problem at index " + i + ")");
            }
        }

        Map<String, List<Double>> lineCopy = new LinkedHashMap<>();
        for (Map.Entry<String, List<Double>> entry : lines.entrySet()) {
            String lineName = entry.getKey();
            List<Double> values = entry.getValue();

            if (lineName == null || lineName.isBlank()) {
                throw new IllegalArgumentException("Line names cannot be blank");
            }
            if (values == null) {
                throw new IllegalArgumentException("Line '" + lineName + "' has no values");
            }
            if (values.size() != timeCopy.size()) {
                throw new IllegalArgumentException("Line '" + lineName + "' has " + values.size()
                        + " values but there are " + timeCopy.size() + " timestamps");
            }

            List<Double> valueCopy = new ArrayList<>(values);
            for (int i = 0; i < valueCopy.size(); i++) {
                Double v = valueCopy.get(i);
                if (v == null) {
                    throw new IllegalArgumentException(
                            "Null value in line '" + lineName + "' at index " + i + " (use NaN)");
                }
                if (v.isInfinite()) {
                    throw new IllegalArgumentException(
                            "Infinite value in line '" + lineName + "' at index " + i);
                }
            }
            lineCopy.put(lineName, Collections.unmodifiableList(valueCopy));
        }

        this.name = name.trim();
        this.timestamps = Collections.unmodifiableList(timeCopy);
        this.lines = Collections.unmodifiableMap(lineCopy);
    }

    /** Convenience for single-line indicators. */
    public static IndicatorSeries singleLine(String name, List<Long> timestamps, List<Double> values) {
        Map<String, List<Double>> lines = new LinkedHashMap<>();
        lines.put(VALUE, values);
        return new IndicatorSeries(name, timestamps, lines);
    }

    /** Getters. All returned lists are read-only. */
    public String getName() { return name; }
    public List<Long> getTimestamps() { return timestamps; }

    public int size() { return timestamps.size(); }
    public boolean isEmpty() { return timestamps.isEmpty(); }

    /** Line names in the order they were supplied, e.g. [MACD, signal, histogram]. */
    public List<String> getLineNames() {
        return new ArrayList<>(lines.keySet());
    }

    public boolean hasLine(String lineName) {
        return lines.containsKey(lineName);
    }

    public List<Double> getLine(String lineName) {
        List<Double> values = lines.get(lineName);
        if (values == null) {
            throw new IllegalArgumentException("No line named '" + lineName + "' in " + name);
        }
        return values;
    }

    /** The values of a single-line indicator. */
    public List<Double> getValues() {
        if (lines.size() != 1) {
            throw new IllegalStateException(name + " has " + lines.size() + " lines; use getLine(name)");
        }
        return lines.values().iterator().next();
    }

    public double valueAt(String lineName, int index) {
        return getLine(lineName).get(index);
    }

    /** False while the indicator is still warming up. */
    public boolean isDefined(String lineName, int index) {
        return !Double.isNaN(valueAt(lineName, index));
    }

    /** The most recent value of a line. NaN if in warmup period. */
    public double latest(String lineName) {
        if (timestamps.isEmpty()) {
            throw new IllegalStateException("No values for " + name);
        }
        List<Double> values = getLine(lineName);
        return values.get(values.size() - 1);
    }

    @Override
    public String toString() {
        return "IndicatorSeries[" + name + ", lines=" + lines.keySet() + ", " + size() + " points]";
    }
}