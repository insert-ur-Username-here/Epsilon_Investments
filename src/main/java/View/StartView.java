package View;

import javax.swing.*;
import java.awt.*;

public class StartView implements ViewInterface {

    // Views:
    // US1: StartView, AccountView, SettingsView, SearchView
    // US2: BreakdownView, SecurityView,
    // US3: NewsView
    // US4: TransactionsView

    @Override
    public void draw(JFrame myFrame) {

        Container contentContainer = myFrame.getContentPane();
        ImageIcon epsilonInvestmentsBackground = new ImageIcon("src/main/java/Images/Epsilon_Investments.png");
        JLabel background = new JLabel(epsilonInvestmentsBackground);
        background.setBounds(0, 0, epsilonInvestmentsBackground.getIconWidth(), epsilonInvestmentsBackground.getIconHeight());
        
        JScrollPane accountScrollPane = new JScrollPane();
        accountScrollPane.setBounds(contentContainer.getWidth() * 1/16,
                                    contentContainer.getHeight() * 1/4,
                                    contentContainer.getWidth() * 14/16,
                                    contentContainer.getHeight() * 5/8);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(accountScrollPane.getX(),
                              accountScrollPane.getY() + accountScrollPane.getHeight(),
                              accountScrollPane.getWidth(),
                              contentContainer.getHeight() * 1/16);


        JButton addAccountButton = new JButton("Create Account");
        addAccountButton.addActionListener(e -> {
            ;
        });
        buttonPanel.add(addAccountButton);

        // Order to paint components
        myFrame.add(accountScrollPane, 0);
        myFrame.add(buttonPanel, 1);
        myFrame.add(background, 2);

        myFrame.validate();
        myFrame.repaint();
    }
}
