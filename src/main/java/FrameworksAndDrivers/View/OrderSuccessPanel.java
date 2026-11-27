package FrameworksAndDrivers.View;

import javax.swing.*;
import java.awt.*;

public class OrderSuccessPanel extends JPanel
{
    private final JTextArea detailsText = new JTextArea(6, 30);

    public OrderSuccessPanel(JComboBox<String> orderOptions, JPanel orderPanel, CardLayout cardLayout)
    {
        setLayout(new BorderLayout(0, 12));
        setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel heading = new JLabel("Order Recorded!!!", SwingConstants.CENTER);
        heading.setFont(new Font("Dialog", Font.BOLD, 32));
        add(heading, BorderLayout.NORTH);

        detailsText.setEditable(false);
        detailsText.setFont(new Font("Dialog", Font.PLAIN, 22));
        detailsText.setMargin(new Insets(12, 12, 12, 12));

        JScrollPane detailsScrollPane = new JScrollPane(detailsText);
        add(detailsScrollPane, BorderLayout.CENTER);

        JButton doneButton = new JButton("→");
        doneButton.setPreferredSize(new Dimension(140, 60));
        doneButton.setBackground(new Color(203, 232, 244));
        doneButton.setForeground(new Color(35, 51, 86));
        doneButton.setFont(doneButton.getFont().deriveFont(Font.BOLD, 36f));
        doneButton.setBorder(BorderFactory.createLineBorder(new Color(142, 187, 201), 8, true));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.add(doneButton);
        add(buttonPanel, BorderLayout.SOUTH);

        doneButton.addActionListener(e ->
        {
            String selected = (String) orderOptions.getSelectedItem();
            if (selected != null)
            {
                cardLayout.show(orderPanel, selected);
            }
        });
    }

    public void setDetailsText(String text)
    {
        detailsText.setText(text);
        detailsText.setCaretPosition(0);
    }
}
