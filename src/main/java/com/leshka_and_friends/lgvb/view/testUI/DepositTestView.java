package com.leshka_and_friends.lgvb.view.testUI;

import javax.swing.*;
import java.awt.*;

public class DepositTestView extends JFrame {

    // Declare the components as class fields so they can be accessed elsewhere if needed
    public double depositAmount;

    public JTextField amountField;
    public JButton depositButton;
    public JLabel statusLabel; // A label to show the result of the "deposit"

    public DepositTestView() {
        // --- 1. Basic Frame Setup ---
        super("Deposit Functionality Test"); // Set the window title
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(300, 150); // Set a reasonable size for the window
        setLocationRelativeTo(null); // Center the window on the screen

        // --- 2. Create Components ---
        amountField = new JTextField(10); // Text field for the amount
        depositButton = new JButton("Deposit"); // Button to trigger the action
        statusLabel = new JLabel("Enter amount and click Deposit"); // Initial status message

        // --- 3. Set up the Layout ---
        // Use a simple layout manager like FlowLayout or GridLayout.
        // GridLayout is good for organizing components in rows and columns.
        setLayout(new GridLayout(3, 1, 10, 10)); // 3 rows, 1 column, with 10-pixel gaps

        // --- 4. Add Components to the Frame ---

        // Row 1: Label and Text Field (use a JPanel for better grouping)
        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        amountPanel.add(new JLabel("Amount:"));
        amountPanel.add(amountField);
        add(amountPanel);

        // Row 2: Deposit Button
        add(depositButton);

        // Row 3: Status Label
        // Center the text within the label
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(statusLabel);


        // --- 5. Add Functionality (Action Listener) ---
        depositButton.addActionListener(e -> {
            try {
                // Get the text from the amount field
                String amountText = amountField.getText();
                // Attempt to parse it as a double
                depositAmount = Double.parseDouble(amountText);


            } catch (NumberFormatException ex) {
                // Handle cases where the input is not a valid number
                statusLabel.setText("❌ Error: Invalid number format.");
                statusLabel.setForeground(Color.RED);
            }
        });

        // --- 6. Make the Frame Visible ---
        setVisible(true);
    }

    // A main method to run the test frame
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure thread safety
        SwingUtilities.invokeLater(() -> new DepositTestView());
    }
}