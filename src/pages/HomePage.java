package pages;

import components.Input;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomePage extends BasePage {
    private JLabel totalItemsLabel;
    private JLabel lowStockLabel;
    private JLabel totalValueLabel;
    private JLabel currentDateLabel;

    public HomePage(MainFrame mainFrame) {
        super(mainFrame);
        setUI();
    }

    @Override
    public void setUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 249));
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // Header
        JLabel headerLabel = new JLabel("Dashboard Overview", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 28));
        headerLabel.setForeground(new Color(70, 130, 180));
        headerLabel.setBorder(new EmptyBorder(0, 0, 30, 0));

        // Metrics panel
        JPanel metricsPanel = createMetricsPanel();

        // Quick actions panel
        JPanel actionsPanel = createQuickActionsPanel();

        add(headerLabel, BorderLayout.NORTH);
        add(metricsPanel, BorderLayout.CENTER);
        add(actionsPanel, BorderLayout.SOUTH);

        // Refresh data to show current values
        refreshData();
    }

    private JPanel createMetricsPanel() {
        JPanel metricsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        metricsPanel.setBackground(new Color(240, 245, 249));

        // Total Items Card
        JPanel totalItemsCard = createMetricCard("Total Items", "0", 
            new Color(41, 128, 185));
        totalItemsLabel = (JLabel) ((JPanel) totalItemsCard.getComponent(1)).getComponent(0);

        // Low Stock Items Card
        JPanel lowStockCard = createMetricCard("Low Stock Items", "0", 
            new Color(231, 76, 60));
        lowStockLabel = (JLabel) ((JPanel) lowStockCard.getComponent(1)).getComponent(0);

        // Total Stock Value Card
        JPanel totalValueCard = createMetricCard("Total Stock Value", "$0.00", 
            new Color(39, 174, 96));
        totalValueLabel = (JLabel) ((JPanel) totalValueCard.getComponent(1)).getComponent(0);

        // Current Date Card
        JPanel dateCard = createMetricCard("Current Date", getCurrentDate(), 
            new Color(155, 89, 182));
        currentDateLabel = (JLabel) ((JPanel) dateCard.getComponent(1)).getComponent(0);

        metricsPanel.add(totalItemsCard);
        metricsPanel.add(lowStockCard);
        metricsPanel.add(totalValueCard);
        metricsPanel.add(dateCard);

        return metricsPanel;
    }

    private JPanel createMetricCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(200, 120));

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        titleLabel.setForeground(new Color(100, 100, 100));

        // Value
        JLabel valueLabel = new JLabel(value, JLabel.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(color);

        JPanel valuePanel = new JPanel(new BorderLayout());
        valuePanel.setBackground(Color.WHITE);
        valuePanel.add(valueLabel, BorderLayout.CENTER);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valuePanel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createQuickActionsPanel() {
        JPanel actionsPanel = new JPanel(new FlowLayout());
        actionsPanel.setBackground(new Color(240, 245, 249));
        actionsPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        JButton refreshBtn = createActionButton("Refresh Data", new Color(41, 128, 185));
        JButton viewInventoryBtn = createActionButton("View Inventory", new Color(39, 174, 96));
        JButton addItemBtn = createActionButton("Add New Item", new Color(155, 89, 182));

        refreshBtn.addActionListener(e -> refreshData());
        viewInventoryBtn.addActionListener(e -> getMainFrame().navigateTo("INVENTORY"));
        addItemBtn.addActionListener(e -> getMainFrame().navigateTo("INVENTORY"));

        actionsPanel.add(refreshBtn);
        actionsPanel.add(viewInventoryBtn);
        actionsPanel.add(addItemBtn);

        return actionsPanel;
    }

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    public void refreshData() {
        // Get inventory from MainFrame or however you're managing it
        if (getMainFrame().getInventory() != null) {
            var inventory = getMainFrame().getInventory();
            // Update metrics using the existing Inventory class
            totalItemsLabel.setText(String.valueOf(inventory.getTotalItems()));
            lowStockLabel.setText(String.valueOf(inventory.getLowStockCount()));
            totalValueLabel.setText(String.format("$%.2f", inventory.getTotalStockValue()));
            currentDateLabel.setText(getCurrentDate());
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, yyyy");
        return dateFormat.format(new Date());
    }

    @Override
    public void onPageSelected() {
        // Refresh data when home page is selected
        refreshData();
    }
}
