package ApplicationBusinessRules.OutputBoundary;

public interface SearcherOutputBoundary {

        void presentCompanyNews(CompanyNewsCollection collection, double sentiment);
        void presentMarketNews(MarketNewsCollection collection);
        void presentError(String message);

}
