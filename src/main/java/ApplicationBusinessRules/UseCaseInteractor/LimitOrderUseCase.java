package ApplicationBusinessRules.UseCaseInteractor;

import ApplicationBusinessRules.InputBoundary.LimitOrderInputBoundary;
import ApplicationBusinessRules.InputData.LimitOrderInputData;
import ApplicationBusinessRules.OutputBoundary.LimitOrderOutputBoundary;
import ApplicationBusinessRules.OutputData.LimitOrderOutputData;
import Entities.LimitOrder;
import Entities.Portfolio;
import ApplicationBusinessRules.DataAccessInterface.PortfolioDataAccessInterface;

import java.math.BigDecimal;

public class LimitOrderUseCase implements LimitOrderInputBoundary {

    private final LimitOrderOutputBoundary presenter;
    private final PortfolioDataAccessInterface portfolioDataAccessInterface;

    public LimitOrderUseCase(LimitOrderOutputBoundary presenter, PortfolioDataAccessInterface portfolioDataAccessInterface)
    {
        this.presenter = presenter;
        this.portfolioDataAccessInterface = portfolioDataAccessInterface;
    }

    @Override
    public void execute(LimitOrderInputData inputData)
    {
        // Initializing Variables
        String company = inputData.getCompany();
        String buyOrSell = inputData.getBuyOrSell();
        int quantity = inputData.getQuantity();

        Portfolio myPortfolio = portfolioDataAccessInterface.getPortfolio();
        BigDecimal limitPrice = inputData.getLimitPrice();
        if (limitPrice == null || limitPrice.signum() <= 0)
        {
            presenter.failView(new LimitOrderOutputData(false));
            return;
        }

        // Updated Portfolio
        Portfolio newPortfolio = myPortfolio.copy();

        if(buyOrSell.equals("BUY"))
        {
            LimitOrder limitOrder = new LimitOrder(company, buyOrSell, quantity, limitPrice);
            if (!newPortfolio.tryPlaceBuyOrder(limitOrder))
            {
                presenter.failView(new LimitOrderOutputData(false));
                return;
            }

            LimitOrderOutputData outputData = new LimitOrderOutputData(true, company, buyOrSell, quantity, limitPrice, newPortfolio.getCashBalance());
            portfolioDataAccessInterface.setPortfolio(newPortfolio);
            presenter.successView(outputData);
        }
        else if(buyOrSell.equals("SELL"))
        {
            // If not enough stocks owned
            if (myPortfolio.getSharesHeld(company) < quantity) {
                LimitOrderOutputData outputData = new LimitOrderOutputData(false);
                presenter.failView(outputData);
            }
            // Has the stocks to sell :D
            else
            {
                LimitOrder limitOrder = new LimitOrder(company, buyOrSell, quantity, limitPrice);
                newPortfolio.addOrder(limitOrder);

                LimitOrderOutputData outputData = new LimitOrderOutputData(true, company, buyOrSell, quantity, limitPrice, newPortfolio.getCashBalance());
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
