package pages;

import javax.swing.*;

//abstract class which pages like home, settings, etc. extends
public abstract class BasePage extends JPanel {
    private MainFrame mainFrame;

    //constructor with MainFrame parameter
    public BasePage(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setUI();
    }

    //all subclasses of pages.BasePage must override this method
    protected abstract void setUI();

    // Getter for MainFrame - allows pages to access navigation methods
    protected MainFrame getMainFrame() {
        return mainFrame;
    }

    // Optional method that can be overridden by subclasses
    // Called when the page becomes visible (when navigated to)
    public void onPageSelected() {
        // Default implementation does nothing
        // Subclasses can override to refresh data when page is shown
    }
}
