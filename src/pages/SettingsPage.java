package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SettingsPage extends JPanel {

    // Fields
    private JTextField storeNameField, addressField, contactField;
    private JCheckBox darkModeToggle;
    private Color lightBg = Color.WHITE;
    private Color darkBg = new Color(30, 30, 30);
    private Color lightText = Color.BLACK;
    private Color darkText = Color.WHITE;
    private Color darkPanel = new Color(50, 50, 50);
    private Color darkField = new Color(70, 70, 70);

    private JFrame parentFrame; // for applying dark mode on whole frame
    private JLabel title;
    private JScrollPane scrollPane;

    // Static variable to store dark mode state across all pages
    private static boolean isDarkMode = false;

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
        mainPanel.setLayout(new GridLayout(4, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        mainPanel.setBackground(isDarkMode ? darkPanel : lightBg);

        // ===== STORE INFORMATION =====
        JPanel storeInfoPanel = createSectionPanel("🏪 Store Information");
        storeNameField = new JTextField("freshmart", 10);
        addressField = new JTextField("Tagbilaran Bohol", 10);
        contactField = new JTextField("0955223344", 10);
        storeInfoPanel.add(new JLabel("Store Name:"));
        storeInfoPanel.add(storeNameField);
        storeInfoPanel.add(new JLabel("Address:"));
        storeInfoPanel.add(addressField);
        storeInfoPanel.add(new JLabel("Contact Number:"));
        storeInfoPanel.add(contactField);

        // ===== SYSTEM APPEARANCE =====
        JPanel appearancePanel = createSectionPanel("🎨 System Appearance");
        darkModeToggle = new JCheckBox("Enable Full Screen Dark Mode");
        darkModeToggle.setSelected(isDarkMode);
        appearancePanel.add(darkModeToggle);

        // ===== SYSTEM MAINTENANCE =====
        JPanel maintenancePanel = createSectionPanel("💾 System Maintenance");
        JButton backupButton = new JButton("Backup Data");
        JButton restoreButton = new JButton("Restore Data");
        JButton clearButton = new JButton("Clear Store Info");
        JButton exitButton = new JButton("Exit Program"); // 🔹 NEW BUTTON
        maintenancePanel.add(backupButton);
        maintenancePanel.add(restoreButton);
        maintenancePanel.add(clearButton);
        maintenancePanel.add(exitButton); // 🔹 Add Exit Button

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
        mainPanel.add(storeInfoPanel);
        mainPanel.add(appearancePanel);
        mainPanel.add(maintenancePanel);
        mainPanel.add(aboutPanel);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getViewport().setBackground(isDarkMode ? darkBg : lightBg);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ===== EVENT HANDLERS =====
        darkModeToggle.addActionListener(e -> toggleDarkMode());
        backupButton.addActionListener(e -> backupData());
        restoreButton.addActionListener(e -> restoreData());
        clearButton.addActionListener(e -> clearData());
        exitButton.addActionListener(e -> exitProgram()); // 🔹 Exit Button Function

        // Apply theme if dark mode was already enabled
        if (isDarkMode) {
            updateComponentColor(this, true);
        }
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
            // Fullscreen dark mode
            if (dark) {
                parentFrame.dispose();
                parentFrame.setUndecorated(true);
                parentFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                parentFrame.setVisible(true);
            } else {
                parentFrame.dispose();
                parentFrame.setUndecorated(false);
                parentFrame.setExtendedState(JFrame.NORMAL);
                parentFrame.setSize(900, 700);
                parentFrame.setVisible(true);
            }

            applyThemeToFrame(parentFrame, dark);
        }

        title.setForeground(text);
        setBackground(bg);
        scrollPane.getViewport().setBackground(bg);
        repaint();
    }

    private void applyThemeToFrame(Container container, boolean dark) {
        for (Component comp : container.getComponents()) {
            updateComponentColor(comp, dark);
            if (comp instanceof Container)
                applyThemeToFrame((Container) comp, dark);
        }
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

    private void backupData() {
        try (PrintWriter writer = new PrintWriter("settings_backup.txt")) {
            writer.println(storeNameField.getText());
            writer.println(addressField.getText());
            writer.println(contactField.getText());
            writer.println(isDarkMode);
            JOptionPane.showMessageDialog(this, "✅ Backup saved successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Error saving backup!");
        }
    }

    private void restoreData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("settings_backup.txt"))) {
            storeNameField.setText(reader.readLine());
            addressField.setText(reader.readLine());
            contactField.setText(reader.readLine());
            boolean dark = Boolean.parseBoolean(reader.readLine());
            if (dark != isDarkMode) {
                darkModeToggle.setSelected(dark);
                toggleDarkMode();
            }
            JOptionPane.showMessageDialog(this, "✅ Data restored successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "⚠️ No backup found!");
        }
    }

    private void clearData() {
        storeNameField.setText("");
        addressField.setText("");
        contactField.setText("");
        JOptionPane.showMessageDialog(this, "🧹 Cleared all store info!");
    }

    // 🔹 EXIT PROGRAM FUNCTION
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
