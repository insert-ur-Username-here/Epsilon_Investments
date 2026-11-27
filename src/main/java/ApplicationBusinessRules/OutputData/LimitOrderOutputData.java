package ApplicationBusinessRules.OutputData;

import java.math.BigDecimal;

public class LimitOrderOutputData {

    private boolean success;
    private String company;
    private String buyOrSell;
    private int quantity;
    private BigDecimal limitPrice;
    private BigDecimal newCashBalance;

    public LimitOrderOutputData(boolean success, String company, String buyOrSell, int quantity, BigDecimal limitPrice, BigDecimal newCashBalance)
    {
        this.success = success;
        this.company = company;
        this.buyOrSell = buyOrSell;
        this.quantity = quantity;
        this.limitPrice = limitPrice;
        this.newCashBalance = newCashBalance;
    }

    public LimitOrderOutputData(boolean success)
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

    public BigDecimal getLimitPrice()
    {
        return this.limitPrice;
    }

    public BigDecimal getNewCashBalance()
    {
        return this.newCashBalance;
    }
}
