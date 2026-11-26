package ApplicationBusinessRules.UseCaseInteractor;

import ApplicationBusinessRules.InputBoundary.StringInput;
import ApplicationBusinessRules.OutputBoundary.CompanyNewsCollection;
import ApplicationBusinessRules.OutputBoundary.MarketNewsCollection;
import ApplicationBusinessRules.StocksSearcher;

public class StockSearchInteractor {


    public static CompanyNewsCollection getCollectionOfCompanyNews(StringInput input) throws Exception {
        String userInput = input.getInput();
        String APIResponse = StocksSearcher.getCompanyNews(userInput);

        return new CompanyNewsCollection(APIResponse);
    }

    public static MarketNewsCollection getCollectionOfMarketNews(StringInput category) throws Exception {
        String userInput = category.getInput();
        String APIResponse = StocksSearcher.getMarketNews(userInput);
        return new MarketNewsCollection(APIResponse, userInput);
    }


    public static double getSentiment(String input) throws Exception {
        return StocksSearcher.getSentiment(input);

    }

    public static String getInvalidInputResponse(){
            return "This is an invalid input, please try again.";
        }

}

