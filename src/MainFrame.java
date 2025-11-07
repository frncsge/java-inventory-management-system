import pages.HomePage;
import pages.InventoryPage;
import pages.SettingsPage;
import components.SidebarPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);

    public MainFrame() {
        setUpMainFrame();

        //create sidebar and pass down an action event
        SidebarPanel sidebar = new SidebarPanel(e -> {
            //show page based on the set action command. ex: Home, Inventory, Settings
            cardLayout.show(mainPanel, e.getActionCommand());
        });

        //add sidebar to the main frame
        add(sidebar, BorderLayout.WEST);

        setUpPages();

        //add main panel to the main frame
        add(mainPanel, BorderLayout.CENTER);
    }

    public void setUpMainFrame() {
        setTitle("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1000);
    }

    public void setUpPages() {
        mainPanel.add(new HomePage(), "Home");
        mainPanel.add(new InventoryPage(), "Inventory");
        mainPanel.add(new SettingsPage(), "Settings");
    }

    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }
}