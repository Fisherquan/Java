package com.apu.assignment.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomerRegistrationFrame extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextArea addressArea;

    public CustomerRegistrationFrame() {
        setTitle("Register New Customer");
        setSize(400, 400);  // Increased height for address field
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));  // Added one more row for address

        // Add form fields
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressArea = new JTextArea(3, 20);  // 3 rows for address
        addressArea.setLineWrap(true);
        addressArea.setWrapStyleWord(true);
        JScrollPane addressScroll = new JScrollPane(addressArea);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Address:"));
        formPanel.add(addressScroll);

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton registerButton = new JButton("Register");
        JButton cancelButton = new JButton("Cancel");

        registerButton.setBackground(new Color(46, 204, 113));
        registerButton.setForeground(Color.BLACK);
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.BLACK);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        // Add action listeners
        registerButton.addActionListener(e -> {
            if (validateInputs()) {
                // Get current customer data
                DefaultTableModel model = DataManager.loadCustomerData();
                
                // Generate new ID
                String newId = DataManager.generateNewId("CUSTOMER", model);
                
                // Get current date
                String registrationDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
                
                // Add new customer
                model.addRow(new Object[]{
                    newId,
                    nameField.getText().trim(),
                    emailField.getText().trim(),
                    phoneField.getText().trim(),
                    registrationDate,
                    "Active",
                    ""
                });
                
                // Save updated data
                DataManager.saveCustomerData(model);
                
                // Show success message
                JOptionPane.showMessageDialog(this,
                    "Customer registered successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        // Add panels to main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);
    }

    private boolean validateInputs() {
        if (nameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() ||
            phoneField.getText().trim().isEmpty() ||
            addressArea.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate email
        String email = emailField.getText().trim();
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid email address",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate phone
        String phone = phoneField.getText().trim();
        if (!phone.matches("^\\+?[0-9()-]+$")) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid phone number",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
} 