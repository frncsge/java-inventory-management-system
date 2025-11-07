import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SidebarPanel extends JPanel {
    int sidebarPanelWidth = 180;
    int sidebarPanelHeight = 0;

    //this constructor will accept an action listener and pass it to the buttons
    public SidebarPanel(ActionListener actionListener) {

        //make the buttons in vertical position
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //set width
        setPreferredSize(new Dimension(sidebarPanelWidth, sidebarPanelHeight));

        //set the background color
        setBackground(Color.GRAY);

        //add the buttons
        add(createButton("Home", actionListener));
        add(createButton("Inventory", actionListener));
        add(createButton("Settings", actionListener));
    }

    //for creating the buttons
    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);

        //make the button as wide as the sidebar panel with 40px height
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        //sets the action command; like Home, Inventory, Settings
        button.setActionCommand(text);
        //add the passed actionListener to the button
        button.addActionListener(actionListener);

        return button;
    }
}