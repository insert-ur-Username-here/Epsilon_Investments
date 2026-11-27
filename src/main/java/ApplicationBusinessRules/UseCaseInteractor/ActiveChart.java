package ApplicationBusinessRules.UseCaseInteractor;

import Entities.Indicator;
import Entities.TimeFrame;

import java.util.List;

/* Class Overview:
Everything the chart view is currently showing: the live session, how many candles to show,
and the selected indicators. The ActiveChart itself is immutable ("changes" return a copy);
the session inside is mutable but thread-safe. The symbol and timeframe come from the session,
so they can never disagree with it.
 */
public final class ActiveChart {
    private final LiveChartSession session;
    private final int candleCount;
    private final List<Indicator> indicators;

    public ActiveChart(LiveChartSession session, int candleCount, List<Indicator> indicators) {
        if (session == null) {
            throw new IllegalArgumentException("Session is required");
        }
        if (candleCount < 1) {
            throw new IllegalArgumentException("Candle count must be at least 1");
        }
        if (indicators == null) {
            throw new IllegalArgumentException("Indicators are required (use an empty list for none)");
        }
        for (Indicator indicator : indicators) {
            if (indicator == null) {
                throw new IllegalArgumentException("Null indicator in list");
            }
        }
        this.session = session;
        this.candleCount = candleCount;
        this.indicators = List.copyOf(indicators);   // immutable copy (Java 10+)
    }

    public LiveChartSession getSession() { return session; }
    public int getCandleCount() { return candleCount; }
    public List<Indicator> getIndicators() { return indicators; }

    public String getSymbol() { return session.getSymbol(); }
    public TimeFrame getTimeFrame() { return session.getTimeFrame(); }

    /** Same chart settings, new session (used when the timeframe changes). */
    public ActiveChart withSession(LiveChartSession newSession) {
        return new ActiveChart(newSession, candleCount, indicators);
    }

    /** Same session, different indicators. */
    public ActiveChart withIndicators(List<Indicator> newIndicators) {
        return new ActiveChart(session, candleCount, newIndicators);
    }
}