package ApplicationBusinessRules.OutputData;

import java.math.BigDecimal;

public class MarketOrderOutputData {

    private boolean success;
    private String company;
    private String buyOrSell;
    private int quantity;
    private BigDecimal price;
    private BigDecimal newCashBalance;

    public MarketOrderOutputData(boolean success, String company, String buyOrSell, int quantity, BigDecimal price, BigDecimal newCashBalance)
    {
        this.success = success;
        this.company = company;
        this.buyOrSell = buyOrSell;
        this.quantity = quantity;
        this.price = price;
        this.newCashBalance = newCashBalance;
    }

    public MarketOrderOutputData(boolean success)
    {
        this.success = success;
    }

    public boolean getSuccess()
    {
        return this.success;
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

    public BigDecimal getPrice()
    {
        return this.price;
    }

    public BigDecimal getNewCashBalance()
    {
        return this.newCashBalance;
    }
}
