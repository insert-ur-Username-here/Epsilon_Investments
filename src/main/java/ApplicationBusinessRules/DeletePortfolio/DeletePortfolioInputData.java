package ApplicationBusinessRules.DeletePortfolio;
import java.util.UUID;

public class DeletePortfolioInputData {
    private final UUID portfolioId;
    public DeletePortfolioInputData(UUID portfolioId) {
        this.portfolioId = portfolioId;
    }
    public UUID getPortfolioId() {
        return this.portfolioId;
    }
}
