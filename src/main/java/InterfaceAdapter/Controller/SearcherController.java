package InterfaceAdapter.Controller;

import ApplicationBusinessRules.InputBoundary.StringInput;
import ApplicationBusinessRules.UseCaseInteractor.SearcherInteractor;

public class SearcherController {

    private final SearcherInteractor interactor;

    public SearcherController(SearcherInteractor interactor) {
        this.interactor = interactor;
    }

    public void searchCompanyNews(String companyInput) {
        interactor.executeCompanyNews(new StringInput(companyInput));
    }

    public void searchMarketNews(String categoryInput) {
        interactor.executeMarketNews(new StringInput(categoryInput));
    }
}