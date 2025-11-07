import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel = new JPanel(cardLayout);

    public MainFrame() {
        //set up main frame
        setTitle("Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        //create sidebar and pass down an action listener
        SidebarPanel sidebar = new SidebarPanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //show page based on the set action command. ex: Home, Inventory, Settings
                cardLayout.show(mainPanel, e.getActionCommand());
            }
        });

        //add sidebar to the main frame
        add(sidebar, BorderLayout.WEST);

        //add pages in the mainPanel
        mainPanel.add(new HomePage(), "Home");
        mainPanel.add(new InventoryPage(), "Inventory");
        mainPanel.add(new SettingsPage(), "Settings");

        //add main panel to the main frame
        add(mainPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new MainFrame().setVisible(true);
    }
}
