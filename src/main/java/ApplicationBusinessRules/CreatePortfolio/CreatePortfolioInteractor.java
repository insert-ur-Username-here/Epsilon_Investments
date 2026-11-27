package ApplicationBusinessRules.CreatePortfolio;
import ApplicationBusinessRules.DataAccessInterface;
import Entities.Portfolio;
import java.math.BigDecimal;
import java.util.Objects;

public class CreatePortfolioInteractor implements CreatePortfolioInputBoundary {

    private final DataAccessInterface dataAccessInterface;
    private final CreatePortfolioOutputBoundary outputBoundary;

    // Constructor
    public CreatePortfolioInteractor(
            DataAccessInterface dataAccessInterface,
            CreatePortfolioOutputBoundary outputBoundary
    ) {
        this.dataAccessInterface = Objects.requireNonNull(
                dataAccessInterface,
                "Portfolio repository cannot be null."
        );
        this.outputBoundary = Objects.requireNonNull(
                outputBoundary,
                "Output boundary cannot be null."
        );
    }

    // Execute use case
    @Override
    public void execute(CreatePortfolioInputData inputData) {
        if (inputData == null) {
            outputBoundary.prepareFailView(
                    "Portfolio creation data cannot be null."
            );
            return;
        }

        String portfolioName = inputData.getPortfolioName();
        BigDecimal initialCash = inputData.getInitialCash();

        // Exceptions
        if (portfolioName == null || portfolioName.isBlank()) {
            outputBoundary.prepareFailView(
                    "Portfolio name cannot be empty."
            );
            return;
        }
        if (initialCash == null) {
            outputBoundary.prepareFailView(
                    "Initial cash cannot be null."
            );
            return;
        }
        if (initialCash.compareTo(BigDecimal.ZERO) < 0) {
            outputBoundary.prepareFailView(
                    "Initial cash cannot be negative."
            );
            return;
        }

        // Create portfolio entity
        Portfolio portfolio;
        try {
            portfolio = new Portfolio(
                    portfolioName,
                    initialCash
            );
        }
        catch (IllegalArgumentException exception) {
            outputBoundary.prepareFailView(
                    exception.getMessage()
            );
            return;
        }

        dataAccessInterface.save(portfolio);

        // Prepare output data
        CreatePortfolioOutputData outputData =
                new CreatePortfolioOutputData(
                        portfolio.getPortfolioId(),
                        portfolio.getPortfolioName(),
                        portfolio.getDateCreated(),
                        portfolio.getCashBalance(),
                        portfolio.getPortfolioValue()
                );

        outputBoundary.prepareSuccessView(outputData);
    }
}