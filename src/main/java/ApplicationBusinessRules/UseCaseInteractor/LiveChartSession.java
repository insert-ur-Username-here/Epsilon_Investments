package ApplicationBusinessRules.UseCaseInteractor;

import Entities.Candle;
import Entities.CandleSeries;
import Entities.CurrentCandle;
import Entities.Quote;
import Entities.TimeFrame;
import Entities.Trade;

/* Class Overview:
Live state for one symbol at one timeframe: finished candles, the forming candle, and the quote.
Mutable but thread-safe: every method touching the mutable state is synchronized, so the stream
thread can call onTrade() while the UI thread calls snapshot(). Both return immutable objects,
and the live CurrentCandle never leaves this class.
 */

public final class LiveChartSession {
    private final String symbol;
    private final TimeFrame timeframe;
    private final int maxCandles;     // cap on finished candles kept in memory

    private CandleSeries history;     // finished candles only
    private CurrentCandle current;    // the forming candle
    private Quote quote;

    private LiveChartSession(String symbol, TimeFrame timeframe, int maxCandles,
                             CandleSeries history, CurrentCandle current, Quote quote) {
        this.symbol = symbol;
        this.timeframe = timeframe;
        this.maxCandles = maxCandles;
        this.history = history;
        this.current = current;
        this.quote = quote;
    }

    /** Builds a session from freshly fetched REST data. */
    public static LiveChartSession start(CandleSeries fetched, Quote quote, int maxCandles) {
        if (fetched == null) {
            throw new IllegalArgumentException("Candles are required");
        }
        if (quote == null) {
            throw new IllegalArgumentException("Quote is required");
        }
        if (maxCandles < 1) {
            throw new IllegalArgumentException("maxCandles must be at least 1");
        }
        if (fetched.isEmpty()) {
            throw new IllegalArgumentException("Need at least one candle to anchor the time grid");
        }
        if (!fetched.getSymbol().equals(quote.getSymbol())) {
            throw new IllegalArgumentException("Series and quote are for different symbols");
        }

        TimeFrame tf = fetched.getTimeFrame();
        Candle last = fetched.latest();
        long step = tf.getSeconds();
        // A stale quote (older than the latest candle) is treated as if it were at that candle
        long quoteTime = Math.max(quote.getTimestamp(), last.getTimestamp());

        CandleSeries history;
        CurrentCandle current;
        if (quoteTime < last.getTimestamp() + step) {
            // The fetched last candle is still forming: take it out of history and resume it
            history = fetched.withoutLast();
            current = CurrentCandle.from(fetched.getSymbol(), tf, last);
        } else {
            // The last candle is complete: start a new one on the next interval boundary
            long start = last.getTimestamp() + ((quoteTime - last.getTimestamp()) / step) * step;
            history = fetched;
            current = new CurrentCandle(fetched.getSymbol(), tf, start, quote.getPrice());
        }
        return new LiveChartSession(fetched.getSymbol(), tf, maxCandles,
                history.lastN(maxCandles), current, quote);
    }

    /** Called from the stream thread for every trade. */
    public synchronized void onTrade(Trade trade) {
        if (!trade.getSymbol().equals(symbol)) {
            return;                                           // not our symbol
        }
        if (trade.getTimestamp() < current.getStartTime()) {
            return;                                           // belongs to an already finished candle
        }
        if (!current.covers(trade.getTimestamp())) {
            history = history.append(current.toCandle()).lastN(maxCandles);   // finalize
            current = current.rollOver(trade);                                // start the next one
        }
        current.update(trade);
        quote = quote.withTrade(trade);
    }

    /** Immutable series: finished candles plus the forming candle as the last bar. */
    public synchronized CandleSeries snapshot() {
        return history.append(current.toCandle());
    }

    public synchronized Quote getQuote() {
        return quote;
    }

    public String getSymbol() { return symbol; }              // final fields, no lock needed
    public TimeFrame getTimeFrame() { return timeframe; }
}