package ApplicationBusinessRules.OutputBoundary;

public interface LoadChartOutputBoundary {
    void prepareSuccessView(LoadChartOutputData data);
    void prepareFailView(String message);
}