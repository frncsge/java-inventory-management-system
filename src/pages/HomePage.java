package pages;

import components.Input;

import javax.swing.*;
import java.awt.*;

public class HomePage extends BasePage {

    @Override
    public void setUI() {
        JLabel label = new JLabel("Home page");
        add(label);
    }
}
