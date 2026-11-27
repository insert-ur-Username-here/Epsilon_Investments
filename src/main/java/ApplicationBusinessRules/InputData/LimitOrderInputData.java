package ApplicationBusinessRules.InputData;

import java.math.BigDecimal;

public class LimitOrderInputData
{
    private final String company;
    private final String buyOrSell;
    private final int quantity;
    private final BigDecimal limitPrice;

    public LimitOrderInputData(String company, String buyOrSell, int quantity, BigDecimal limitPrice)
    {
        this.company = company;
        this.buyOrSell = buyOrSell;
        this.quantity = quantity;
        this.limitPrice = limitPrice;
    }

    public String getCompany()
    {
        return company;
    }

    public String getBuyOrSell()
    {
        return buyOrSell;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public BigDecimal getLimitPrice()
    {
        return limitPrice;
    }
}
