package com.apu.assignment.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class EditStaffDialog extends JDialog {
    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JComboBox<String> roleComboBox;
    private JPasswordField passwordField;
    private boolean approved = false;

    public EditStaffDialog(JFrame parent, String id, String name, String role, String email, String phone) {
        super(parent, "Edit Staff", true);
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Staff ID (readonly)
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Staff ID:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        idField = new JTextField(id);
        idField.setEditable(false);
        idField.setBackground(new Color(240, 240, 240));
        formPanel.add(idField, gbc);

        // Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nameField = new JTextField(name);
        formPanel.add(nameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        emailField = new JTextField(email);
        formPanel.add(emailField, gbc);

        // Phone
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Phone:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        phoneField = new JTextField(phone);
        formPanel.add(phoneField, gbc);

        // Role
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("Role:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        String[] roles = {"Managing Staff", "Salesman"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setSelectedItem(role);
        formPanel.add(roleComboBox, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0.0;
        formPanel.add(new JLabel("New Password:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        passwordField = new JPasswordField();
        formPanel.add(passwordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> {
            if (validateForm()) {
                approved = true;
                dispose();
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        // Style buttons
        saveButton.setBackground(new Color(46, 204, 113));
        saveButton.setForeground(Color.BLACK);
        saveButton.setFocusPainted(false);

        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty()) {
            showError("Name is required");
            return false;
        }
        if (emailField.getText().trim().isEmpty()) {
            showError("Email is required");
            return false;
        }
        if (!emailField.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showError("Invalid email format");
            return false;
        }
        if (phoneField.getText().trim().isEmpty()) {
            showError("Phone is required");
            return false;
        }
        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Validation Error",
            JOptionPane.ERROR_MESSAGE);
    }

    public boolean isApproved() {
        return approved;
    }

    public String getStaffId() {
        return idField.getText().trim();
    }

    public String getStaffName() {
        return nameField.getText().trim();
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPhone() {
        return phoneField.getText().trim();
    }

    public String getRole() {
        return (String) roleComboBox.getSelectedItem();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
} 