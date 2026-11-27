package InterfaceAdapter.Controller;

import ApplicationBusinessRules.InputBoundary.MarketOrderInputBoundary;
import ApplicationBusinessRules.InputData.MarketOrderInputData;

import java.math.BigDecimal;

public class MarketOrderController {

    private MarketOrderInputBoundary useCase;

    public MarketOrderController(MarketOrderInputBoundary useCase)
    {
        this.useCase = useCase;
    }

    public void onMarketOrderClick(String company, String buyOrSell, int quantity)
    {
        MarketOrderInputData inputData = new MarketOrderInputData(company, buyOrSell.toUpperCase(), quantity);
        useCase.execute(inputData);
    }
}
