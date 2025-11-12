import pages.HomePage;
import pages.InventoryPage;
import pages.SettingsPage;
import components.SidebarPanel;
import models.Inventory;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);
    private final Inventory inventory = new Inventory(); // Shared inventory instance

    public MainFrame() {
        setUpMainFrame();

        //create sidebar and pass down an action event
        SidebarPanel sidebar = new SidebarPanel(e -> {
            //show page based on the set action command. ex: Home, Inventory, Settings
            cardLayout.show(mainPanel, e.getActionCommand());
            
            // Refresh the home page when navigating to it
            if ("Home".equals(e.getActionCommand())) {
                HomePage homePage = (HomePage) mainPanel.getComponent(0);
                homePage.onPageSelected();
            }
        });

        //add sidebar to the main frame
        add(sidebar, BorderLayout.WEST);

        setUpPages();

        //add main panel to the main frame
        add(mainPanel, BorderLayout.CENTER);
    }

    private void setUpMainFrame() {
        setTitle("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void setUpPages() {
        // Pass the shared inventory instance to all pages
        mainPanel.add(new HomePage(this, inventory), "Home");
        mainPanel.add(new InventoryPage(this, inventory), "Inventory");
        mainPanel.add(new SettingsPage(this), "Settings"); // Settings might not need inventory
    }

    // Navigation method for pages to use
    public void navigateTo(String pageName) {
        cardLayout.show(mainPanel, pageName);
        
        // Refresh home page data when navigating to it
        if ("Home".equals(pageName)) {
            HomePage homePage = (HomePage) mainPanel.getComponent(0);
            homePage.onPageSelected();
        }
    }

    // Getter for inventory (optional, but useful)
    public Inventory getInventory() {
        return inventory;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
