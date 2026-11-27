package ApplicationBusinessRules.DeletePortfolio;
import ApplicationBusinessRules.DataAccessInterface;
import Entities.Portfolio;
import java.util.Objects;
import java.util.Optional;

public class DeletePortfolioInteractor implements DeletePortfolioInputBoundary {

    private final DataAccessInterface dataAccessInterface;
    private final DeletePortfolioOutputBoundary outputBoundary;

    // Constructor
    public DeletePortfolioInteractor(
            DataAccessInterface dataAccessInterface,
            DeletePortfolioOutputBoundary outputBoundary)
    {
        this.dataAccessInterface = Objects.requireNonNull(dataAccessInterface,
                "Portfolio repository cannot be null.");

        this.outputBoundary = Objects.requireNonNull(outputBoundary,
                "Output boundary cannot be null.");
    }

    // Execute use case
    @Override
    public void execute(DeletePortfolioInputData inputData) {
        if (inputData == null || inputData.getPortfolioId() == null) {
            outputBoundary.prepareFailView(
                    "Invalid portfolio ID."
            );
            return;
        }
        Optional<Portfolio> portfolio = dataAccessInterface.findById(inputData.getPortfolioId());

        // Existence check
        if (portfolio.isEmpty()) {
            outputBoundary.prepareFailView(
                    "Portfolio does not exist."
            );
            return;
        }

        // Get data before deletion
        Portfolio portfolioToDelete = portfolio.get();
        dataAccessInterface.delete(portfolioToDelete.getPortfolioId());

        // Output
        DeletePortfolioOutputData outputData =
                new DeletePortfolioOutputData(
                        portfolioToDelete.getPortfolioId(),
                        portfolioToDelete.getPortfolioName()
                );

        outputBoundary.prepareSuccessView(outputData);
    }
}