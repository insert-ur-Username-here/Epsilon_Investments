package ApplicationBusinessRules.OutputBoundary;

public interface ChangeTimeFrameOutputBoundary {
    void prepareSuccessView(ChangeTimeFrameOutputData data);
    void prepareFailView(String message);

}

