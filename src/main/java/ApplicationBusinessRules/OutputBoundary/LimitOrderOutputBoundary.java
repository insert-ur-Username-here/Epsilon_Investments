package ApplicationBusinessRules.OutputBoundary;

import ApplicationBusinessRules.OutputData.LimitOrderOutputData;

public interface LimitOrderOutputBoundary {

    void successView(LimitOrderOutputData outputData);

    void failView(LimitOrderOutputData outputData);

}
