package ApplicationBusinessRules.CreatePortfolio;
import Entities.Portfolio;

public interface PortfolioRepository {
    void save(Portfolio portfolio);
}