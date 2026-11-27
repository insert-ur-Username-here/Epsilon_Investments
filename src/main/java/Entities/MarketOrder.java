package Entities;

import java.math.BigDecimal;

public class MarketOrder extends Order{

    public MarketOrder(String company, String buyOrSell, int quantity, BigDecimal price)
    {
        super(company, buyOrSell, quantity, price);
    }

    public boolean canExecuteAt(BigDecimal marketPrice)
    {
        return true;
    }
}
