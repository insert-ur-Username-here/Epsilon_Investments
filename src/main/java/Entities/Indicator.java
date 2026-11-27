package Entities;

/* Interface Overview:
A technical indicator: a pure calculation from finished candles to an IndicatorSeries.

Notes for implementations:
 - Never modify the input and never return null.
 - The result has the same timestamps, in the same order, as the input candles
   (exactly one value per candle, per line).
 - Positions without enough history yet (the warm-up period) return NaN.
 - An empty input gives an empty result.
 */

public interface Indicator {

    /** Display name including its parameters, e.g. "SMA(20)". Never blank. */
    String name();

    /** Computes this indicator over the given candles. */
    IndicatorSeries calculate(CandleSeries candles);
}