package ApplicationBusinessRules.DeletePortfolio;

import ApplicationBusinessRules.DeletePortfolio.DeletePortfolioOutputData;

public interface DeletePortfolioOutputBoundary {
    void prepareSuccessView(DeletePortfolioOutputData outputData);
    void prepareFailView(String errorMessage);
}
