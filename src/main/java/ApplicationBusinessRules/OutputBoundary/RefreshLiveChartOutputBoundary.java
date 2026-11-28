package ApplicationBusinessRules.OutputBoundary;

public interface RefreshLiveChartOutputBoundary {
    void prepareSuccessView(RefreshLiveChartOutputData data);
    void prepareFailView(String message);
}
