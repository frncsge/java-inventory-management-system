package pages;

import app.MainFrame;
import models.Inventory;
import components.InventoryTable;
import javax.swing.*;
import java.awt.*;
import java.util.IdentityHashMap;
import java.util.Map;

public class SettingsPage extends BasePage {

    private JCheckBox darkModeToggle;
    private JFrame parentFrame;
    private Inventory inventory;
    private Runnable onInventoryChange;

    // DARK THEME COLORS
    private static final Color DARK_BG = new Color(30, 30, 30);
    private static final Color DARK_PANEL = new Color(50, 50, 50);
    private static final Color DARK_FIELD = new Color(70, 70, 70);
    private static final Color DARK_TEXT = Color.WHITE;

    // GLOBAL THEME STATE
    private static boolean isDarkMode = false;

    // Save all original colors here
    private static final Map<Component, Color> originalBg = new IdentityHashMap<>();
    private static final Map<Component, Color> originalFg = new IdentityHashMap<>();

    private static Inventory sharedInventory = null;
    private static InventoryTable sharedInventoryTable = null;

    private JLabel title;
    private JScrollPane scrollPane;

    public SettingsPage(Inventory inventory, Runnable onInventoryChange) {
        this.inventory = inventory;
        this.onInventoryChange = onInventoryChange;
        setUI();
    }

    @Override
    public void setUI() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // TITLE AREA
        title = new JLabel("⚙️ System Settings", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // MAIN CONTENT
        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // SECTION 1 – Appearance
        JPanel appearancePanel = createSectionPanel("🎨 System Appearance");
        darkModeToggle = new JCheckBox("Enable Full System Dark Mode");
        darkModeToggle.setOpaque(false);
        appearancePanel.add(darkModeToggle);

        // SECTION 2 – Controls
        JPanel controlsPanel = createSectionPanel("🎮 System Controls");
        JButton clearInventoryButton = new JButton("Clear Inventory");
        JButton exitButton = new JButton("Exit Program");
        controlsPanel.add(clearInventoryButton);
        controlsPanel.add(exitButton);

        // SECTION 3 – About
        JPanel aboutPanel = createSectionPanel("ℹ️ About System");
        JTextArea aboutText = new JTextArea(
                "Grocery Inventory Management System v1.0\n" +
                        "Developed by:\nFrancis Ge Amoncio\nMar Lloyd Ingking\nNikko Villafuerte"
        );
        aboutText.setEditable(false);
        aboutText.setBackground(new Color(245, 245, 245));
        aboutPanel.add(aboutText);

        // ADD SECTIONS
        mainPanel.add(appearancePanel);
        mainPanel.add(controlsPanel);
        mainPanel.add(aboutPanel);

        scrollPane = new JScrollPane(mainPanel);
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(title, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // EVENTS
        darkModeToggle.addActionListener(e -> toggleDarkMode());
        clearInventoryButton.addActionListener(e -> clearInventory());
        exitButton.addActionListener(e -> exitProgram());

        // Save original colors when page loads
        SwingUtilities.invokeLater(() -> {
            parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (parentFrame != null) {
                saveOriginalColors(parentFrame);
            }
        });
    }

    // ============================================================
    // ✅ CREATE SECTION PANEL
    // ============================================================
    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.setBackground(Color.WHITE);
        return panel;
    }

    // ============================================================
    // ✅ DARK MODE TOGGLE
    // ============================================================
    private void toggleDarkMode() {
        isDarkMode = darkModeToggle.isSelected();

        if (parentFrame == null)
            parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (!isDarkMode) {
            restoreOriginalColors(parentFrame);
            updateTableTheme(false); // ✅ restore table colors for light mode
            parentFrame.repaint();
            return;
        }

        saveOriginalColors(parentFrame);

        applyDarkTheme(parentFrame);
        updateTableTheme(true); // ✅ apply table dark mode theme
        parentFrame.repaint();
    }

    // ============================================================
    // ✅ SAVE ORIGINAL COLORS OF ALL COMPONENTS
    // ============================================================
    private void saveOriginalColors(Component comp) {
        if (!originalBg.containsKey(comp)) {
            originalBg.put(comp, comp.getBackground());
            originalFg.put(comp, comp.getForeground());
        }

        if (comp instanceof Container container) {
            for (Component child : container.getComponents()) {
                saveOriginalColors(child);
            }
        }
    }

    // ============================================================
    // ✅ RESTORE ORIGINAL COLORS
    // ============================================================
    private void restoreOriginalColors(Component comp) {

        if (originalBg.containsKey(comp))
            comp.setBackground(originalBg.get(comp));

        if (originalFg.containsKey(comp))
            comp.setForeground(originalFg.get(comp));

        if (comp instanceof Container container) {
            for (Component child : container.getComponents()) {
                restoreOriginalColors(child);
            }
        }
    }

    // ============================================================
    // ✅ APPLY DARK THEME
    // ============================================================
    private void applyDarkTheme(Component comp) {

        if (comp instanceof JLabel ||
//            comp instanceof JButton ||
                comp instanceof JCheckBox ||
                comp instanceof JTextField ||
                comp instanceof JTextArea) {
            comp.setForeground(DARK_TEXT);
        }

        if (comp instanceof JTextField || comp instanceof JTextArea) {
            comp.setBackground(DARK_FIELD);
        }
        else if (comp instanceof JPanel) {
            comp.setBackground(DARK_PANEL);
        }
        else if (comp instanceof JScrollPane scroll) {
            scroll.getViewport().setBackground(DARK_BG);
            scroll.setBackground(DARK_BG);
        }
        else {
            comp.setBackground(DARK_BG);
        }

        if (comp instanceof Container container) {
            for (Component child : container.getComponents()) {
                applyDarkTheme(child);
            }
        }
    }

    // ============================================================
    // ✅ FIX TABLE COLORS FOR DARK & LIGHT THEMES
    // ============================================================
    private void updateTableTheme(boolean dark) {
        if (sharedInventoryTable == null) return;
        JTable table = sharedInventoryTable.getTable();

        if (dark) {
            table.setBackground(new Color(45, 45, 45));
            table.setForeground(Color.WHITE);
            table.setGridColor(new Color(80, 80, 80));
            table.getTableHeader().setBackground(new Color(60, 60, 60));
            table.getTableHeader().setForeground(Color.WHITE);
            table.setSelectionBackground(new Color(70, 70, 90));
            table.setSelectionForeground(Color.WHITE);
        } else {
            table.setBackground(Color.WHITE);
            table.setForeground(Color.BLACK);
            table.setGridColor(new Color(220, 220, 220));
            table.getTableHeader().setBackground(new Color(240, 240, 240));
            table.getTableHeader().setForeground(Color.BLACK);
            table.setSelectionBackground(new Color(184, 207, 229)); // default blue highlight
            table.setSelectionForeground(Color.BLACK);
        }
    }

    // ============================================================
    // ✅ INVENTORY HANDLING
    // ============================================================
    public static void setInventory(Inventory inventory) { sharedInventory = inventory; }
    public static void setInventoryTable(InventoryTable table) { sharedInventoryTable = table; }

    private void clearInventory() {
        if (inventory == null) {
            JOptionPane.showMessageDialog(this, "⚠️ Inventory not available!");
            return;
        }

        if (inventory.getItems().isEmpty()) {
            JOptionPane.showMessageDialog(this, "📭 No Inventory Listed!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this, "Are you sure you want to clear all items?", "Clear Inventory",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            inventory.getItems().clear();
            if (sharedInventoryTable != null)
                sharedInventoryTable.update(inventory.getItems());

            if(onInventoryChange != null) onInventoryChange.run();

            JOptionPane.showMessageDialog(this, "🧹 Inventory cleared successfully!");
        }
    }

    private void exitProgram() {
        int confirm = JOptionPane.showConfirmDialog(
                this, "Are you sure you want to exit?", "Exit Program",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) System.exit(0);
    }
}

