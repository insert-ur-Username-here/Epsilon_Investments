package FrameworksAndDrivers.View;

import InterfaceAdapter.ViewModel.ExecuteOrderViewModel;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;

public class ExecuteOrderView
{
    private static final Color PANEL_BACKGROUND = new Color(237, 246, 250);
    private static final Color TEXT_COLOR = new Color(35, 51, 86);
    private static final Color BORDER_COLOR = new Color(181, 210, 222);
    private static final Color BUTTON_BLUE = new Color(39, 93, 122);

    private final JFrame myFrame;
    private final ExecuteOrderViewModel viewModel;

    public ExecuteOrderView(JFrame myFrame, ExecuteOrderViewModel viewModel)
    {
        this.myFrame = myFrame;
        this.viewModel = viewModel;
    }

    public void showExecutedOrders()
    {
        String detailsText = viewModel.getDetailsText();
        if (detailsText.isEmpty())
        {
            return;
        }

        JDialog dialog = new JDialog(myFrame, "Orders executed", false);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(e -> dialog.dispose());
        dialog.setContentPane(createContent(detailsText, doneButton));
        dialog.getRootPane().setDefaultButton(doneButton);
        dialog.pack();
        dialog.setLocationRelativeTo(myFrame);
        dialog.setVisible(true);
    }

    private JPanel createContent(String detailsText, JButton doneButton)
    {
        JPanel content = new JPanel(new BorderLayout(0, 16));
        content.setBackground(PANEL_BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(20, 24, 18, 24));

        JLabel heading = new JLabel("Orders executed", SwingConstants.CENTER);
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 24f));
        heading.setForeground(TEXT_COLOR);
        content.add(heading, BorderLayout.NORTH);

        JTextArea details = new JTextArea(detailsText, 5, 37);
        details.setRows(Math.max(5, Math.min(11, details.getLineCount() + 1)));
        details.setFont(details.getFont().deriveFont(16f));
        details.setForeground(TEXT_COLOR);
        details.setBackground(Color.WHITE);
        details.setMargin(new Insets(14, 16, 14, 16));
        details.setEditable(false);
        details.setLineWrap(true);
        details.setWrapStyleWord(true);
        details.setCaretPosition(0);

        JScrollPane detailsScrollPane = new JScrollPane(details);
        detailsScrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1, true));
        content.add(detailsScrollPane, BorderLayout.CENTER);

        doneButton.setPreferredSize(new Dimension(112, 36));
        doneButton.setFont(doneButton.getFont().deriveFont(Font.BOLD, 15f));
        doneButton.setForeground(Color.WHITE);
        doneButton.setBackground(BUTTON_BLUE);
        doneButton.setOpaque(true);
        doneButton.setBorder(BorderFactory.createLineBorder(BUTTON_BLUE, 1, true));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttons.setOpaque(false);
        buttons.add(doneButton);
        content.add(buttons, BorderLayout.SOUTH);
        return content;
    }
}
