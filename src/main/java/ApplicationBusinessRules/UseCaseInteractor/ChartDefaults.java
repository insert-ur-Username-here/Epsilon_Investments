package ApplicationBusinessRules.UseCaseInteractor;

import Entities.TimeFrame;

/* Class Overview:
Settings shared by LoadChart and ChangeTimeFrame, defined once so they can't drift apart.
 */
public final class ChartDefaults {

    /** Cap on finished candles kept in a session; also the most candles a user can request. */
    public static final int MAX_CANDLES = 1000;

    private static final long DAY = 86_400L;

    private ChartDefaults() {}

    /**
     * Start of the time range to request so that, after nights, weekends and holidays,
     * at least candleCount candles come back. A heuristic: ask for more than needed
     * and let CandleSeries.lastN trim. The gateway may clamp it to what the provider allows.
     */
    public static long fetchFrom(long to, int candleCount, TimeFrame timeFrame) {
        long span = candleCount * timeFrame.getSeconds();
        if (timeFrame.getSeconds() < DAY) {
            // Intraday: markets are open roughly a fifth of the week; add 4 days for weekends/holidays
            return to - 8L * span - 4L * DAY;
        }
        // Daily and weekly: only weekends and holidays are missing
        return to - 2L * span;
    }
}