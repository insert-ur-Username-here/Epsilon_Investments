package InterfaceAdapter.Controller;
import ApplicationBusinessRules.CreatePortfolio.*;
import java.math.BigDecimal;
import java.util.Objects;

public class CreatePortfolioController {

    private final CreatePortfolioInputBoundary interactor;

    // Constructor
    public CreatePortfolioController(CreatePortfolioInputBoundary interactor) {
        this.interactor = Objects.requireNonNull(interactor);
    }

    //Methods
    public void createPortfolio(
            String portfolioName,
            BigDecimal initialCash
    ) {

        // Construct input
        CreatePortfolioInputData inputData =
                new CreatePortfolioInputData(
                        portfolioName,
                        initialCash
                );

        // Pass to interactor
        interactor.execute(inputData);
    }
}