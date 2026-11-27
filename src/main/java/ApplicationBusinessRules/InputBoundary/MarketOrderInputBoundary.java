package ApplicationBusinessRules.InputBoundary;

import ApplicationBusinessRules.InputData.MarketOrderInputData;

public interface MarketOrderInputBoundary {

    void execute(MarketOrderInputData inputData);
}
