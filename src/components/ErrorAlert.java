package components;

import javax.swing.*;

public class ErrorAlert extends JOptionPane {

    public ErrorAlert(String title, String message) {
        showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
