package ApplicationBusinessRules.CreatePortfolio;

public interface CreatePortfolioOutputBoundary {
    void prepareSuccessView(CreatePortfolioOutputData outputData);
    void prepareFailView(String errorMessage);
}