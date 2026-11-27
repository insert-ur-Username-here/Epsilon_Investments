package ApplicationBusinessRules.DeletePortfolio;
import ApplicationBusinessRules.PortfolioRepository;
import Entities.Portfolio;
import java.util.Objects;
import java.util.Optional;

public class DeletePortfolioInteractor implements DeletePortfolioInputBoundary {

    private final PortfolioRepository portfolioRepository;
    private final DeletePortfolioOutputBoundary outputBoundary;

    // Constructor
    public DeletePortfolioInteractor(
            PortfolioRepository portfolioRepository,
            DeletePortfolioOutputBoundary outputBoundary)
    {
        this.portfolioRepository = Objects.requireNonNull(portfolioRepository,
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
        Optional<Portfolio> portfolio = portfolioRepository.findById(inputData.getPortfolioId());

        // Existence check
        if (portfolio.isEmpty()) {
            outputBoundary.prepareFailView(
                    "Portfolio does not exist."
            );
            return;
        }

        // Get data before deletion
        Portfolio portfolioToDelete = portfolio.get();
        portfolioRepository.delete(portfolioToDelete.getPortfolioId());

        // Output
        DeletePortfolioOutputData outputData =
                new DeletePortfolioOutputData(
                        portfolioToDelete.getPortfolioId(),
                        portfolioToDelete.getPortfolioName()
                );

        outputBoundary.prepareSuccessView(outputData);
    }
}