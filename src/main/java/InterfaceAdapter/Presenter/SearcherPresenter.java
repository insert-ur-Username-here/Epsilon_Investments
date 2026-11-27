package InterfaceAdapter.Presenter;

import ApplicationBusinessRules.OutputBoundary.CompanyNewsCollection;
import ApplicationBusinessRules.OutputBoundary.MarketNewsCollection;
import ApplicationBusinessRules.OutputBoundary.SearcherOutputBoundary;
import ApplicationBusinessRules.UseCaseInteractor.SearcherInteractor;
import Entities.CompanyNews;
import Entities.MarketNews;
import InterfaceAdapter.ViewModel.SearcherViewModel;

import java.util.List;

public class SearcherPresenter implements SearcherOutputBoundary {

    private final SearcherViewModel viewModel;

    public SearcherPresenter(SearcherViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void presentCompanyNews(CompanyNewsCollection collection, double sentiment) {
        List<CompanyNews> items = collection.getCollectionOfCompanyNews();
        String company = collection.getCompanyName();

        if (items == null || items.isEmpty() || company == null || company.trim().isEmpty()) {
            presentError(SearcherInteractor.getInvalidInputResponse());
            return;
        }

        String formattedSentiment = String.format("%.2f", sentiment);
        String header = company + " News (" + items.size() + ") | Sentiment: " + formattedSentiment;
        viewModel.setCompanyNewsState(header, items);
    }

    public void presentMarketNews(MarketNewsCollection collection) {
        List<MarketNews> items = collection.getCollectionOfMarketNews();
        String category = collection.getCategory();

        String header;
        if (category == null || category.isEmpty()) {
            header = "Market News (" + items.size() + ")";
        } else {
            header = category + " Market News (" + items.size() + ")";
        }

        viewModel.setMarketNewsState(header, items);
    }

    public void presentError(String message) {
        viewModel.setErrorState(message);
    }
}