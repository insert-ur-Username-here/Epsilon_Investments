package ApplicationBusinessRules.UseCaseInteractor;

import ApplicationBusinessRules.DataAccessInterface.ChartSessionRepository;
import ApplicationBusinessRules.DataAccessInterface.TradeFeed;
import ApplicationBusinessRules.InputBoundary.StopLiveUpdatesInputBoundary;
import ApplicationBusinessRules.OutputBoundary.StopLiveUpdatesOutputBoundary;


/* Class Overview:
Stops the live stream and forgets the active chart. Safe to call twice
or with nothing loaded
 */

public class StopLiveUpdates implements StopLiveUpdatesInputBoundary{
    private final TradeFeed feed;
    private final ChartSessionRepository sessions;
    private final StopLiveUpdatesOutputBoundary presenter;

    public StopLiveUpdates(TradeFeed feed,
                           ChartSessionRepository sessions,
                           StopLiveUpdatesOutputBoundary presenter) {
        this.feed = feed;
        this.sessions = sessions;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        ActiveChart chart = sessions.current();
        sessions.clear();   // first, so any late trade finds no chart and is ignored

        try {
            if (chart != null) {
                feed.unsubscribe(chart.getSymbol());
            }
        } catch (RuntimeException e) {
            // already disconnected or never connected: nothing left to undo
        }
        try {
            feed.close();
        } catch (RuntimeException e) {
            // already closed
        }

        presenter.prepareSuccessView();
    }

}
