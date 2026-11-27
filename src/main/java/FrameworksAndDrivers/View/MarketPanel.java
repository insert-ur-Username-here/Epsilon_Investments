package FrameworksAndDrivers.View;

import InterfaceAdapter.Controller.MarketOrderController;
import InterfaceAdapter.ViewModel.OrderViewModel;

import javax.swing.*;
import java.awt.*;

public class MarketPanel extends JPanel
{
    JLabel amountText = new JLabel("Amount: ");
    JTextField amountTextField = new JTextField(8);

    public MarketPanel(MarketOrderController marketOrderController, OrderViewModel viewModel, JComboBox<String> buyOrSellBox, OrderSuccessPanel successPanel, JPanel orderPanel, CardLayout cardLayout)
    {
        setLayout(null);

        amountText.setFont(new Font("Dialog", Font.BOLD, 48));
        amountText.setBounds(130, 100, 320, 60);
        add(amountText);

        amountTextField.setFont(new Font("Dialog", Font.PLAIN, 32));
        amountTextField.setBounds(510, 100, 200, 60);
        add(amountTextField);

        JButton confirmButton = new JButton("✔");
        confirmButton.setBounds(720, 370, 90, 60);
        confirmButton.setBackground(new Color(173, 235, 179));
        confirmButton.setBorder(BorderFactory.createLineBorder(new Color(0, 187, 119), 10, true));
        confirmButton.setFont(new Font("Dialog", Font.BOLD, 45));
        add(confirmButton);

        confirmButton.addActionListener(e ->
        {
            int quantity;
            try
            {
                quantity = Integer.parseInt(amountTextField.getText().trim());
            }
            catch (NumberFormatException exception)
            {
                JOptionPane.showMessageDialog(this, "Enter a valid whole share quantity.");
                return;
            }

            if (quantity <= 0)
            {
                JOptionPane.showMessageDialog(this, "Enter at least one share.");
                return;
            }

            String buyOrSell = (String) buyOrSellBox.getSelectedItem();
            marketOrderController.onMarketOrderClick("ABCD", buyOrSell, quantity);

            String detailText = viewModel.getDetailsText();
            if (!viewModel.getSuccess())
            {
                JOptionPane.showMessageDialog(this, detailText, "Order rejected", JOptionPane.ERROR_MESSAGE);
                return;
            }

            successPanel.setDetailsText(detailText);
            cardLayout.show(orderPanel, "Success");
        });

        JButton leaveButton = new JButton("✖");
        leaveButton.setBounds(30, 370, 90, 60);
        leaveButton.setBackground(new Color(246, 166, 166));
        leaveButton.setBorder(BorderFactory.createLineBorder(new Color(228, 61, 61), 10, true));
        leaveButton.setFont(new Font("Dialog", Font.BOLD, 45));
        add(leaveButton);
    }

    public void clearText()
    {
        amountTextField.setText("");
    }
}
