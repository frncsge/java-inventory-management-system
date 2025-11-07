package pages;

import components.Input;

import javax.swing.*;
import java.awt.*;

public class InventoryPage extends BasePage {
    Input nameInput, priceInput, qtyInput, categoryInput;

    @Override
    public void setUI() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Inventory", SwingConstants.CENTER);

        add(label, BorderLayout.NORTH);
        add(inputs(), BorderLayout.WEST);
    }

    private JPanel inputs() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //add padding
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        nameInput = new Input("Item name");
        priceInput = new Input("Price");
        qtyInput = new Input("Quantity");
        categoryInput = new Input("Category");

        Input[] allInputs = {nameInput, priceInput, qtyInput, categoryInput};

        for (Input input : allInputs) {
            input.setMaximumSize(input.getPreferredSize()); //maintains the default size of the inputs
            panel.add(input);
        }

        return panel;
    }
}
