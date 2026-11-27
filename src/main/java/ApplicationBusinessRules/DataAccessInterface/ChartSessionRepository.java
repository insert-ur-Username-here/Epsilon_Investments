package ApplicationBusinessRules.DataAccessInterface;

/* Interface Overview:
Holds the chart the user is currently looking at, so separate use cases
(change indicators, refresh, change timeframe, stop) can share one state.
 */

import ApplicationBusinessRules.UseCaseInteractor.ActiveChart;

public interface ChartSessionRepository {

    void save(ActiveChart chart);

    /** The active chart, or null if none has been loaded. */
    ActiveChart current();

    void clear();
}