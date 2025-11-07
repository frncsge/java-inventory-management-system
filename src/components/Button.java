package components;

import javax.swing.*;
import java.awt.event.ActionListener;

public class Button extends JButton {

    public Button(String label, ActionListener actionListener) {
        setText(label);
        addActionListener(actionListener);
    }
}
