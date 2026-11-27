package ApplicationBusinessRules.ComparePortfolio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface PerformanceDataGateway {

    // Returns historical benchmark returns.
    Map<LocalDate, BigDecimal> getBenchmarkDailyReturns(
            String benchmarkSymbol,
            LocalDate startDate,
            LocalDate endDate
    );

    // Returns historical risk-free rates converted to the same daily frequency.
    Map<LocalDate, BigDecimal> getRiskFreeDailyReturns(
            LocalDate startDate,
            LocalDate endDate
    );
}
