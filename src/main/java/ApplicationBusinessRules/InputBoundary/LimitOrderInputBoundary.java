package ApplicationBusinessRules.InputBoundary;

import ApplicationBusinessRules.InputData.LimitOrderInputData;

public interface LimitOrderInputBoundary
{
    void execute(LimitOrderInputData inputData);
}
