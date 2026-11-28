package ApplicationBusinessRules.UseCaseInteractor;

import ApplicationBusinessRules.DataAccessInterface.ChartSessionRepository;
import ApplicationBusinessRules.InputBoundary.RefreshLiveChartInputBoundary;
import ApplicationBusinessRules.OutputBoundary.RefreshLiveChartOutputBoundary;
import ApplicationBusinessRules.OutputBoundary.RefreshLiveChartOutputData;
import Entities.CandleSeries;

/* Class Overview:
Presents the current live state. In memory only. Called by the view's timer.
 */
public class RefreshLiveChart implements RefreshLiveChartInputBoundary {
    private final ChartSessionRepository sessions;
    private final RefreshLiveChartOutputBoundary presenter;

    public RefreshLiveChart(ChartSessionRepository sessions,
                            RefreshLiveChartOutputBoundary presenter) {
        this.sessions = sessions;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        ActiveChart chart = sessions.current();
        if (chart == null) {
            presenter.prepareFailView("No chart loaded.");
            return;
        }

        try {
            LiveChartSession session = chart.getSession();
            CandleSeries snapshot = session.snapshot();
            presenter.prepareSuccessView(new RefreshLiveChartOutputData(
                    snapshot, session.getQuote(),
                    IndicatorRunner.run(chart.getIndicators(), snapshot)));
        } catch (IllegalArgumentException | IllegalStateException e) {
            presenter.prepareFailView(e.getMessage());
        }
    }

}
