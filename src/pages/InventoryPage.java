package pages;

import components.Input;

import javax.swing.*;
import java.awt.*;

public class InventoryPage extends BasePage {
    Input nameInput, priceInput, qtyInput, categoryInput;

    @Override
    public void setUI() {
        setLayout(new BorderLayout());

        //title label
        JLabel titleLabel = new JLabel("Inventory", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        //add the panel containing all inputs
        add(inputs(), BorderLayout.WEST);
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

        //add the "add" button to the panel
        JButton button = new JButton("Add");
        button.addActionListener(e -> addItemToInventory()); //calls addItemToInventory method
        panel.add(button);

        return panel;
    }

    //extracts value/text from the inputs
    private Object[] getInputValue() {
        String name = nameInput.getInput();
        String price = priceInput.getInput();
        String qty = qtyInput.getInput().trim();
        String category = categoryInput.getInput();

        return new Object[]{name, price, qty, category};
    }

    //clears the value of all inputs
    private void clearInputValue() {
        Input[] allInputs = {nameInput, priceInput, qtyInput, categoryInput};

        for (Input input : allInputs) {
            input.setInput("");
        }
    }

    private void addItemToInventory() {
        Object[] inputValues = getInputValue();
        for (Object value : inputValues) System.out.println(value);

        clearInputValue();
    }
}
