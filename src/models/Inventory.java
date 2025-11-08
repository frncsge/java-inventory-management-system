package models;

import java.util.ArrayList;
import java.util.Comparator;

public class Inventory {
    private ArrayList<Item> items = new ArrayList<>();

    //setter
    public void addItem(Item item) {
        items.add(item);
    }

    //getter
    public ArrayList<Item> getItems() {
        return items;
    }

    public void sortByName() {
        items.sort(Comparator.comparing(item -> item.getName(), String.CASE_INSENSITIVE_ORDER));
    }

    public void sortByPrice() {
        items.sort(Comparator.comparing((Item item) -> item.getPrice()).reversed());
    }

    public void sortByQty() {
        items.sort(Comparator.comparing((Item item) -> item.getQty()).reversed());
    }

    public void sortByCategory() {
        items.sort(Comparator.comparing(item -> item.getCategory(), String.CASE_INSENSITIVE_ORDER));
    }

    public void sortByDateAdded() {
        items.sort(Comparator.comparing((Item item )-> item.getDateTime()).reversed());
    }
}
