package ApplicationBusinessRules.DeletePortfolio;
import java.util.UUID;

public class DeletePortfolioOutputData {
    private final UUID portfolioId;
    private final String portfolioName;
    public DeletePortfolioOutputData(
            UUID portfolioId,
            String portfolioName
    ) {
        this.portfolioId = portfolioId;
        this.portfolioName = portfolioName;
    }
    public UUID getPortfolioId() {
        return this.portfolioId;
    }
    public String getPortfolioName() {
        return this.portfolioName;
    }
}