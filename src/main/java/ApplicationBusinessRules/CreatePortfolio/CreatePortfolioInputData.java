package ApplicationBusinessRules.CreatePortfolio;
import java.math.BigDecimal;

public class CreatePortfolioInputData {

    private final String portfolioName;
    private final BigDecimal initialCash;

    // Constructor
    public CreatePortfolioInputData(
            String portfolioName,
            BigDecimal initialCash
    ) {
        this.portfolioName = portfolioName;
        this.initialCash = initialCash;
    }

    // Creation of empty portfolio
    public CreatePortfolioInputData(String portfolioName) {
        this(portfolioName, BigDecimal.ZERO);
    }

    // Getters
    public String getPortfolioName() {
        return this.portfolioName;
    }

    public BigDecimal getInitialCash() {
        return this.initialCash;
    }
}