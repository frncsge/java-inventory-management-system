package models;

import java.util.ArrayList;

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
}
