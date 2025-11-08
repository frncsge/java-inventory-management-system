package models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Item {

    private String name, category;
    private BigDecimal price;
    private int qty;
    private LocalDate date;

    public Item(String name, BigDecimal price, int qty, String category) {
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.category = category;
        this.date = LocalDate.now();
    }

    //getters
    public LocalDate getDate() { return this.date; }
    public String getName() { return this.name; }
    public BigDecimal getPrice() { return this.price; }
    public int getQty() { return this.qty; }
    public String getCategory() { return this.category; }

    //setters
    public void setName(String name) { this.name = name; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setQty(int qty) { this.qty = qty; }
    public void setCategory(String category) { this.category = category; }
}
