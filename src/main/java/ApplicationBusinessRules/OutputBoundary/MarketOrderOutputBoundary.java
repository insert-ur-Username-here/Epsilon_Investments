package ApplicationBusinessRules.OutputBoundary;

import ApplicationBusinessRules.OutputData.MarketOrderOutputData;

public interface MarketOrderOutputBoundary {

    void successView(MarketOrderOutputData outputData);

    void failView(MarketOrderOutputData outputData);

}
