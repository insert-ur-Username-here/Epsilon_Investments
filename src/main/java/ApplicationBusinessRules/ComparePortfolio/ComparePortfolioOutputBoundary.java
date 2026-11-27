package ApplicationBusinessRules.ComparePortfolio;

public interface ComparePortfolioOutputBoundary {
    void prepareSuccessView(ComparePortfolioOutputData outputData);
    void prepareFailView(String errorMessage);
}
