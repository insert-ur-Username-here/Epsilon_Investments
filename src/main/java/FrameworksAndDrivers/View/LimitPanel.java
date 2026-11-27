package FrameworksAndDrivers.View;

import InterfaceAdapter.Controller.LimitOrderController;
import InterfaceAdapter.ViewModel.OrderViewModel;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class LimitPanel extends JPanel
{

    JLabel amountText = new JLabel("Amount: ");
    JTextField amountTextField = new JTextField(8);

    JLabel priceText = new JLabel("Limit Price: ");
    JTextField priceTextField = new JTextField(8);

    public LimitPanel(LimitOrderController limitOrderController, OrderViewModel viewModel, JComboBox<String> buyOrSellBox, OrderSuccessPanel successPanel, JPanel orderPanel, CardLayout cardLayout)
    {
        setLayout(null);

        amountText.setFont(new Font("Dialog", Font.BOLD, 48));
        amountText.setBounds(130, 100, 320, 60);
        add(amountText);

        amountTextField.setFont(new Font("Dialog", Font.PLAIN, 32));
        amountTextField.setBounds(510, 100,200, 60);
        add(amountTextField);

        priceText.setFont(new Font("Dialog", Font.BOLD, 48));
        priceText.setBounds(130, 200, 320, 60);
        add(priceText);

        priceTextField.setFont(new Font("Dialog", Font.PLAIN, 32));
        priceTextField.setBounds(510, 200,200, 60);
        add(priceTextField);

        JButton confirmButton = new JButton("✔");
        confirmButton.setBounds(720, 370, 90, 60);
        confirmButton.setBackground(new Color(173,235,179));
        confirmButton.setBorder(BorderFactory.createLineBorder(new Color(0,187,119), 10, true));
        confirmButton.setFont(new Font("Dialog", Font.BOLD, 45));
        add(confirmButton);

        confirmButton.addActionListener(e ->
        {
            int quantity;
            BigDecimal limitPrice;
            try
            {
                quantity = Integer.parseInt(amountTextField.getText().trim());
                limitPrice = new BigDecimal(priceTextField.getText().trim());
            }
            catch (NumberFormatException exception)
            {
                JOptionPane.showMessageDialog(this, "Enter a whole share quantity and a valid limit price.");
                return;
            }

            if (quantity <= 0 || limitPrice.signum() <= 0)
            {
                JOptionPane.showMessageDialog(this, "Quantity and limit price must be positive.");
                return;
            }

            String buyOrSell = (String) buyOrSellBox.getSelectedItem();
            limitOrderController.onLimitOrderClick("ABCD", buyOrSell, quantity, limitPrice);

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
        priceTextField.setText("");
    }
}
