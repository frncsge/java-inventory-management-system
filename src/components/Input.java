package components;

import javax.swing.*;

public class Input extends JPanel {

    private final JTextField textField;

    public Input(String inputLabel) {
        //align the label and text field vertically
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel(inputLabel);
        label.setAlignmentX(LEFT_ALIGNMENT); //put label on left side

        textField = new JTextField(15);
        textField.setAlignmentX(LEFT_ALIGNMENT); //also put text field on left side so they align

        //add to the input panel
        add(label);
        add(textField);
    }

    public String getInput() {
        return textField.getText().trim();
    }

    public void setInput(String input) {
        textField.setText(input);
    }
}
