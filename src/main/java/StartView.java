import javax.swing.*;

public class StartView implements ViewInterface{

    // Views:
    // US1: StartView, AccountView, SettingsView, SearchView
    // US2: BreakdownView, SecurityView,
    // US3: NewsView
    // US4: TransactionsView

    @Override
    public void draw(JFrame myFrame) {

        ImageIcon epsilonInvestmentsBackground = new ImageIcon("src/View/java/Epsilon_Investments.png");
        JLabel background = new JLabel(epsilonInvestmentsBackground);
        background.setBounds(0, 0, epsilonInvestmentsBackground.getIconWidth(), epsilonInvestmentsBackground.getIconHeight());

        myFrame.add(background);
        myFrame.validate();
        myFrame.repaint();
    }
}
