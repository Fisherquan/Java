package com.apu.assignment.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class EditCustomerDialog extends JDialog {
    private boolean approved = false;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JComboBox<String> statusCombo;

    public EditCustomerDialog(JFrame parent, String id, String name, String email, String phone, String status) {
        super(parent, "Edit Customer - " + id, true);
        setLayout(new BorderLayout(10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add form fields
        nameField = new JTextField(name);
        emailField = new JTextField(email);
        phoneField = new JTextField(phone);
        statusCombo = new JComboBox<>(new String[]{"Active", "Inactive"});
        statusCombo.setSelectedItem(status);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Status:"));
        formPanel.add(statusCombo);

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.setBackground(new Color(46, 204, 113));
        saveButton.setForeground(Color.BLACK);
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.BLACK);

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add action listeners
        saveButton.addActionListener(e -> {
            if (validateInputs()) {
                approved = true;
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        // Add panels to dialog
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private boolean validateInputs() {
        if (nameField.getText().trim().isEmpty() ||
            emailField.getText().trim().isEmpty() ||
            phoneField.getText().trim().isEmpty()) {
            
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

    public boolean isApproved() {
        return approved;
    }

    public String getName() {
        return nameField.getText().trim();
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPhone() {
        return phoneField.getText().trim();
    }

    public String getStatus() {
        return (String) statusCombo.getSelectedItem();
    }
} 