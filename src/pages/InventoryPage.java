package pages;

import components.Button;
import components.ErrorAlert;
import components.Input;
import components.InventoryTable;
import models.Inventory;
import models.Item;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.math.BigDecimal;

public class InventoryPage extends BasePage {
    Input nameInput, priceInput, qtyInput, categoryInput;
    private Inventory inventory;
    private InventoryTable inventoryTable;

    @Override
    public void setUI() {
        setLayout(new BorderLayout());

        //title label
        JLabel titleLabel = new JLabel("Inventory", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        //initialize inventory and inventoryTable
        inventory = new Inventory();
        inventoryTable = new InventoryTable();

        //add padding to the table
        inventoryTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //add the panel containing all inputs
        add(inputs(), BorderLayout.WEST);
        //add table
        add(inventoryTable, BorderLayout.CENTER);
        //add delete button and sort buttons
        add(buttons(), BorderLayout.SOUTH);
    }

    //creates a panel containing all inputs and the add button
    private JPanel inputs() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15)); //add padding

        nameInput = new Input("Item name");
        priceInput = new Input("Price");
        qtyInput = new Input("Quantity");
        categoryInput = new Input("Category");

        Input[] allInputs = {nameInput, priceInput, qtyInput, categoryInput};
        for (Input input : allInputs) {
            input.setMaximumSize(input.getPreferredSize()); //maintains the default size of the inputs
            panel.add(input); //add input to the panel
        }

        //add and clear buttons
        Button addButton = new Button("Add", e -> addItemToInventory());
        Button clearButton = new Button("Clear", e -> clearInputValue());

        panel.add(addButton);
        panel.add(clearButton);

        return panel;
    }

    private JPanel buttons() {
        JPanel panel = new JPanel();

        //initialize delete item button
        Button deleteButton = new Button("Delete Selected Item", e -> deleteSelectedItem());
        deleteButton.setForeground(Color.red);

        //initialize sort buttons
        Button sortByName = new Button("Sort by Name", e -> {sortBy("name");});
        Button sortByPrice = new Button("Sort by Price", e -> {sortBy("price");});
        Button sortByQty = new Button("Sort by Quantity", e -> {sortBy("qty");});
        Button sortByCategory = new Button("Sort by Category", e -> {sortBy("category");});
        Button sortByDate = new Button("Sort by Date added", e -> {sortBy("date");});

        //add to panel
        panel.add(deleteButton);
        panel.add(sortByName);
        panel.add(sortByPrice);
        panel.add(sortByQty);
        panel.add(sortByCategory);
        panel.add(sortByDate);

        return panel;
    }

    private void addItemToInventory() {
        //extract input values
        String name = nameInput.getInput();
        String priceStr = priceInput.getInput();
        String qtyStr = qtyInput.getInput();
        String category = categoryInput.getInput();

        //check if an input is empty
        if (name.isEmpty() || priceStr.isEmpty() || qtyStr.isEmpty() || category.isEmpty()) {
            new ErrorAlert("Empty Input", "Please fill in all input fields.");
            return;
        }

        //validate price, show dialog box for error
        BigDecimal price;
        try {
            price = new BigDecimal(priceStr);

            if(price.compareTo(BigDecimal.ZERO) <= 0) { //returns -1 if price < 0; 0 if price = 0; 1 if price > 0
                new ErrorAlert("Invalid Input", "Price must be more than 0.");
                return ;
            }
        } catch (NumberFormatException e) {
            new ErrorAlert("Invalid Input", "Price must be a number.");
            return;
        }

        //validate qty, show dialog box for error
        int qty;
        try {
            qty = Integer.parseInt(qtyStr);

            if(qty <= 0) {
                new ErrorAlert("Invalid Input", "Quantity must be more than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            new ErrorAlert("Invalid Input", "Quantity must be a number.");
            return;
        }

        //add item to inventory and update table
        inventory.addItem(new Item(name, price, qty, category));
        inventoryTable.update(inventory.getItems());

        //clear input if item is added
        clearInputValue();
    }

    //for deleting an item in the table and inventory
    private void deleteSelectedItem() {
        int selectedRow = inventoryTable.getTable().getSelectedRow(); //returns the index of the item from the table
        if(selectedRow < 0) {
            new ErrorAlert("No Selected Item", "You need to select an item from the table to delete.");
            return;
        }

        //remove item from the inventory class' arrayList and update the table
        inventory.getItems().remove(selectedRow);
        inventoryTable.update(inventory.getItems());
    }

    //for sorting
    private void sortBy(String type) {
        switch(type) {
            case "name" -> inventory.sortByName();
            case "price" -> inventory.sortByPrice();
            case "qty" -> inventory.sortByQty();
            case "category" -> inventory.sortByCategory();
            case "date" -> inventory.sortByDateAdded();
        }

        //update the inventory table
        inventoryTable.update(inventory.getItems());
    }

    //clears the value of all inputs
    private void clearInputValue() {
        Input[] allInputs = {nameInput, priceInput, qtyInput, categoryInput};

        for (Input input : allInputs) {
            input.setInput("");
        }
    }
}
