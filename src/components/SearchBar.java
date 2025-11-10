package components;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SearchBar extends JPanel {
    private JTextField searchField;
    private Button searchButton;

    public SearchBar(ActionListener onSearch) {

        //initialize searchField and searchButton
        searchField = new JTextField();
        searchButton = new Button("Search", onSearch);

        //add to SearchBar
        add(searchField);
        add(searchButton);
    }

    public String getQuery() { return searchField.getText().trim(); }
    public void clear() { searchField.setText(""); }
}
