package InterfaceAdapter.Controller;

import ApplicationBusinessRules.ComparePortfolio.*;
import java.time.LocalDate;
import java.util.*;

public class ComparePortfolioController {

    private final ComparePortfolioInputBoundary interactor;

    // Constructor
    public ComparePortfolioController(ComparePortfolioInputBoundary interactor) {
        this.interactor = interactor;
    }

    // Call
    public void comparePortfolios(
            List<UUID> portfolioIds,
            PortfolioMetric metric,
            LocalDate startDate,
            LocalDate endDate,
            String benchmarkSymbol
    ) {
        ComparePortfolioInputData inputData =
                new ComparePortfolioInputData(
                        portfolioIds,
                        metric,
                        startDate,
                        endDate,
                        benchmarkSymbol
                );
        interactor.execute(inputData);
    }
}