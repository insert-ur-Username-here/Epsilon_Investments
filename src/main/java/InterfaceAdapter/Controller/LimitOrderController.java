package InterfaceAdapter.Controller;

import ApplicationBusinessRules.InputBoundary.LimitOrderInputBoundary;
import ApplicationBusinessRules.InputData.LimitOrderInputData;

import java.math.BigDecimal;
import java.util.Locale;

public class LimitOrderController
{
    private final LimitOrderInputBoundary useCase;

    public LimitOrderController(LimitOrderInputBoundary useCase)
    {
        this.useCase = useCase;
    }

    public void onLimitOrderClick(String company, String buyOrSell, int quantity, BigDecimal limitPrice)
    {
        LimitOrderInputData inputData = new LimitOrderInputData(company, buyOrSell.toUpperCase(Locale.ROOT), quantity, limitPrice);
        useCase.execute(inputData);
    }
}
