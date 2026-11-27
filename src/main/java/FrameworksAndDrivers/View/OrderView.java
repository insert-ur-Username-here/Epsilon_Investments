package FrameworksAndDrivers.View;

import InterfaceAdapter.Controller.LimitOrderController;
import InterfaceAdapter.Controller.MarketOrderController;
import InterfaceAdapter.ViewModel.OrderViewModel;

import javax.swing.*;
import java.awt.*;

public class OrderView implements ViewInterface
{
    private final MarketOrderController marketOrderController;
    private final LimitOrderController limitOrderController;
    private final OrderViewModel viewModel;

    public OrderView(MarketOrderController marketOrderController, LimitOrderController limitOrderController, OrderViewModel viewModel)
    {
        this.marketOrderController = marketOrderController;
        this.limitOrderController = limitOrderController;
        this.viewModel = viewModel;
    }

    @Override
    public void draw(JFrame myFrame)
    {
        Container contentContainer = myFrame.getContentPane();
        int width = contentContainer.getWidth();
        int height = contentContainer.getHeight();

        String[] buyOrSell = {"Buy", "Sell"};
        JComboBox<String> buyOrSellComboBox = new JComboBox<>(buyOrSell);
        buyOrSellComboBox.setFont(new Font("Dialog", Font.PLAIN, 24));
        buyOrSellComboBox.setBounds(width * 85 / 100, height * 20 / 100,
                                 width * 10 / 100, height * 8 / 100);
        myFrame.add(buyOrSellComboBox);

        CardLayout cardLayout = new CardLayout();
        JPanel orderPanel = new JPanel(cardLayout);
        orderPanel.setBounds(width * 25 / 100, height * 30 / 100,
                          width * 70 / 100, height * 60 / 100);
        orderPanel.setBackground(Color.LIGHT_GRAY);
        orderPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));

        String[] typesOfOrders = {"Market", "Limit"};
        JComboBox<String> orderOptions = new JComboBox<>(typesOfOrders);

        OrderSuccessPanel successPanel = new OrderSuccessPanel(orderOptions, orderPanel, cardLayout);
        MarketPanel marketPanel = new MarketPanel(marketOrderController, viewModel, buyOrSellComboBox, successPanel, orderPanel, cardLayout);
        LimitPanel limitPanel = new LimitPanel(limitOrderController, viewModel, buyOrSellComboBox, successPanel, orderPanel, cardLayout);

        orderPanel.add(marketPanel, "Market");
        orderPanel.add(limitPanel, "Limit");
        orderPanel.add(successPanel, "Success");
        myFrame.add(orderPanel);

        orderOptions.setFont(new Font("Dialog", Font.PLAIN, 24));
        orderOptions.setBounds(width * 68 / 100, height * 20 / 100, width * 15 / 100, height * 8 / 100);

        orderOptions.addActionListener(e ->
        {
            String selected = (String) orderOptions.getSelectedItem();
            if (selected != null)
            {
                marketPanel.clearText();
                limitPanel.clearText();
                cardLayout.show(orderPanel, selected);
            }
        });
        myFrame.add(orderOptions);

        ImageIcon rawIcon = new ImageIcon("src/main/java/Images/epsilonBackground.png");
        Image scaledImg = rawIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(scaledImg));
        background.setBounds(0, 0, width, height);
        myFrame.add(background);

        myFrame.validate();
        myFrame.repaint();
    }
}
