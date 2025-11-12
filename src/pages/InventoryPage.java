package pages;

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

    @Override
    public void setUI() {
        setLayout(new BorderLayout());

        //initialize inventory and inventoryTable
        inventory = new Inventory();
        inventoryTable = new InventoryTable();

         SettingsPage.setInventory(inventory);
         SettingsPage.setInventoryTable(inventoryTable);
        //add padding to the table
        inventoryTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //create search bar
        searchBar = new SearchBar(e -> searchItem(), e -> showAllItem());

        //add the panel containing all inputs
        add(inputs(), BorderLayout.WEST);
        //add table
        add(inventoryTable, BorderLayout.CENTER);
        //add delete button and sort buttons
        add(new JScrollPane(buttons()), BorderLayout.SOUTH);
        //add search bar
        add(searchBar, BorderLayout.NORTH);
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

        //add to panel
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
        currDisplayedItems = new ArrayList<>(inventory.getItems());
        inventoryTable.update(currDisplayedItems);

        //clear input if item is added
        clearInputValue();
    }

    private void searchItem() {
        String query = searchBar.getQuery();

        //create a new array list containing a filtered inventory based on the query/search
        currDisplayedItems = new ArrayList<>(
                inventory.getItems().stream()
                        .filter(item -> item.getName().toLowerCase().contains(query))
                        .toList()
        );

        //update the table
        inventoryTable.update(currDisplayedItems);

        //clear search bar
        searchBar.clear();
    }

    private void showAllItem() {
        currDisplayedItems = new ArrayList<>(inventory.getItems());
        inventoryTable.update(currDisplayedItems);
    }

    //for deleting an item in the table and inventory
    private void deleteSelectedItem() {
        int selectedRow = inventoryTable.getTable().getSelectedRow(); //returns the index of the item from the table
        if(selectedRow < 0) {
            new ErrorAlert("No Selected Item", "You need to select an item from the table to delete.");
            return;
        }

        //get the actual object from the currently displayed items
        Item itemToDelete = currDisplayedItems.get(selectedRow);

        //remove item from the inventory
        inventory.getItems().remove(itemToDelete);

        //remove from the displayed list
        currDisplayedItems.remove(itemToDelete);

        //update the inventory table
        inventoryTable.update(currDisplayedItems);
    }

    //for sorting
    private void sortBy(String type, String order) {

        switch(type) {
            case "name" -> inventory.sortByName(order);
            case "price" -> inventory.sortByPrice(order);
            case "qty" -> inventory.sortByQty(order);
            case "category" -> inventory.sortByCategory(order);
            case "date" -> inventory.sortByDateAdded(order);
        }

        //update the inventory table
        currDisplayedItems = new ArrayList<>(inventory.getItems());
        inventoryTable.update(currDisplayedItems);
    }

    //clears the value of all inputs
    private void clearInputValue() {
        Input[] allInputs = {nameInput, priceInput, qtyInput, categoryInput};

        for (Input input : allInputs) {
            input.setInput("");
        }
    }
}
