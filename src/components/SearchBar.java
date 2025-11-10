package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SearchBar extends JPanel {
    private JTextField searchField;
    private Button searchButton;
    private Button showAllButton;
    private JLabel label;

    public SearchBar(ActionListener onSearch, ActionListener showAll) {

        //initialize searchField and searchButton
        label = new JLabel("Search Item by Name");
        searchField = new JTextField(30);
        searchButton = new Button("Search", onSearch);
        showAllButton = new Button("Show All Items", showAll);

        showAllButton.setForeground(Color.GREEN);

        //add to SearchBar
        add(label);
        add(searchField);
        add(searchButton);
        add(showAllButton);
    }

    public String getQuery() { return searchField.getText().trim(); }
    public void clear() { searchField.setText(""); }
}
