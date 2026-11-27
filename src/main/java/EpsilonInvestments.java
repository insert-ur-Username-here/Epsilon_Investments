import ApplicationBusinessRules.UseCaseInteractor.LimitOrderUseCase;
import ApplicationBusinessRules.UseCaseInteractor.MarketOrderUseCase;
import FrameworksAndDrivers.DataAccess.SussyPortfolio;
import FrameworksAndDrivers.DataAccess.SussyStock;
import FrameworksAndDrivers.View.OrderView;
import FrameworksAndDrivers.View.ViewInterface;
import InterfaceAdapter.Controller.LimitOrderController;
import InterfaceAdapter.Controller.MarketOrderController;
import InterfaceAdapter.Presenter.LimitOrderPresenter;
import InterfaceAdapter.Presenter.MarketOrderPresenter;
import InterfaceAdapter.ViewModel.OrderViewModel;

import javax.swing.*;
import java.awt.*;

public class EpsilonInvestments {

    final private int width = 1200;
    final private int height = 800;

    public void start()
    {
        // JFrame
        JFrame myFrame = new JFrame();
        myFrame.setLayout(null);
        myFrame.getContentPane().setLayout(null);

        // Size
        myFrame.setResizable(false);
        myFrame.setPreferredSize(new Dimension(width, height));

        // Packing the Frame
        myFrame.pack();

        // Setting everything up
        OrderViewModel orderViewModel = new OrderViewModel();

        MarketOrderPresenter marketOrderPresenter = new MarketOrderPresenter(orderViewModel);
        LimitOrderPresenter limitOrderPresenter = new LimitOrderPresenter(orderViewModel);

        SussyPortfolio portfolioDataAccess = new SussyPortfolio();
        MarketOrderUseCase marketOrderUseCase = new MarketOrderUseCase(marketOrderPresenter, portfolioDataAccess, new SussyStock());
        LimitOrderUseCase limitOrderUseCase = new LimitOrderUseCase(limitOrderPresenter, portfolioDataAccess);

        MarketOrderController marketOrderController = new MarketOrderController(marketOrderUseCase);
        LimitOrderController limitOrderController = new LimitOrderController(limitOrderUseCase);

        ViewInterface orderView = new OrderView(marketOrderController, limitOrderController, orderViewModel);

        // View
        orderView.draw(myFrame);

        // Drawing the Frame
        myFrame.setVisible(true);
    }
}
