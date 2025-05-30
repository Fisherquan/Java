package com.apu.assignment.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.prefs.Preferences;

public class LoginFrame extends JFrame {
    private JTextField staffIdField;
    private JPasswordField passwordField;
    private JCheckBox rememberMeCheckbox;
    private Preferences prefs;
    private static final String PREF_STAFF_ID = "staffId";
    private static final String PREF_REMEMBER = "remember";

    public LoginFrame() {
        prefs = Preferences.userNodeForPackage(LoginFrame.class);
        
        setTitle("APU Car Sales System - Staff Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(41, 128, 185);
                Color color2 = new Color(142, 68, 173);
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(null);

        // Login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(50, 50, 300, 200);
        loginPanel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Managing Staff Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0, 20, 300, 25);
        loginPanel.add(titleLabel);

        // Staff ID field
        JLabel staffIdLabel = new JLabel("Staff ID:");
        staffIdLabel.setBounds(30, 60, 80, 25);
        loginPanel.add(staffIdLabel);

        staffIdField = new JTextField();
        staffIdField.setBounds(110, 60, 160, 25);
        loginPanel.add(staffIdField);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(30, 90, 80, 25);
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(110, 90, 160, 25);
        loginPanel.add(passwordField);

        // Remember Me checkbox
        rememberMeCheckbox = new JCheckBox("Remember Me");
        rememberMeCheckbox.setBounds(110, 120, 160, 20);
        rememberMeCheckbox.setBackground(Color.WHITE);
        loginPanel.add(rememberMeCheckbox);

        // Login button with green color
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(110, 145, 160, 35);
        loginButton.setBackground(new Color(46, 204, 113)); // Green color
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setOpaque(true);
        
        // Add hover effect
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(39, 174, 96)); // Darker green on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(46, 204, 113)); // Original green
            }
        });
        
        loginButton.addActionListener(e -> handleLogin());
        loginPanel.add(loginButton);

        // Forgot Password link
        JLabel forgotPasswordLink = new JLabel("Forgot Password?");
        forgotPasswordLink.setBounds(110, 185, 160, 20);
        forgotPasswordLink.setHorizontalAlignment(SwingConstants.CENTER);
        forgotPasswordLink.setForeground(new Color(41, 128, 185));
        forgotPasswordLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotPasswordLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleForgotPassword();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                forgotPasswordLink.setForeground(new Color(52, 152, 219));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                forgotPasswordLink.setForeground(new Color(41, 128, 185));
            }
        });
        loginPanel.add(forgotPasswordLink);

        mainPanel.add(loginPanel);
        add(mainPanel);

        // Add key listener for Enter key
        getRootPane().setDefaultButton(loginButton);

        // Load remembered staff ID if exists
        loadRememberedStaffId();
    }

    private void loadRememberedStaffId() {
        boolean remembered = prefs.getBoolean(PREF_REMEMBER, false);
        if (remembered) {
            String savedStaffId = prefs.get(PREF_STAFF_ID, "");
            staffIdField.setText(savedStaffId);
            rememberMeCheckbox.setSelected(true);
        }
    }

    private void saveRememberedStaffId() {
        if (rememberMeCheckbox.isSelected()) {
            prefs.put(PREF_STAFF_ID, staffIdField.getText());
            prefs.putBoolean(PREF_REMEMBER, true);
        } else {
            prefs.remove(PREF_STAFF_ID);
            prefs.putBoolean(PREF_REMEMBER, false);
        }
    }

    private void handleForgotPassword() {
        String staffId = JOptionPane.showInputDialog(this,
            "Enter your Staff ID to reset password:",
            "Password Reset",
            JOptionPane.QUESTION_MESSAGE);
            
        if (staffId != null && !staffId.trim().isEmpty()) {
            // TODO: Implement actual password reset logic
            JOptionPane.showMessageDialog(this,
                "Password reset instructions have been sent to your registered email.",
                "Password Reset",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void handleLogin() {
        String staffId = staffIdField.getText();
        String password = new String(passwordField.getPassword());

        if (staffId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both Staff ID and Password",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // TODO: Add authentication logic here
        if (authenticateStaff(staffId, password)) {
            saveRememberedStaffId();
            openMainDashboard();
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid Staff ID or Password",
                "Login Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean authenticateStaff(String staffId, String password) {
        // TODO: Implement actual authentication
        // For testing purposes, using a dummy check
        return staffId.equals("admin") && password.equals("admin123");
    }

    private void openMainDashboard() {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            MainDashboard dashboard = new MainDashboard();
            dashboard.setVisible(true);
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
} 