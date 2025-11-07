package components;

import models.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class InventoryTable extends JPanel {
    private final DefaultTableModel tableModel;
    private final JTable table;

    public InventoryTable() {
        String[] cols = {"Item", "Price", "Quantity", "Category"};
        tableModel = new DefaultTableModel(cols, 0);
        table = new JTable(tableModel);

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void update(ArrayList<Item> items) {
        tableModel.setRowCount(0);

        for (Item item : items) {
            tableModel.addRow(new Object[]{
                item.getName(),
                item.getPrice(),
                item.getQty(),
                item.getCategory()
            });
        }
    }
}
