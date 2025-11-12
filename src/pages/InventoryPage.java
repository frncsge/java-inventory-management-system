package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    // Add item to inventory
    public void addItem(Item item) {
        items.add(item);
    }

    // Get all items
    public ArrayList<Item> getItems() {
        return new ArrayList<>(items); // Return copy to maintain encapsulation
    }

    // Remove item from inventory
    public boolean removeItem(Item item) {
        return items.remove(item);
    }

    // Get total number of items
    public int getTotalItems() {
        return items.size();
    }

    // Get count of low stock items (quantity <= 5)
    public int getLowStockCount() {
        return (int) items.stream()
                .filter(item -> item.getQuantity() <= 5)
                .count();
    }

    // Get total stock value (sum of price * quantity for all items)
    public BigDecimal getTotalStockValue() {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Search items by name
    public List<Item> searchItemsByName(String name) {
        return items.stream()
                .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }

    // Sorting methods (as used in your InventoryPage)
    public void sortByName(String order) {
        items.sort((item1, item2) -> {
            int comparison = item1.getName().compareToIgnoreCase(item2.getName());
            return "asc".equals(order) ? comparison : -comparison;
        });
    }

    public void sortByPrice(String order) {
        items.sort((item1, item2) -> {
            int comparison = item1.getPrice().compareTo(item2.getPrice());
            return "asc".equals(order) ? comparison : -comparison;
        });
    }

    public void sortByQty(String order) {
        items.sort((item1, item2) -> {
            int comparison = Integer.compare(item1.getQuantity(), item2.getQuantity());
            return "asc".equals(order) ? comparison : -comparison;
        });
    }

    public void sortByCategory(String order) {
        items.sort((item1, item2) -> {
            int comparison = item1.getCategory().compareToIgnoreCase(item2.getCategory());
            return "asc".equals(order) ? comparison : -comparison;
        });
    }

    public void sortByDateAdded(String order) {
        items.sort((item1, item2) -> {
            int comparison = item1.getDateAdded().compareTo(item2.getDateAdded());
            return "asc".equals(order) ? comparison : -comparison;
        });
    }

    // Clear all items from inventory
    public void clearInventory() {
        items.clear();
    }

    // Update an existing item
    public boolean updateItem(Item oldItem, Item newItem) {
        int index = items.indexOf(oldItem);
        if (index != -1) {
            items.set(index, newItem);
            return true;
        }
        return false;
    }

    // Find item by name (exact match)
    public Item findItemByName(String name) {
        return items.stream()
                .filter(item -> item.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    // Get items by category
    public List<Item> getItemsByCategory(String category) {
        return items.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase(category))
                .toList();
    }
}
