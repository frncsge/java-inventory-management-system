package pages;

import javax.swing.*;

public class HomePage extends BasePage {

    @Override
    public void setUI() {
        JLabel label = new JLabel("home page");
        add(label);
    }
}
