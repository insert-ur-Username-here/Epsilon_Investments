package ApplicationBusinessRules.UseCaseInteractor;

import ApplicationBusinessRules.InputBoundary.ExecuteOrderInputBoundary;
import ApplicationBusinessRules.InputData.ExecuteOrderInputData;
import ApplicationBusinessRules.OutputBoundary.ExecuteOrderOutputBoundary;
import ApplicationBusinessRules.OutputData.ExecuteOrderOutputData;
import Entities.Order;
import Entities.Portfolio;
import ApplicationBusinessRules.DataAccessInterface.PortfolioDataAccessInterface;
import ApplicationBusinessRules.DataAccessInterface.StockDataAccessInterface;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ExecuteOrderUseCase implements ExecuteOrderInputBoundary
{

    private final ExecuteOrderOutputBoundary presenter;
    private final PortfolioDataAccessInterface portfolioDataAccessInterface;
    private final StockDataAccessInterface stockDataAccessInterface;

    public ExecuteOrderUseCase(ExecuteOrderOutputBoundary presenter, PortfolioDataAccessInterface portfolioDataAccessInterface, StockDataAccessInterface stockDataAccessInterface)
    {
        this.presenter = presenter;
        this.portfolioDataAccessInterface = portfolioDataAccessInterface;
        this.stockDataAccessInterface = stockDataAccessInterface;
    }

    @Override
    public void execute(ExecuteOrderInputData inputData)
    {
        Portfolio portfolio = portfolioDataAccessInterface.getPortfolio().copy();
        List<ExecuteOrderOutputData.Fill> fills = new ArrayList<>();

        for (Order order : portfolio.getOrders())
        {
            if (order.isFilled())
            {
                continue;
            }

            BigDecimal marketPrice = stockDataAccessInterface.getStockPrice(order.getCompany());
            portfolio.tryFillOrder(order, marketPrice).ifPresent(transaction ->
            {
                fills.add(new ExecuteOrderOutputData.Fill(transaction.getCompany(), transaction.getBuyOrSell(), transaction.getFilledQuantity(), transaction.getExecutionPrice()));
            });
        }

        if (!fills.isEmpty())
        {
            portfolioDataAccessInterface.setPortfolio(portfolio);
        }

        ExecuteOrderOutputData outputData = new ExecuteOrderOutputData(fills, portfolio.getCashBalance());
        presenter.present(outputData);
    }
}
