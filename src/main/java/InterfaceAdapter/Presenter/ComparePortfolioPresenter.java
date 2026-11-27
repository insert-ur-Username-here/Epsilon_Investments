package InterfaceAdapter.Presenter;

import ApplicationBusinessRules.ComparePortfolio.*;
import InterfaceAdapter.ViewModel.*;
import java.math.BigDecimal;
import java.util.*;

public class ComparePortfolioPresenter
        implements ComparePortfolioOutputBoundary {

    private final ComparePortfolioViewModel viewModel;

    public ComparePortfolioPresenter(ComparePortfolioViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ComparePortfolioOutputData outputData) {

        viewModel.setSuccess(true);
        viewModel.setMessage("Comparison completed successfully.");
        viewModel.setMetric(outputData.getMetric().name());
        viewModel.setStartDate(outputData.getStartDate());
        viewModel.setEndDate(outputData.getEndDate());
        List<ComparisonRowViewModel> rows = new ArrayList<>();

        for (PortfolioMetricResult result : outputData.getResults()) {

            // Convert decimal return/alpha into percentage
            BigDecimal percentage = result.getMetricValue().multiply(new BigDecimal("100"));
            String formattedValue = percentage.toPlainString() + "%";

            rows.add(
                    new ComparisonRowViewModel(
                            result.getPortfolioId(),
                            result.getPortfolioName(),
                            formattedValue
                    )
            );
        }
        viewModel.setResults(rows);
    }

    @Override
    public void prepareFailView(String errorMessage) {

        viewModel.setSuccess(false);
        viewModel.setMessage(errorMessage);
        viewModel.setResults(Collections.emptyList());
    }
}