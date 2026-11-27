package ApplicationBusinessRules.DataAccessInterface;

import java.math.BigDecimal;

public interface StockDataAccessInterface {

    BigDecimal getStockPrice(String company);
}
