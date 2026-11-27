package ApplicationBusinessRules.ComparePortfolio;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.Objects;

public class PortfolioMetricResult {

    private final UUID portfolioId;
    private final String portfolioName;
    private final BigDecimal metricValue;

    // Constructor
    public PortfolioMetricResult(
            UUID portfolioId,
            String portfolioName,
            BigDecimal metricValue
    ) {
        this.portfolioId = Objects.requireNonNull(portfolioId);
        this.portfolioName = Objects.requireNonNull(portfolioName);
        this.metricValue = Objects.requireNonNull(metricValue);
    }

    // Getters
    public UUID getPortfolioId() {
        return portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public BigDecimal getMetricValue() {
        return metricValue;
    }
}
