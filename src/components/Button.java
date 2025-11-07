package models;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Button extends JButton {

    public Button(String label, ActionListener actionListener) {
        addActionListener(actionListener);
    }
}
