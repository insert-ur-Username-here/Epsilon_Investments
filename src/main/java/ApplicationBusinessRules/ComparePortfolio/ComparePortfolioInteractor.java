package ApplicationBusinessRules.ComparePortfolio;

import Entities.Portfolio;
import Entities.PortfolioSnapshot;
import ApplicationBusinessRules.DataAccessInterface;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.*;

public class ComparePortfolioInteractor implements ComparePortfolioInputBoundary {

    private static final BigDecimal ZERO = BigDecimal.ZERO;
    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final int TRADING_DAYS_PER_YEAR = 252; // Approximately 252 trading days in a year
    private final DataAccessInterface DataAccessInterface;
    private final PerformanceDataGateway performanceDataGateway;
    private final ComparePortfolioOutputBoundary outputBoundary;

    // Constructor
    public ComparePortfolioInteractor(
            DataAccessInterface DataAccessInterface,
            PerformanceDataGateway performanceDataGateway,
            ComparePortfolioOutputBoundary outputBoundary
    ) {
        this.DataAccessInterface = Objects.requireNonNull(
                DataAccessInterface
        );

        this.performanceDataGateway = Objects.requireNonNull(
                performanceDataGateway
        );

        this.outputBoundary = Objects.requireNonNull(
                outputBoundary
        );
    }


    // Execute
    @Override
    public void execute(ComparePortfolioInputData inputData) {
        try {
            // Validate inputs
            if (inputData == null) {
                throw new IllegalArgumentException(
                        "Comparison input cannot be null."
                );
            }
            List<UUID> portfolioIds = getUuids(inputData);
            PortfolioMetric metric = inputData.getMetric();
            if (metric == null) {
                throw new IllegalArgumentException(
                        "A comparison metric must be selected."
                );
            }
            LocalDate startDate = inputData.getStartDate();
            LocalDate endDate = inputData.getEndDate();
            Objects.requireNonNull(startDate, "Start date cannot be null.");
            Objects.requireNonNull(endDate, "End date cannot be null.");
            if (!startDate.isBefore(endDate)) {
                throw new IllegalArgumentException(
                        "Start date must be before end date."
                );
            }

            // Retrieve portfolios
            Map<UUID, Portfolio> portfolios = new LinkedHashMap<>();

            for (UUID portfolioId : portfolioIds) {
                if (portfolioId == null) {
                    throw new IllegalArgumentException(
                            "Portfolio ID cannot be null."
                    );
                }
                Portfolio portfolio = DataAccessInterface
                        .findById(portfolioId)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "Portfolio not found: " + portfolioId
                        ));

                portfolios.put(portfolioId, portfolio);
            }

            // Generate daily return series for each portfolio
            Map<UUID, Map<LocalDate, BigDecimal>> portfolioReturns =
                    new LinkedHashMap<>();

            for (Map.Entry<UUID, Portfolio> entry : portfolios.entrySet()) {
                Map<LocalDate, BigDecimal> returns =
                        calculateDailyReturns(
                                entry.getValue(),
                                startDate,
                                endDate
                        );
                portfolioReturns.put(entry.getKey(), returns);
            }

            // Portfolios must have same obs date
            Set<LocalDate> commonDates = validateAlignedReturns(
                    portfolioReturns
            );

            int observations = commonDates.size();
            if (metric == PortfolioMetric.JENSENS_ALPHA
                    && observations < 2) {
                throw new IllegalArgumentException(
                        "At least two return observations are required "
                                + "for Jensen's alpha."
                );
            }

            // Additional info for Jenson's Alpha
            Map<LocalDate, BigDecimal> benchmarkReturns = null;
            Map<LocalDate, BigDecimal> riskFreeReturns = null;
            String benchmarkSymbol = null;

            if (metric == PortfolioMetric.JENSENS_ALPHA) {
                benchmarkSymbol = inputData.getBenchmarkSymbol();
                if (benchmarkSymbol == null || benchmarkSymbol.isBlank()) {
                    throw new IllegalArgumentException(
                            "A benchmark symbol is required for Jensen's alpha."
                    );
                }
                benchmarkReturns =
                        performanceDataGateway.getBenchmarkDailyReturns(
                                benchmarkSymbol,
                                startDate,
                                endDate
                        );
                riskFreeReturns =
                        performanceDataGateway.getRiskFreeDailyReturns(
                                startDate,
                                endDate
                        );
                validateExternalReturns(
                        benchmarkReturns,
                        riskFreeReturns,
                        commonDates
                );
            }

            // Calculate selected metric for each portfolio
            List<PortfolioMetricResult> results = new ArrayList<>();

            for (Map.Entry<UUID, Portfolio> entry : portfolios.entrySet()) {

                UUID portfolioId = entry.getKey();
                Portfolio portfolio = entry.getValue();
                BigDecimal metricValue;

                switch (metric) {
                    // Case-by-case
                    case TOTAL_RETURN:
                        metricValue = calculateTotalReturn(
                                portfolioReturns.get(portfolioId),
                                commonDates
                        );
                        break;

                    case JENSENS_ALPHA:
                        metricValue = calculateJensensAlpha(
                                portfolioReturns.get(portfolioId),
                                benchmarkReturns,
                                riskFreeReturns,
                                commonDates
                        );
                        break;

                    default:
                        throw new IllegalArgumentException(
                                "Unsupported comparison metric: " + metric
                        );
                }

                results.add(
                        new PortfolioMetricResult(
                                portfolioId,
                                portfolio.getPortfolioName(),
                                metricValue
                        )
                );
            }

            ComparePortfolioOutputData outputData =
                    new ComparePortfolioOutputData(
                            metric,
                            startDate,
                            endDate,
                            benchmarkSymbol,
                            observations,
                            results
                    );

            outputBoundary.prepareSuccessView(outputData);
        } catch (IllegalArgumentException | NullPointerException exception) {
            outputBoundary.prepareFailView(
                    exception.getMessage()
            );
        }
    }

    private static List<UUID> getUuids(ComparePortfolioInputData inputData) {
        List<UUID> portfolioIds = inputData.getPortfolioIds();

        // Checks
        if (portfolioIds == null || portfolioIds.size() < 2) {
            throw new IllegalArgumentException(
                    "At least two portfolios are required."
            );
        }
        if (new HashSet<>(portfolioIds).size() != portfolioIds.size()) {
            throw new IllegalArgumentException(
                    "Duplicate portfolio IDs are not allowed."
            );
        }

        return portfolioIds;
    }


    // Portfolio return calculations
    private Map<LocalDate, BigDecimal> calculateDailyReturns(
            Portfolio portfolio,
            LocalDate startDate,
            LocalDate endDate
    ) {

        NavigableMap<LocalDate, PortfolioSnapshot> snapshots = portfolio.getDailySnapshots();

        if (!snapshots.containsKey(startDate) || !snapshots.containsKey(endDate)) {
            throw new IllegalArgumentException(
                    "Portfolio " + portfolio.getPortfolioName()
                            + " is missing a snapshot at the requested start or end date."
            );
        }

        BigDecimal previousValue = snapshots.get(startDate).getPortfolioValue();

        if (previousValue.compareTo(ZERO) <= 0) {
            throw new IllegalArgumentException("Portfolio starting value must be positive.");
        }

        Map<LocalDate, BigDecimal> dailyReturns = new TreeMap<>();

        for (Map.Entry<LocalDate, PortfolioSnapshot> entry :
                snapshots.subMap(
                        startDate, false,
                        endDate, true
                ).entrySet()) {

            PortfolioSnapshot currentSnapshot = entry.getValue();
            BigDecimal endingValue = currentSnapshot.getPortfolioValue();
            BigDecimal externalCashFlow = currentSnapshot.getNetExternalCashFlow();

            // Remove external contributions from ending
            BigDecimal adjustedEndingValue = endingValue.subtract(externalCashFlow);

            // Return = (Adjusted ending value / Beginning value) - 1.
            BigDecimal dailyReturn = adjustedEndingValue.divide(
                    previousValue,
                    MathContext.DECIMAL128
            ).subtract(ONE);

            dailyReturns.put(entry.getKey(), dailyReturn);
            previousValue = endingValue;
        }

        return dailyReturns;
    }


    // Ensure return dates are aligned
    private Set<LocalDate> validateAlignedReturns(
            Map<UUID, Map<LocalDate, BigDecimal>> portfolioReturns
    ) {
        Set<LocalDate> commonDates = null;
        for (Map<LocalDate, BigDecimal> returns : portfolioReturns.values()) {
            if (commonDates == null) {
                commonDates = new TreeSet<>(returns.keySet());
            }
            else if (!commonDates.equals(returns.keySet())) {
                throw new IllegalArgumentException("Portfolios must have matching snapshot dates "
                                + "for a fair comparison."
                );
            }
        }
        if (commonDates == null || commonDates.isEmpty()) {
            throw new IllegalArgumentException("No historical return data is available.");
        }
        return commonDates;
    }


    // Validate benchmark and risk-free returns
    private void validateExternalReturns(
            Map<LocalDate, BigDecimal> benchmarkReturns,
            Map<LocalDate, BigDecimal> riskFreeReturns,
            Set<LocalDate> dates
    ) {
        Objects.requireNonNull(benchmarkReturns, "Benchmark returns are unavailable.");
        Objects.requireNonNull(riskFreeReturns, "Risk-free returns are unavailable.");

        for (LocalDate date : dates) {
            if (!benchmarkReturns.containsKey(date) || !riskFreeReturns.containsKey(date)) {
                throw new IllegalArgumentException("Missing benchmark or risk-free return for " + date);
            }
            Objects.requireNonNull(benchmarkReturns.get(date), "Benchmark return cannot be null.");
            Objects.requireNonNull(riskFreeReturns.get(date), "Risk-free return cannot be null.");
        }
    }


    // Total return
    private BigDecimal calculateTotalReturn(
            Map<LocalDate, BigDecimal> returns,
            Set<LocalDate> dates
    ) {

        BigDecimal cumulativeGrowth = ONE;

        for (LocalDate date : dates) {
            // Compound individual daily returns.
            cumulativeGrowth = cumulativeGrowth.multiply(
                    ONE.add(returns.get(date)),
                    MathContext.DECIMAL128
            );
        }
        return cumulativeGrowth.subtract(ONE);
    }


    // Jensen's alpha
    // TODO: Change format to be helpers in calculating sd, variance, covariance, etc to help with beta, alpha, etc
    private BigDecimal calculateJensensAlpha(
            Map<LocalDate, BigDecimal> portfolioReturns,
            Map<LocalDate, BigDecimal> benchmarkReturns,
            Map<LocalDate, BigDecimal> riskFreeReturns,
            Set<LocalDate> dates
    ) {

        int n = dates.size();
        double[] rp = new double[n];
        double[] rm = new double[n];
        double[] rf = new double[n];
        int index = 0;

        for (LocalDate date : dates) {
            rp[index] = portfolioReturns.get(date).doubleValue();
            rm[index] = benchmarkReturns.get(date).doubleValue();
            rf[index] = riskFreeReturns.get(date).doubleValue();
            index++;
        }


        // Calculate average daily returns.
        double meanPortfolio = mean(rp);
        double meanMarket = mean(rm);
        double meanRiskFree = mean(rf);


        // Calculate benchmark variance and portfolio/benchmark covariance.
        double covariance = 0.0;
        double marketVariance = 0.0;

        for (int i = 0; i < n; i++) {
            double marketDifference = rm[i] - meanMarket;
            covariance +=
                    (rp[i] - meanPortfolio) * marketDifference;
            marketVariance +=
                    marketDifference * marketDifference;
        }

        covariance /= (n - 1);
        marketVariance /= (n - 1);

        if (marketVariance <= 0.0) {
            throw new IllegalArgumentException(
                    "Benchmark returns have zero variance; beta cannot be calculated."
            );
        }

        // Beta = Covariance(Rp, Rm) / Variance(Rm).
        double beta = covariance / marketVariance;


        // Jensen's alpha:
        double dailyAlpha = meanPortfolio
                - (meanRiskFree + beta * (meanMarket - meanRiskFree));

        // Annualize daily alpha using approximately 252 trading days.
        double annualizedAlpha =
                dailyAlpha * TRADING_DAYS_PER_YEAR;
        return BigDecimal.valueOf(annualizedAlpha);
    }


    // Helpers
    private double mean(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }
}
