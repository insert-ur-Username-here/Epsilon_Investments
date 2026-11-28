import ApplicationBusinessRules.DataAccessInterface.PortfolioDataAccessInterface;
import ApplicationBusinessRules.DataAccessInterface.StockDataAccessInterface;
import ApplicationBusinessRules.UseCaseInteractor.ExecuteOrderUseCase;
import ApplicationBusinessRules.UseCaseInteractor.LimitOrderUseCase;
import ApplicationBusinessRules.UseCaseInteractor.MarketOrderUseCase;
import FrameworksAndDrivers.DataAccess.SussyPortfolio;
import FrameworksAndDrivers.DataAccess.SussyStock;
import FrameworksAndDrivers.View.ExecuteOrderView;
import FrameworksAndDrivers.View.OrderView;
import FrameworksAndDrivers.View.ViewInterface;
import InterfaceAdapter.Controller.ExecuteOrderController;
import InterfaceAdapter.Controller.LimitOrderController;
import InterfaceAdapter.Controller.MarketOrderController;
import InterfaceAdapter.Presenter.ExecuteOrderPresenter;
import InterfaceAdapter.Presenter.LimitOrderPresenter;
import InterfaceAdapter.Presenter.MarketOrderPresenter;
import InterfaceAdapter.ViewModel.ExecuteOrderViewModel;
import InterfaceAdapter.ViewModel.OrderViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EpsilonInvestments {

    final private int width = 1200;
    final private int height = 800;

    public void start()
    {
        start(new SussyPortfolio(), new SussyStock());
    }

    public JFrame start(PortfolioDataAccessInterface portfolioDataAccess, StockDataAccessInterface stockDataAccess)
    {
        // JFrame
        JFrame myFrame = new JFrame();
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.setLayout(null);
        myFrame.getContentPane().setLayout(null);

        // Size
        myFrame.setResizable(false);
        myFrame.setPreferredSize(new Dimension(width, height));

        // Packing the Frame
        myFrame.pack();

        // Order View Use Case
        OrderViewModel orderViewModel = new OrderViewModel();

        MarketOrderPresenter marketOrderPresenter = new MarketOrderPresenter(orderViewModel);
        LimitOrderPresenter limitOrderPresenter = new LimitOrderPresenter(orderViewModel);

        MarketOrderUseCase marketOrderUseCase = new MarketOrderUseCase(marketOrderPresenter, portfolioDataAccess, stockDataAccess);
        LimitOrderUseCase limitOrderUseCase = new LimitOrderUseCase(limitOrderPresenter, portfolioDataAccess);

        MarketOrderController marketOrderController = new MarketOrderController(marketOrderUseCase);
        LimitOrderController limitOrderController = new LimitOrderController(limitOrderUseCase);

        ViewInterface orderView = new OrderView(marketOrderController, limitOrderController, orderViewModel);

        // Execute Order Use Case
        ExecuteOrderViewModel executeOrderViewModel = new ExecuteOrderViewModel();
        ExecuteOrderPresenter executeOrderPresenter = new ExecuteOrderPresenter(executeOrderViewModel);
        ExecuteOrderUseCase executeOrderUseCase = new ExecuteOrderUseCase(executeOrderPresenter, portfolioDataAccess, stockDataAccess);
        ExecuteOrderController executeOrderController = new ExecuteOrderController(executeOrderUseCase);
        ExecuteOrderView executeOrderView = new ExecuteOrderView(myFrame, executeOrderViewModel);

        Timer orderTimer = new Timer(1000, e ->
        {
            executeOrderController.execute();
            executeOrderView.showExecutedOrders();
        });
        myFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent event)
            {
                orderTimer.stop();
            }
        });


        // View
        orderView.draw(myFrame);

        // Drawing the Frame
        myFrame.setVisible(true);
        orderTimer.start();
        return myFrame;
    }
}
