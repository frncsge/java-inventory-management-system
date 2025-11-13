package pages;

import app.MainFrame;
import components.*;
import components.Button;
import models.Inventory;
import models.Item;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class InventoryPage extends BasePage {
    Input nameInput, priceInput, qtyInput, categoryInput;
    private Inventory inventory;
    private InventoryTable inventoryTable;
    private SearchBar searchBar;
    private ArrayList<Item> currDisplayedItems;

    public InventoryPage(MainFrame mainFrame, Inventory inventory) {
        super(mainFrame);
        this.inventory = inventory;
        setUI();
    }

    @Override
    public void setUI() {
        setLayout(new BorderLayout());

        // Initialize inventoryTable (inventory is now passed via constructor)
        inventoryTable = new InventoryTable();

        // Add padding to the table
        inventoryTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create search bar
        searchBar = new SearchBar(e -> searchItem(), e -> showAllItem());

        // Initialize current displayed items
        currDisplayedItems = new ArrayList<>(inventory.getItems());

        // Add the panel containing all inputs
        add(inputs(), BorderLayout.WEST);
        // Add table
        add(inventoryTable, BorderLayout.CENTER);
        // Add delete button and sort buttons
        add(new JScrollPane(buttons()), BorderLayout.SOUTH);
        // Add search bar
        add(searchBar, BorderLayout.NORTH);

        // Update table with initial data
        inventoryTable.update(currDisplayedItems);
    }

    // Creates a panel containing all inputs and the add button
    private JPanel inputs() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15)); // Add padding

        nameInput = new Input("Item name");
        priceInput = new Input("Price");
        qtyInput = new Input("Quantity");
        categoryInput = new Input("Category");

        Input[] allInputs = {nameInput, priceInput, qtyInput, categoryInput};
        for (Input input : allInputs) {
            input.setMaximumSize(input.getPreferredSize()); // Maintains the default size of the inputs
            panel.add(input); // Add input to the panel
        }

        // Add and clear buttons
        Button addButton = new Button("Add", e -> addItemToInventory());
        Button clearButton = new Button("Clear", e -> clearInputValue());

        panel.add(addButton);
        panel.add(clearButton);

        return panel;
    }

    private JPanel buttons() {
        JPanel panel = new JPanel();

        // Initialize delete item button
        Button deleteButton = new Button("Delete Selected Item", e -> deleteSelectedItem());
        deleteButton.setForeground(Color.red);

        // Initialize sort buttons
        Button sortByNameAsc = new Button("Sort by Name ↑", e -> sortBy("name", "asc"));
        Button sortByNameDesc = new Button("Sort by Name ↓", e -> sortBy("name", "desc"));

        Button sortByPriceAsc = new Button("Sort by Price ↑", e -> sortBy("price", "asc"));
        Button sortByPriceDesc = new Button("Sort by Price ↓", e -> sortBy("price", "desc"));

        Button sortByQtyAsc = new Button("Sort by Quantity ↑", e -> sortBy("qty", "asc"));
        Button sortByQtyDesc = new Button("Sort by Quantity ↓", e -> sortBy("qty", "desc"));

        Button sortByCategoryAsc = new Button("Sort by Category ↑", e -> sortBy("category", "asc"));
        Button sortByCategoryDesc = new Button("Sort by Category ↓", e -> sortBy("category", "desc"));

        Button sortByDateAsc = new Button("Sort by Date added ↑", e -> sortBy("date", "asc"));
        Button sortByDateDesc = new Button("Sort by Date added ↓", e -> sortBy("date", "desc"));

        // Add to panel
        panel.add(createButtonGroup(sortByNameAsc, sortByNameDesc));
        panel.add(createButtonGroup(sortByPriceAsc, sortByPriceDesc));
        panel.add(createButtonGroup(sortByQtyAsc, sortByQtyDesc));
        panel.add(createButtonGroup(sortByCategoryAsc, sortByCategoryDesc));
        panel.add(createButtonGroup(sortByDateAsc, sortByDateDesc));

        panel.add(deleteButton);

        return panel;
    }

    private JPanel createButtonGroup(JButton ascButton, JButton descButton) {
        JPanel group = new JPanel();
        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
        group.add(ascButton);
        group.add(descButton);

        return group;
    }

    private void addItemToInventory() {
        // Extract input values
        String name = nameInput.getInput();
        String priceStr = priceInput.getInput();
        String qtyStr = qtyInput.getInput();
        String category = categoryInput.getInput();

        // Check if an input is empty
        if (name.isEmpty() || priceStr.isEmpty() || qtyStr.isEmpty() || category.isEmpty()) {
            new ErrorAlert("Empty Input", "Please fill in all input fields.");
            return;
        }

        // Validate price, show dialog box for error
        BigDecimal price;
        try {
            price = new BigDecimal(priceStr);

            if(price.compareTo(BigDecimal.ZERO) <= 0) { // Returns -1 if price < 0; 0 if price = 0; 1 if price > 0
                new ErrorAlert("Invalid Input", "Price must be more than 0.");
                return ;
            }
        } catch (NumberFormatException e) {
            new ErrorAlert("Invalid Input", "Price must be a number.");
            return;
        }

        // Validate qty, show dialog box for error
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

        // Add item to inventory and update table
        inventory.addItem(new Item(name, price, qty, category));
        currDisplayedItems = new ArrayList<>(inventory.getItems());
        inventoryTable.update(currDisplayedItems);

        // Clear input if item is added
        clearInputValue();
    }

    private void searchItem() {
        String query = searchBar.getQuery();

        // Create a new array list containing a filtered inventory based on the query/search
        currDisplayedItems = new ArrayList<>(
                inventory.getItems().stream()
                        .filter(item -> item.getName().toLowerCase().contains(query.toLowerCase()))
                        .toList()
        );

        // Update the table
        inventoryTable.update(currDisplayedItems);

        // Clear search bar
        searchBar.clear();
    }

    public void showAllItem() {
        currDisplayedItems = new ArrayList<>(inventory.getItems());
        inventoryTable.update(currDisplayedItems);
    }

    // For deleting an item in the table and inventory
    private void deleteSelectedItem() {
        int selectedRow = inventoryTable.getTable().getSelectedRow(); // Returns the index of the item from the table
        if(selectedRow < 0) {
            new ErrorAlert("No Selected Item", "You need to select an item from the table to delete.");
            return;
        }

        // Get the actual object from the currently displayed items
        Item itemToDelete = currDisplayedItems.get(selectedRow);

        // Remove item from the inventory
        inventory.getItems().remove(itemToDelete);

        // Remove from the displayed list
        currDisplayedItems.remove(itemToDelete);

        // Update the inventory table
        inventoryTable.update(currDisplayedItems);
    }

    // For sorting
    private void sortBy(String type, String order) {
        switch(type) {
            case "name" -> inventory.sortByName(order);
            case "price" -> inventory.sortByPrice(order);
            case "qty" -> inventory.sortByQty(order);
            case "category" -> inventory.sortByCategory(order);
            case "date" -> inventory.sortByDateAdded(order);
        }

        // Update the inventory table
        currDisplayedItems = new ArrayList<>(inventory.getItems());
        inventoryTable.update(currDisplayedItems);
    }

    // Clears the value of all inputs
    private void clearInputValue() {
        Input[] allInputs = {nameInput, priceInput, qtyInput, categoryInput};

        for (Input input : allInputs) {
            input.setInput("");
        }
    }

    @Override
    public void onPageSelected() {
        // Refresh the table data when page is selected
        currDisplayedItems = new ArrayList<>(inventory.getItems());
        inventoryTable.update(currDisplayedItems);
    }
}
