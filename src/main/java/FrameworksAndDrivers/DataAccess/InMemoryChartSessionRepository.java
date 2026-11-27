package FrameworksAndDrivers.DataAccess;

import ApplicationBusinessRules.DataAccessInterface.ChartSessionRepository;
import ApplicationBusinessRules.UseCaseInteractor.ActiveChart;

public class InMemoryChartSessionRepository implements ChartSessionRepository {

    private volatile ActiveChart chart;   // volatile: the stream and UI threads both read it immediately

    @Override
    public void save(ActiveChart chart) {
        if (chart == null) {
            throw new IllegalArgumentException("Chart is required");
        }
        this.chart = chart;
    }

    @Override
    public ActiveChart current() {
        return chart;
    }

    @Override
    public void clear() {
        chart = null;
    }
}