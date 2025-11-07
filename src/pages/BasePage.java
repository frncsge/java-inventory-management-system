package pages;

import javax.swing.*;

//abstract class which pages like home, settings, etc. extends
public abstract class BasePage extends JPanel {

    //constructor
    public BasePage() {
        setUI();
    }

    //all subclasses of pages.BasePage must override this method
    protected abstract void setUI();
}
