package Entities;

import java.math.BigDecimal;

public class LimitOrder extends Order
{
    public LimitOrder(String company, String buyOrSell, int quantity, BigDecimal limitPrice)
    {
        super(company, buyOrSell, quantity, limitPrice);
    }

    public BigDecimal getLimitPrice()
    {
        return getPrice();
    }

    @Override
    public boolean canExecuteAt(BigDecimal marketPrice)
    {
        int comparison = marketPrice.compareTo(getLimitPrice());
        if (isBuy())
        {
            return comparison <= 0;
        }

        return comparison >= 0;
    }
}
