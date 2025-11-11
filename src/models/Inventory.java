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

    public void sortByName(String order) {
        switch (order) {
            case "asc" -> items.sort(Comparator.comparing(item -> item.getName(), String.CASE_INSENSITIVE_ORDER));
            case "desc" -> items.sort(Comparator.comparing((Item item) -> item.getName(), String.CASE_INSENSITIVE_ORDER).reversed());
        }
    }

    public void sortByPrice(String order) {
        switch (order) {
            case "asc" -> items.sort(Comparator.comparing((Item item) -> item.getPrice()));
            case "desc" -> items.sort(Comparator.comparing((Item item) -> item.getPrice()).reversed());
        }
    }

    public void sortByQty(String order) {
        switch (order) {
            case "asc" -> items.sort(Comparator.comparing((Item item) -> item.getQty()));
            case "desc" -> items.sort(Comparator.comparing((Item item) -> item.getQty()).reversed());
        }
    }

    public void sortByCategory(String order) {
        switch (order) {
            case "asc" -> items.sort(Comparator.comparing(item -> item.getCategory(), String.CASE_INSENSITIVE_ORDER));
            case "desc" -> items.sort(Comparator.comparing((Item item) -> item.getCategory(), String.CASE_INSENSITIVE_ORDER).reversed());
         }
    }

    public void sortByDateAdded(String order) {
        switch (order) {
            case "asc" -> items.sort(Comparator.comparing((Item item )-> item.getDateTime()));
            case "desc" -> items.sort(Comparator.comparing((Item item )-> item.getDateTime()).reversed());
        }
    }
}
