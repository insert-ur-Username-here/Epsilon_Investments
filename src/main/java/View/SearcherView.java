package View;

import Entities.CompanyNews;
import Entities.MarketNews;
import InterfaceAdapter.Controller.SearcherController;
import InterfaceAdapter.ViewModel.SearcherViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URI;
import java.util.List;

public class SearcherView extends JPanel implements PropertyChangeListener {

    private static final String[] MARKET_CATEGORIES = {
            "General", "Forex", "Crypto", "Merger"
    };

    private final SearcherViewModel viewModel;
    private final SearcherController controller;

    private final JTextField companySearchField = new JTextField(18);
    private final JButton companySearchButton = new JButton("Search");

    private final JComboBox<String> marketCategoryDropdown =
            new JComboBox<>(MARKET_CATEGORIES);
    private final JButton marketNewsButton = new JButton("Search");

    private final JLabel statusHeaderLabel = new JLabel("Enter a company or pick a market category.");
    private final JPanel resultsListPanel = new JPanel();
    private final JScrollPane resultsScrollPane;

    public SearcherView(SearcherViewModel viewModel, SearcherController controller) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        resultsListPanel.setLayout(new BoxLayout(resultsListPanel, BoxLayout.Y_AXIS));
        resultsScrollPane = new JScrollPane(resultsListPanel);
        resultsScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel controlsPanel = new JPanel(new GridLayout(2, 1, 8, 8));

        JPanel companyRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        companyRow.add(new JLabel("Company Search:"));
        companyRow.add(companySearchField);
        companyRow.add(companySearchButton);

        JPanel marketRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        marketRow.add(new JLabel("Market News:"));
        marketRow.add(marketCategoryDropdown);
        marketRow.add(marketNewsButton);

        controlsPanel.add(companyRow);
        controlsPanel.add(marketRow);

        JPanel topSection = new JPanel(new BorderLayout(6, 6));
        topSection.add(controlsPanel, BorderLayout.NORTH);
        topSection.add(statusHeaderLabel, BorderLayout.SOUTH);

        add(topSection, BorderLayout.NORTH);
        add(resultsScrollPane, BorderLayout.CENTER);

        ActionListener companyAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.searchCompanyNews(companySearchField.getText());
            }
        };
        companySearchButton.addActionListener(companyAction);
        companySearchField.addActionListener(companyAction);

        marketNewsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.searchMarketNews((String) marketCategoryDropdown.getSelectedItem());
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        resultsListPanel.removeAll();
        String mode = viewModel.getActiveMode();

        if ("ERROR".equals(mode)) {
            statusHeaderLabel.setText("Error");
            resultsListPanel.add(createMessageLabel(viewModel.getErrorMessage()));
        } else if ("COMPANY".equals(mode)) {
            statusHeaderLabel.setText(viewModel.getHeader());
            List<CompanyNews> items = viewModel.getCompanyNewsList();
            if (items.isEmpty()) {
                resultsListPanel.add(createMessageLabel("No company news found."));
            } else {
                for (CompanyNews news : items) {
                    resultsListPanel.add(createExpandableArticleItem(
                            safeText(news.getHeadline()),
                            safeText(news.getSummary()),
                            safeText(news.getURL())
                    ));
                }
            }
        } else if ("MARKET".equals(mode)) {
            statusHeaderLabel.setText(viewModel.getHeader());
            List<MarketNews> items = viewModel.getMarketNewsList();
            if (items.isEmpty()) {
                resultsListPanel.add(createMessageLabel("No market news found."));
            } else {
                for (MarketNews news : items) {
                    resultsListPanel.add(createExpandableArticleItem(
                            safeText(news.getHeadline()),
                            safeText(news.getSummary()),
                            safeText(news.getURL())
                    ));
                }
            }
        }

        resultsListPanel.revalidate();
        resultsListPanel.repaint();
        resultsScrollPane.getVerticalScrollBar().setValue(0);
    }

    private JPanel createExpandableArticleItem(String headline, String summary, String url) {
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JPanel headingBar = new JPanel(new BorderLayout(8, 0));
        headingBar.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
        headingBar.setBackground(new Color(245, 247, 250));
        headingBar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel arrowLabel = new JLabel(">");
        JLabel headingOnlyLabel = new JLabel(headline);
        headingOnlyLabel.setFont(headingOnlyLabel.getFont().deriveFont(Font.BOLD));

        headingBar.add(arrowLabel, BorderLayout.WEST);
        headingBar.add(headingOnlyLabel, BorderLayout.CENTER);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 24, 14, 14));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setVisible(false);

        JTextArea fullHeadlineArea = createPlainTextArea("Headline: " + headline, Font.BOLD);
        JTextArea summaryArea = createPlainTextArea("Summary: " + summary, Font.PLAIN);
        JButton openUrlButton = new JButton("Source: " + url);
        openUrlButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        openUrlButton.setFocusPainted(false);
        openUrlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUrlInBrowser(url);
            }
        });

        detailsPanel.add(fullHeadlineArea);
        detailsPanel.add(Box.createVerticalStrut(6));
        detailsPanel.add(summaryArea);
        detailsPanel.add(Box.createVerticalStrut(8));
        detailsPanel.add(openUrlButton);

        headingBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean expanding = !detailsPanel.isVisible();
                detailsPanel.setVisible(expanding);
                arrowLabel.setText(expanding ? "v" : ">");
                resultsListPanel.revalidate();
                resultsListPanel.repaint();
            }
        });

        itemPanel.add(headingBar, BorderLayout.NORTH);
        itemPanel.add(detailsPanel, BorderLayout.CENTER);
        return itemPanel;
    }

    private JTextArea createPlainTextArea(String text, int fontStyle) {
        JTextArea area = new JTextArea(text);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setOpaque(false);
        area.setFont(UIManager.getFont("Label.font").deriveFont(fontStyle));
        area.setAlignmentX(Component.LEFT_ALIGNMENT);
        return area;
    }

    private void openUrlInBrowser(String url) {
        if (url == null || url.isBlank()) {
            return;
        }
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url.trim()));
            }
        } catch (Exception ignored) {
            // Ignored if invalid URI or browser launch is unsupported
        }
    }

    private JLabel createMessageLabel(String message) {
        JLabel label = new JLabel(message);
        label.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        return label;
    }

    private String safeText(String value) {
        return value == null ? "" : value;
    }
}