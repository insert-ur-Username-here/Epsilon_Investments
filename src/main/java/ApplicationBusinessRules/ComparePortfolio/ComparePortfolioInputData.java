package ApplicationBusinessRules.ComparePortfolio;

import java.time.LocalDate;
import java.util.*;

public class ComparePortfolioInputData {

    // Portfolios being compared
    private final List<UUID> portfolioIds;

    // Selected financial metric
    private final PortfolioMetric metric;

    // Comparison timeframe
    private final LocalDate startDate;
    private final LocalDate endDate;

    // Required for metrics such as Jensen's alpha
    private final String benchmarkSymbol;


    // Constructor
    public ComparePortfolioInputData(
            List<UUID> portfolioIds,
            PortfolioMetric metric,
            LocalDate startDate,
            LocalDate endDate,
            String benchmarkSymbol
    ) {

        // Defensive copy to prevent external modification
        this.portfolioIds = portfolioIds == null
                ? null
                : Collections.unmodifiableList(
                new ArrayList<>(portfolioIds)
        );

        this.metric = metric;
        this.startDate = startDate;
        this.endDate = endDate;
        this.benchmarkSymbol = benchmarkSymbol;
    }


    // Getters

    public List<UUID> getPortfolioIds() {
        return this.portfolioIds;
    }

    public PortfolioMetric getMetric() {
        return this.metric;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public String getBenchmarkSymbol() {
        return this.benchmarkSymbol;
    }
}