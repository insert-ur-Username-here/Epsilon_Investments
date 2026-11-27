package ApplicationBusinessRules.UseCaseInteractor;

import ApplicationBusinessRules.InputBoundary.StringInput;
import ApplicationBusinessRules.OutputData.CompanyNewsCollection;
import ApplicationBusinessRules.OutputData.MarketNewsCollection;
import ApplicationBusinessRules.OutputBoundary.SearcherOutputBoundary;
import ApplicationBusinessRules.Searcher;

public class SearcherInteractor {

    private final SearcherOutputBoundary presenter;

    public SearcherInteractor(SearcherOutputBoundary presenter) {
        this.presenter = presenter;
    }

    public void executeCompanyNews(StringInput input) {
        if (input == null || input.getInput() == null || input.getInput().trim().isEmpty()) {
            presenter.presentError(getInvalidInputResponse());
            return;
        }

        try {
            String query = input.getInput().trim();
            CompanyNewsCollection collection = getCollectionOfCompanyNews(new StringInput(query));
            double sentiment = getSentiment(new StringInput(query));
            presenter.presentCompanyNews(collection, sentiment);
        } catch (Exception e) {
            presenter.presentError(getInvalidInputResponse());
        }
    }

    public void executeMarketNews(StringInput input) {
        if (input == null || input.getInput() == null || input.getInput().trim().isEmpty()) {
            presenter.presentError(getInvalidInputResponse());
            return;
        }

        try {
            String category = input.getInput().trim();
            MarketNewsCollection collection = getCollectionOfMarketNews(new StringInput(category));
            presenter.presentMarketNews(collection);
        } catch (Exception e) {
            presenter.presentError(getInvalidInputResponse());
        }
    }

    public double getSentiment(StringInput input) throws Exception {
        return Searcher.getSentiment(input.getInput().trim());
    }

    public CompanyNewsCollection getCollectionOfCompanyNews(StringInput input) throws Exception {
        String json = Searcher.getCompanyNews(input.getInput().trim());
        return new CompanyNewsCollection(json);
    }

    public MarketNewsCollection getCollectionOfMarketNews(StringInput input) throws Exception {
        String category = input.getInput().trim();
        String json = Searcher.getMarketNews(category);
        return new MarketNewsCollection(json, category);
    }

    public static String getInvalidInputResponse() {
        return "Invalid input. Please enter a valid company or select a market category.";
    }
}