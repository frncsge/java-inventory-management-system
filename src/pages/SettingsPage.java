package pages;

import javax.swing.*;

public class SettingsPage extends BasePage {

    @Override
    public void setUI() {
        JLabel label = new JLabel("settings page");
        add(label);
    }
}
