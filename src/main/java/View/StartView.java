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
                                    contentContainer.getHeight() * 5/16,
                                    contentContainer.getWidth() * 14/16,
                                    contentContainer.getHeight() * 5/8);

        // Order to paint components
        myFrame.add(accountScrollPane, 0);
        myFrame.add(background, 1);

        myFrame.validate();
        myFrame.repaint();
    }
}
