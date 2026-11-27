package ApplicationBusinessRules.OutputBoundary;

import ApplicationBusinessRules.OutputData.CompanyNewsCollection;
import ApplicationBusinessRules.OutputData.MarketNewsCollection;

public interface SearcherOutputBoundary {

        void presentCompanyNews(CompanyNewsCollection collection, double sentiment);
        void presentMarketNews(MarketNewsCollection collection);
        void presentError(String message);

}
