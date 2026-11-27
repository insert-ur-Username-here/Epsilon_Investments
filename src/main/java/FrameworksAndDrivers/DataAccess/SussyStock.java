package FrameworksAndDrivers.DataAccess;

import ApplicationBusinessRules.DataAccessInterface.StockDataAccessInterface;

import java.math.BigDecimal;

public class SussyStock implements StockDataAccessInterface {

    @Override
    public BigDecimal getStockPrice(String company) {
        return BigDecimal.valueOf(67);
    }
}
