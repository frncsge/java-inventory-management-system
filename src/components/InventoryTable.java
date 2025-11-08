package components;

import models.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class InventoryTable extends JPanel {
    private final DefaultTableModel tableModel;
    private final JTable table;
    private ArrayList<Item> items;

    public InventoryTable() {
        String[] cols = {"Item", "Price", "Quantity", "Category", "Date added"};

        //initialize table model and the table
        tableModel = new DefaultTableModel(cols, 0) {
            //prevent date col from being edited
            @Override
            public boolean isCellEditable(int row, int col) {
                return col != 4;
            }
        };
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component cell = super.prepareRenderer(renderer, row, col);

                //get the quantity of the row
                int qty = (int) getModel().getValueAt(row, 2);

                //check if qty is low
                if (qty <= 5) {
                    cell.setForeground(Color.RED);
                } else if (qty <= 10) {
                    cell.setForeground(Color.ORANGE);
                } else {
                    cell.setForeground(Color.BLACK);
                }

                return cell;
            }
        };

        //makes the tableModel listen for events like editing the value in a column
        tableModel.addTableModelListener(e -> {
            //get the row and col (both return their index in the table)
            int row = e.getFirstRow();
            int col = e.getColumn();

            //ignore if table is empty or is being updated
            if(row < 0 || col < 0 || this.items == null) return;

            Object newValue = tableModel.getValueAt(row, col);
            updateItem(row, col, newValue);
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void update(ArrayList<Item> items) {
        this.items = items;
        tableModel.setRowCount(0); //reset the rows back to 0 to avoid duplication

        for (Item item : items) {
            tableModel.addRow(new Object[]{
                item.getName(),
                item.getPrice(),
                item.getQty(),
                item.getCategory(),
                item.getDateTime()
            });
        }
    }

    private void updateItem(int row, int col, Object newValue) {
        Item item = items.get(row);

        switch (col){
            case 0 -> item.setName(newValue.toString());
            case 1 -> item.setPrice(new java.math.BigDecimal(newValue.toString()));
            case 2 -> item.setQty(Integer.parseInt(newValue.toString()));
            case 3 -> item.setCategory(newValue.toString());
        }
    }

    public JTable getTable() {
        return table;
    }
}
