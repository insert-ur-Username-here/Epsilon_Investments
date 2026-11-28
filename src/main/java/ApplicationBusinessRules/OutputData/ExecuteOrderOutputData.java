package ApplicationBusinessRules.OutputData;

import java.math.BigDecimal;
import java.util.List;

public class ExecuteOrderOutputData
{
    private final List<Fill> fills;
    private final BigDecimal cashBalance;

    public ExecuteOrderOutputData(List<Fill> fills, BigDecimal cashBalance)
    {
        this.fills = List.copyOf(fills);
        this.cashBalance = cashBalance;
    }

    public List<Fill> getFills()
    {
        return fills;
    }

    public BigDecimal getCashBalance()
    {
        return cashBalance;
    }

    public static class Fill
    {
        private final String company;
        private final String buyOrSell;
        private final int quantity;
        private final BigDecimal executionPrice;

        public Fill(String company, String buyOrSell, int quantity, BigDecimal executionPrice)
        {
            this.company = company;
            this.buyOrSell = buyOrSell;
            this.quantity = quantity;
            this.executionPrice = executionPrice;
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

        public BigDecimal getExecutionPrice()
        {
            return executionPrice;
        }
    }
}
