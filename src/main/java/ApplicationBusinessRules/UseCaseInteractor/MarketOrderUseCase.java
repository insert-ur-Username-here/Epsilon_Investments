package ApplicationBusinessRules.UseCaseInteractor;

import ApplicationBusinessRules.InputBoundary.MarketOrderInputBoundary;
import ApplicationBusinessRules.InputData.MarketOrderInputData;
import ApplicationBusinessRules.OutputBoundary.MarketOrderOutputBoundary;
import ApplicationBusinessRules.OutputData.MarketOrderOutputData;
import Entities.MarketOrder;
import Entities.Portfolio;
import ApplicationBusinessRules.DataAccessInterface.PortfolioDataAccessInterface;
import ApplicationBusinessRules.DataAccessInterface.StockDataAccessInterface;

import java.math.BigDecimal;

public class MarketOrderUseCase implements MarketOrderInputBoundary {

    private final MarketOrderOutputBoundary presenter;
    private final PortfolioDataAccessInterface portfolioDataAccessInterface;
    private final StockDataAccessInterface stockDataAccessInterface;

    public MarketOrderUseCase(MarketOrderOutputBoundary presenter,
                              PortfolioDataAccessInterface portfolioDataAccessInterface,
                              StockDataAccessInterface stockDataAccessInterface)
    {
        this.presenter = presenter;
        this.portfolioDataAccessInterface = portfolioDataAccessInterface;
        this.stockDataAccessInterface = stockDataAccessInterface;
    }

    public void execute(MarketOrderInputData inputData)
    {
        // Initializing Variables
        String company = inputData.getCompany();
        String buyOrSell = inputData.getBuyOrSell();
        int quantity = inputData.getQuantity();

        Portfolio myPortfolio = portfolioDataAccessInterface.getPortfolio();
        BigDecimal price = stockDataAccessInterface.getStockPrice(company);

        // Updated Portfolio
        Portfolio newPortfolio = myPortfolio.copy();

        if(buyOrSell.equals("BUY"))
        {
            MarketOrder marketOrder = new MarketOrder(company, buyOrSell, quantity, price);
            if (!newPortfolio.tryPlaceBuyOrder(marketOrder))
            {
                presenter.failView(new MarketOrderOutputData(false));
                return;
            }

            MarketOrderOutputData outputData = new MarketOrderOutputData(true, company, buyOrSell, quantity, price, newPortfolio.getCashBalance());
            portfolioDataAccessInterface.setPortfolio(newPortfolio);
            presenter.successView(outputData);
        }
        else if(buyOrSell.equals("SELL"))
        {
            if (myPortfolio.getSharesHeld(company) < quantity) {
                MarketOrderOutputData outputData = new MarketOrderOutputData(false);
                presenter.failView(outputData);
            }
            // Has the stocks to sell :D
            else
            {
                MarketOrder marketOrder = new MarketOrder(company, buyOrSell, quantity, price);
                newPortfolio.addOrder(marketOrder);

                MarketOrderOutputData outputData = new MarketOrderOutputData(true, company, buyOrSell, quantity, price, newPortfolio.getCashBalance());
                portfolioDataAccessInterface.setPortfolio(newPortfolio);
                presenter.successView(outputData);
            }
        }
        else
        {
            System.out.println("Buy / Sell does not equal \"BUY\" or \"SELL\"");
        }
    }
}
