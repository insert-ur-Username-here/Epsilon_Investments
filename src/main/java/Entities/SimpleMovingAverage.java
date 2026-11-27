package Entities;

import java.util.ArrayList;
import java.util.List;

/* Class Overview:
Simple Moving Average: the plain average of the last `period` closing prices.
Immutable (its only state is the period), so one instance can be reused and shared freely.
Follows the Indicator contract: one value per candle, NaN during the warm-up period
(the first period - 1 candles), and an empty result for an empty input.
 */
public final class SimpleMovingAverage implements Indicator {

    private final int period; // period that the SMA is calculated over; in candles

    public SimpleMovingAverage(int period) {
        if (period < 1) {
            throw new IllegalArgumentException("Period must be at least 1");
        }
        this.period = period;
    }

    /** Getter */
    public int getPeriod() {
        return period;
    }

    @Override
    public String name() {
        return "SMA(" + period + ")";
    }

    @Override
    public IndicatorSeries calculate(CandleSeries candles) {
        if (candles == null) {
            throw new IllegalArgumentException("Candles are required");
        }

        List<Double> closes = candles.closes();
        List<Double> values = new ArrayList<>(closes.size());

        double sum = 0;   // sum of the closes currently inside the window
        for (int i = 0; i < closes.size(); i++) {
            sum += closes.get(i);                    // newest close enters the window
            if (i >= period) {
                sum -= closes.get(i - period);       // oldest close leaves the window
            }
            values.add(i >= period - 1 ? sum / period : Double.NaN);
        }

        return IndicatorSeries.singleLine(name(), candles.timestamps(), values);
    }
}