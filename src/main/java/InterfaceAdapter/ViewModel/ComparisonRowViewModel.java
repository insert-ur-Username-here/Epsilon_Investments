package InterfaceAdapter.ViewModel;

import java.util.UUID;

public class ComparisonRowViewModel {

    private final UUID portfolioId;
    private final String portfolioName;
    private final String metricValue;

    public ComparisonRowViewModel(
            UUID portfolioId,
            String portfolioName,
            String metricValue
    ) {
        this.portfolioId = portfolioId;
        this.portfolioName = portfolioName;
        this.metricValue = metricValue;
    }

    // Getters
    public UUID getPortfolioId() {
        return portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public String getMetricValue() {
        return metricValue;
    }
}