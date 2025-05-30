package com.apu.assignment.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddCarDialog extends JDialog {
    private boolean approved = false;
    private JTextField brandField;
    private JTextField modelField;
    private JTextField yearField;
    private JTextField priceField;
    private JComboBox<String> statusCombo;

    public AddCarDialog(JFrame parent) {
        super(parent, "Add New Car", true);
        setLayout(new BorderLayout(10, 10));
        setSize(400, 300);
        setLocationRelativeTo(parent);

        // Create form panel
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Add form fields
        brandField = new JTextField();
        modelField = new JTextField();
        yearField = new JTextField();
        priceField = new JTextField();
        statusCombo = new JComboBox<>(new String[]{"Available", "Sold", "Reserved"});

        formPanel.add(new JLabel("Brand:"));
        formPanel.add(brandField);
        formPanel.add(new JLabel("Model:"));
        formPanel.add(modelField);
        formPanel.add(new JLabel("Year:"));
        formPanel.add(yearField);
        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);
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
        if (brandField.getText().trim().isEmpty() ||
            modelField.getText().trim().isEmpty() ||
            yearField.getText().trim().isEmpty() ||
            priceField.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this,
                "Please fill in all fields",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate year
        try {
            int year = Integer.parseInt(yearField.getText().trim());
            if (year < 1900 || year > 2100) {
                JOptionPane.showMessageDialog(this,
                    "Please enter a valid year (1900-2100)",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid year",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validate price
        try {
            double price = Double.parseDouble(priceField.getText().trim());
            if (price <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Please enter a valid price (greater than 0)",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid price",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getBrand() {
        return brandField.getText().trim();
    }

    public String getModel() {
        return modelField.getText().trim();
    }

    public String getYear() {
        return yearField.getText().trim();
    }

    public String getPrice() {
        return priceField.getText().trim();
    }

    public String getStatus() {
        return (String) statusCombo.getSelectedItem();
    }
} 