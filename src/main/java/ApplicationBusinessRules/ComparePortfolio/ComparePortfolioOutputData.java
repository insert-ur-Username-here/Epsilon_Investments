package ApplicationBusinessRules.ComparePortfolio;

import java.time.LocalDate;
import java.util.*;

public class ComparePortfolioOutputData {

    private final PortfolioMetric metric;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String benchmarkSymbol;
    private final int observations;
    private final List<PortfolioMetricResult> results;

    public ComparePortfolioOutputData(
            PortfolioMetric metric,
            LocalDate startDate,
            LocalDate endDate,
            String benchmarkSymbol,
            int observations,
            List<PortfolioMetricResult> results
    ) {
        this.metric = metric;
        this.startDate = startDate;
        this.endDate = endDate;
        this.benchmarkSymbol = benchmarkSymbol;
        this.observations = observations;
        this.results = Collections.unmodifiableList(
                new ArrayList<>(results)
        );
    }

    // Getters
    public PortfolioMetric getMetric() {
        return metric;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getBenchmarkSymbol() {
        return benchmarkSymbol;
    }

    public int getObservations() {
        return observations;
    }

    public List<PortfolioMetricResult> getResults() {
        return results;
    }
}
