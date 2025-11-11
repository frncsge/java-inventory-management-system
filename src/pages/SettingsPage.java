package pages;

import models.Inventory;
import components.InventoryTable;
import javax.swing.*;
import java.awt.*;

public class SettingsPage extends JPanel {

    // Fields
    private JCheckBox darkModeToggle;
    private Color lightBg = Color.WHITE;
    private Color darkBg = new Color(30, 30, 30);
    private Color lightText = Color.BLACK;
    private Color darkText = Color.WHITE;
    private Color darkPanel = new Color(50, 50, 50);
    private Color darkField = new Color(70, 70, 70);

    private JFrame parentFrame;
    private JLabel title;
    private JScrollPane scrollPane;

    // Static variable to store dark mode state across all pages
    private static boolean isDarkMode = false;

    // Static references shared between pages
    private static Inventory sharedInventory = null;
    private static InventoryTable sharedInventoryTable = null;

    // Store original window state
    private Dimension originalSize;
    private int originalState;
    private boolean wasMaximized;

    public SettingsPage() {
        setLayout(new BorderLayout());
        setBackground(isDarkMode ? darkBg : lightBg);

        // ===== TITLE =====
        title = new JLabel("⚙️ System Settings", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        title.setForeground(isDarkMode ? darkText : lightText);

        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        mainPanel.setBackground(isDarkMode ? darkPanel : lightBg);

        // ===== SYSTEM APPEARANCE =====
        JPanel appearancePanel = createSectionPanel("🎨 System Appearance");
        darkModeToggle = new JCheckBox("Enable Full Screen Dark Mode");
        darkModeToggle.setSelected(isDarkMode);
        appearancePanel.add(darkModeToggle);

        // ===== SYSTEM CONTROLS =====
        JPanel controlsPanel = createSectionPanel("🎮 System Controls");
        JButton clearInventoryButton = new JButton("Clear Inventory");
        JButton exitButton = new JButton("Exit Program");

        clearInventoryButton.setForeground(Color.RED);

        controlsPanel.add(clearInventoryButton);
        controlsPanel.add(exitButton);

        // ===== ABOUT SYSTEM =====
        JPanel aboutPanel = createSectionPanel("ℹ️ About System");
        JTextArea aboutText = new JTextArea(
                "Grocery Inventory Management System v1.0\n" +
                "Developed by:\nFrancis Ge Amoncio\nMar Lloyd Ingking\nNikko Villafuerte"
        );
        aboutText.setEditable(false);
        aboutText.setBackground(isDarkMode ? darkField : new Color(245, 245, 245));
        aboutText.setForeground(isDarkMode ? darkText : lightText);
        aboutText.setFont(new Font("Arial", Font.PLAIN, 13));
        aboutPanel.add(aboutText);

        // ===== ADD SECTIONS =====
        mainPanel.add(appearancePanel);
        mainPanel.add(controlsPanel);
        mainPanel.add(aboutPanel);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(isDarkMode ? darkBg : lightBg);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ===== EVENT HANDLERS =====
        darkModeToggle.addActionListener(e -> toggleDarkMode());
        clearInventoryButton.addActionListener(e -> clearInventory());
        exitButton.addActionListener(e -> exitProgram());

        // Apply theme if dark mode was already enabled
        if (isDarkMode) {
            updateComponentColor(this, true);
        }
    }

    // Static methods to share data from InventoryPage
    public static void setInventory(Inventory inventory) {
        sharedInventory = inventory;
    }

    public static void setInventoryTable(InventoryTable table) {
        sharedInventoryTable = table;
    }

    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(isDarkMode ? darkPanel : lightBg);
        return panel;
    }

    private void toggleDarkMode() {
        isDarkMode = darkModeToggle.isSelected();
        boolean dark = isDarkMode;
        Color bg = dark ? darkBg : lightBg;
        Color text = dark ? darkText : lightText;

        if (parentFrame == null)
            parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (parentFrame != null) {
            // Save original size and state before enabling dark mode
            if (dark) {
                originalSize = parentFrame.getSize();
                originalState = parentFrame.getExtendedState();
                wasMaximized = (originalState & JFrame.MAXIMIZED_BOTH) == JFrame.MAXIMIZED_BOTH;

                parentFrame.dispose();
                parentFrame.setUndecorated(true);
                parentFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                parentFrame.setVisible(true);
            } else {
                parentFrame.dispose();
                parentFrame.setUndecorated(false);

                if (wasMaximized) {
                    parentFrame.setExtendedState(originalState);
                } else {
                    parentFrame.setExtendedState(JFrame.NORMAL);
                    if (originalSize != null) {
                        parentFrame.setSize(originalSize);
                    } else {
                        parentFrame.setSize(900, 700);
                    }
                }

                parentFrame.setVisible(true);
            }

            // FIXED: Only apply theme to SettingsPage, not the entire frame
            updateComponentColor(this, dark);
        }

        title.setForeground(text);
        setBackground(bg);
        scrollPane.getViewport().setBackground(bg);
        repaint();
    }

    private void updateComponentColor(Component comp, boolean dark) {
        Color bg = dark ? darkBg : lightBg;
        Color panelBg = dark ? darkPanel : lightBg;
        Color fieldBg = dark ? darkField : Color.WHITE;
        Color text = dark ? darkText : lightText;

        if (comp instanceof JLabel) {
            ((JLabel) comp).setForeground(text);
        } else if (comp instanceof JTextField) {
            ((JTextField) comp).setBackground(fieldBg);
            ((JTextField) comp).setForeground(text);
        } else if (comp instanceof JTextArea) {
            ((JTextArea) comp).setBackground(fieldBg);
            ((JTextArea) comp).setForeground(text);
        } else if (comp instanceof JCheckBox) {
            ((JCheckBox) comp).setBackground(panelBg);
            ((JCheckBox) comp).setForeground(text);
        } else if (comp instanceof JButton) {
            ((JButton) comp).setBackground(dark ? new Color(80, 80, 80) : Color.LIGHT_GRAY);
            ((JButton) comp).setForeground(text);
        } else if (comp instanceof JPanel) {
            ((JPanel) comp).setBackground(panelBg);
            for (Component child : ((JPanel) comp).getComponents()) {
                updateComponentColor(child, dark);
            }
        } else if (comp instanceof JScrollPane) {
            JScrollPane scroll = (JScrollPane) comp;
            scroll.getViewport().setBackground(bg);
            scroll.setBackground(bg);
            Component view = scroll.getViewport().getView();
            if (view != null)
                updateComponentColor(view, dark);
        }
    }

    private void clearInventory() {
        if (sharedInventory == null) {
            JOptionPane.showMessageDialog(this, "⚠️ Inventory not available!");
            return;
        }

        // 🔹 Check if inventory is empty
        if (sharedInventory.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "📭 No Inventory Listed!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to clear all items from the inventory?\nThis action cannot be undone.",
                "Clear Inventory Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            sharedInventory.getItems().clear();
            if (sharedInventoryTable != null) {
                sharedInventoryTable.update(sharedInventory.getItems());
            }
            JOptionPane.showMessageDialog(this, "🧹 Inventory cleared successfully!");
        }
    }

    private void exitProgram() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit the program?",
                "Exit Confirmation",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Settings Page - Grocery Inventory System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.add(new SettingsPage());
        frame.setVisible(true);
    }
}