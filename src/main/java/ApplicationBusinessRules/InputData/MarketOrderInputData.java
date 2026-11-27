package ApplicationBusinessRules.InputData;

import java.math.BigDecimal;

public class MarketOrderInputData {

    private String company;
    private String buyOrSell;
    private int quantity;
    private BigDecimal price;

    public MarketOrderInputData(String company, String buyOrSell, int quantity)
    {
        this.company = company;
        this.buyOrSell = buyOrSell;
        this.quantity = quantity;
    }

    public String getCompany()
    {
        return this.company;
    }

    public String getBuyOrSell()
    {
        return this.buyOrSell;
    }

    public int getQuantity()
    {
        return this.quantity;
    }
}
