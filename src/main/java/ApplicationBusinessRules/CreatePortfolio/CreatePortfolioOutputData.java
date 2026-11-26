package ApplicationBusinessRules.CreatePortfolio;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreatePortfolioOutputData {

    private final UUID portfolioId;
    private final String portfolioName;
    private final LocalDateTime dateCreated;
    private final BigDecimal initialCash;
    private final BigDecimal portfolioValue;

    // Constructors
    public CreatePortfolioOutputData(
            UUID portfolioId,
            String portfolioName,
            LocalDateTime dateCreated,
            BigDecimal initialCash,
            BigDecimal portfolioValue
    ) {
        this.portfolioId = portfolioId;
        this.portfolioName = portfolioName;
        this.dateCreated = dateCreated;
        this.initialCash = initialCash;
        this.portfolioValue = portfolioValue;
    }

    // Getters
    public UUID getPortfolioId() {
        return portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public BigDecimal getInitialCash() {
        return initialCash;
    }

    public BigDecimal getPortfolioValue() {
        return portfolioValue;
    }
}