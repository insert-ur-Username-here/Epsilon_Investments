package InterfaceAdapter.Controller;

import ApplicationBusinessRules.DeletePortfolio.*;
import java.util.Objects;
import java.util.UUID;

public class DeletePortfolioController {

    private final DeletePortfolioInputBoundary interactor;

    public DeletePortfolioController(
            DeletePortfolioInputBoundary interactor
    ) {
        this.interactor = Objects.requireNonNull(interactor);
    }

    public void deletePortfolio(UUID portfolioId) {

        // Construct and pass
        DeletePortfolioInputData inputData =
                new DeletePortfolioInputData(portfolioId);
        interactor.execute(inputData);
    }
}