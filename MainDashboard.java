package com.apu.assignment.gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.DefaultCellEditor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.util.EventObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class MainDashboard extends JFrame {
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private JTable staffTable;
    private JTable carTable;
    private JTabbedPane customerTabs;

    public MainDashboard() {
        setTitle("APU Car Sales System - Managing Staff Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Create main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create sidebar
        createSidebar(mainPanel);
        
        // Create content panel with CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Add different panels for each section
        contentPanel.add(createDashboardPanel(), "DASHBOARD");
        contentPanel.add(createStaffManagementPanel(), "STAFF");
        contentPanel.add(createCustomerManagementPanel(), "CUSTOMERS");
        contentPanel.add(createCarManagementPanel(), "CARS");
        contentPanel.add(createAnalyticsPanel(), "ANALYTICS");
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
        
        // Show dashboard by default
        cardLayout.show(contentPanel, "DASHBOARD");
    }

    private void createSidebar(JPanel mainPanel) {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setPreferredSize(new Dimension(200, 0));
        sidebarPanel.setBackground(new Color(41, 128, 185));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        
        // Add logo/title
        JLabel titleLabel = new JLabel("ACSS");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(20, 0, 20, 0));
        sidebarPanel.add(titleLabel);
        
        // Add navigation buttons
        addNavButton(sidebarPanel, "Dashboard", "DASHBOARD");
        addNavButton(sidebarPanel, "Staff Management", "STAFF");
        addNavButton(sidebarPanel, "Customer Management", "CUSTOMERS");
        addNavButton(sidebarPanel, "Car Management", "CARS");
        addNavButton(sidebarPanel, "Analytics & Reports", "ANALYTICS");
        
        // Add logout button at bottom
        JButton logoutButton = new JButton("Logout");
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setMaximumSize(new Dimension(180, 40));
        logoutButton.addActionListener(e -> handleLogout());
        
        // Add glue to push logout button to bottom
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(logoutButton);
        sidebarPanel.add(Box.createVerticalStrut(20));
        
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
    }

    private void addNavButton(JPanel sidebar, String text, String cardName) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.addActionListener(e -> cardLayout.show(contentPanel, cardName));
        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(10));
    }

    private int getCurrentStaffCount() {
        DefaultTableModel model = DataManager.loadStaffData();
        return model.getRowCount();
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        
        // Add title
        JLabel titleLabel = new JLabel("Dashboard");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Create main content panel with GridBagLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Add statistics cards panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        statsPanel.setBackground(Color.WHITE);
        
        addStatCard(statsPanel, "Total Staff", String.valueOf(getCurrentStaffCount()));
        addStatCard(statsPanel, "Active Cars", "150");
        addStatCard(statsPanel, "Monthly Sales", "$45,000");
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(statsPanel, gbc);
        
        // Add sales chart
        JPanel chartPanel = createSalesChartPanel();
        chartPanel.setPreferredSize(new Dimension(800, 300));
        
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        contentPanel.add(chartPanel, gbc);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSalesChartPanel() {
        // Create the dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Add sample data (you can replace this with real data)
        dataset.addValue(45000, "Sales", "Jan");
        dataset.addValue(52000, "Sales", "Feb");
        dataset.addValue(48000, "Sales", "Mar");
        dataset.addValue(51000, "Sales", "Apr");
        dataset.addValue(55000, "Sales", "May");
        dataset.addValue(49000, "Sales", "Jun");
        
        // Create the chart
        JFreeChart chart = ChartFactory.createBarChart(
            "Monthly Sales Performance",  // Chart title
            "Month",                     // X-Axis Label
            "Sales ($)",                 // Y-Axis Label
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        // Customize the chart
        chart.setBackgroundPaint(Color.white);
        
        // Create the chart panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.WHITE);
        
        // Create a panel to hold the chart
        JPanel holder = new JPanel(new BorderLayout());
        holder.setBackground(Color.WHITE);
        holder.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        holder.add(chartPanel);
        
        return holder;
    }

    private void addStatCard(JPanel parent, String title, String value) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(Box.createVerticalStrut(20));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        card.add(Box.createVerticalStrut(20));
        
        parent.add(card);
    }

    // Update the staff count on the dashboard
    private void updateDashboardStaffCount() {
        cardLayout.show(contentPanel, "DASHBOARD");
        contentPanel.remove(contentPanel.getComponent(0));
        contentPanel.add(createDashboardPanel(), "DASHBOARD", 0);
        cardLayout.show(contentPanel, "DASHBOARD");
    }

    private JPanel createStaffManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Add title and action buttons
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Staff Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Create button panel with search
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topButtonPanel.setBackground(Color.WHITE);

        // Add search field
        JTextField searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Search staff...");
        topButtonPanel.add(searchField);

        // Add search button
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> filterTable(searchField.getText()));
        topButtonPanel.add(searchButton);

        // Add refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(46, 204, 113));
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> {
            searchField.setText("");
            staffTable.setModel(DataManager.loadStaffData());
            setupTableActionColumn(staffTable);
            updateDashboardStaffCount(); // Update dashboard after refresh
        });
        topButtonPanel.add(refreshButton);

        // Add New Staff button with icon and styling
        JButton addButton = new JButton("Add New Staff");
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.BLACK);
        addButton.setFocusPainted(false);
        
        addButton.addActionListener(e -> {
            AddStaffDialog dialog = new AddStaffDialog(this);
            dialog.setVisible(true);
            
            if (dialog.isApproved()) {
                DefaultTableModel staffModel = (DefaultTableModel) staffTable.getModel();
                String newId = DataManager.generateNewId("STAFF", staffModel);
                
                staffModel.addRow(new Object[]{
                    newId,
                    dialog.getStaffName(),
                    dialog.getRole(),
                    dialog.getEmail(),
                    dialog.getPhone(),
                    "Active",
                    ""
                });
                
                // Save updated data
                DataManager.saveStaffData(staffModel);
                updateDashboardStaffCount(); // Update dashboard after adding new staff
                
                JOptionPane.showMessageDialog(this,
                    "Staff member added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        topButtonPanel.add(addButton);
        headerPanel.add(topButtonPanel, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Create table with custom renderer for action buttons
        String[] columns = {"ID", "Name", "Role", "Email", "Phone", "Status", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only actions column is editable
            }
        };
        
        // Load staff data from file
        model = DataManager.loadStaffData();
        staffTable = new JTable(model);
        staffTable.setRowHeight(35);
        staffTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        staffTable.getTableHeader().setReorderingAllowed(false);
        
        // Set custom renderer for the actions column
        staffTable.getColumnModel().getColumn(6).setCellRenderer(new TableCellRenderer() {
            private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            private final JButton editButton = new JButton("Edit");
            private final JButton deleteButton = new JButton("Delete");
            
            {
                editButton.setBackground(new Color(52, 152, 219));
                editButton.setForeground(Color.BLACK);
                editButton.setFocusPainted(false);
                
                deleteButton.setBackground(new Color(231, 76, 60));
                deleteButton.setForeground(Color.BLACK);
                deleteButton.setFocusPainted(false);
                
                panel.add(editButton);
                panel.add(deleteButton);
            }
            
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                return panel;
            }
        });
        
        // Set custom editor for the actions column
        staffTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JTextField()) {
            private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            private final JButton editButton = new JButton("Edit");
            private final JButton deleteButton = new JButton("Delete");
            
            {
                editButton.setBackground(new Color(52, 152, 219));
                editButton.setForeground(Color.BLACK);
                editButton.setFocusPainted(false);
                
                deleteButton.setBackground(new Color(231, 76, 60));
                deleteButton.setForeground(Color.BLACK);
                deleteButton.setFocusPainted(false);
                
                editButton.addActionListener(e -> {
                    int row = staffTable.getSelectedRow();
                    if (row != -1) {
                        String id = staffTable.getValueAt(row, 0).toString();
                        String name = staffTable.getValueAt(row, 1).toString();
                        String role = staffTable.getValueAt(row, 2).toString();
                        String email = staffTable.getValueAt(row, 3).toString();
                        String phone = staffTable.getValueAt(row, 4).toString();

                        EditStaffDialog dialog = new EditStaffDialog(
                            MainDashboard.this,
                            id, name, role, email, phone
                        );
                        dialog.setVisible(true);

                        if (dialog.isApproved()) {
                            // Update the table with new values
                            staffTable.setValueAt(dialog.getStaffName(), row, 1);
                            staffTable.setValueAt(dialog.getRole(), row, 2);
                            staffTable.setValueAt(dialog.getEmail(), row, 3);
                            staffTable.setValueAt(dialog.getPhone(), row, 4);

                            // Save the changes
                            DataManager.saveStaffData((DefaultTableModel) staffTable.getModel());
                            updateDashboardStaffCount(); // Update dashboard after edit

                            // Show success message
                            JOptionPane.showMessageDialog(MainDashboard.this,
                                "Staff information updated successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    fireEditingStopped();
                });
                
                deleteButton.addActionListener(e -> {
                    int row = staffTable.getSelectedRow();
                    if (row != -1) {
                        int confirm = JOptionPane.showConfirmDialog(
                            MainDashboard.this,
                            "Are you sure you want to delete this staff member?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                        );
                        
                        if (confirm == JOptionPane.YES_OPTION) {
                            ((DefaultTableModel) staffTable.getModel()).removeRow(row);
                            
                            // Save the changes
                            DataManager.saveStaffData((DefaultTableModel) staffTable.getModel());
                            updateDashboardStaffCount(); // Update dashboard after delete
                        }
                    }
                    fireEditingStopped();
                });
                
                panel.add(editButton);
                panel.add(deleteButton);
            }
            
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                    boolean isSelected, int row, int column) {
                return panel;
            }
        });
        
        // Add search functionality
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable(searchField.getText());
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable(searchField.getText());
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable(searchField.getText());
            }
        });
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(staffTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private void filterTable(String searchText) {
        DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
        DefaultTableModel filteredModel = new DefaultTableModel(
            new String[]{"ID", "Name", "Role", "Email", "Phone", "Status", "Actions"},
            0
        );
        
        for (int i = 0; i < model.getRowCount(); i++) {
            boolean matches = false;
            for (int j = 0; j < model.getColumnCount() - 1; j++) { // Exclude actions column
                String cellValue = model.getValueAt(i, j).toString().toLowerCase();
                if (cellValue.contains(searchText.toLowerCase())) {
                    matches = true;
                    break;
                }
            }
            if (matches) {
                Object[] rowData = new Object[model.getColumnCount()];
                for (int j = 0; j < model.getColumnCount(); j++) {
                    rowData[j] = model.getValueAt(i, j);
                }
                filteredModel.addRow(rowData);
            }
        }
        
        staffTable.setModel(filteredModel);
        setupTableActionColumn();
    }

    private void setupTableActionColumn() {
        // Re-apply the custom renderer and editor for the actions column
        if (staffTable.getColumnCount() > 6) {
            staffTable.getColumnModel().getColumn(6).setCellRenderer(new TableCellRenderer() {
                private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                private final JButton editButton = new JButton("Edit");
                private final JButton deleteButton = new JButton("Delete");
                
                {
                    editButton.setBackground(new Color(52, 152, 219));
                    editButton.setForeground(Color.BLACK);
                    editButton.setFocusPainted(false);
                    
                    deleteButton.setBackground(new Color(231, 76, 60));
                    deleteButton.setForeground(Color.BLACK);
                    deleteButton.setFocusPainted(false);
                    
                    panel.add(editButton);
                    panel.add(deleteButton);
                }
                
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    return panel;
                }
            });
        }
    }

    private JPanel createCustomerManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Add header panel with title and buttons
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Customer Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Add button panel with search and new customer button
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topButtonPanel.setBackground(Color.WHITE);

        // Add search field
        JTextField searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Search customers...");
        topButtonPanel.add(searchField);

        // Create customer table
        String[] columns = {"ID", "Name", "Email", "Phone", "Registration Date", "Status", "Actions"};
        DefaultTableModel model = DataManager.loadCustomerData();
        JTable customerTable = new JTable(model);
        customerTable.setRowHeight(35);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customerTable.getTableHeader().setReorderingAllowed(false);

        // Add search button
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> {
            DefaultTableModel currentModel = (DefaultTableModel) customerTable.getModel();
            String searchText = searchField.getText().toLowerCase();
            DefaultTableModel filteredModel = new DefaultTableModel(columns, 0);
            
            for (int i = 0; i < currentModel.getRowCount(); i++) {
                boolean matches = false;
                for (int j = 0; j < currentModel.getColumnCount() - 1; j++) {
                    String cellValue = currentModel.getValueAt(i, j).toString().toLowerCase();
                    if (cellValue.contains(searchText)) {
                        matches = true;
                        break;
                    }
                }
                if (matches) {
                    Object[] rowData = new Object[currentModel.getColumnCount()];
                    for (int j = 0; j < currentModel.getColumnCount(); j++) {
                        rowData[j] = currentModel.getValueAt(i, j);
                    }
                    filteredModel.addRow(rowData);
                }
            }
            customerTable.setModel(filteredModel);
            setupTableActionColumn(customerTable);
        });
        topButtonPanel.add(searchButton);

        // Add refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(46, 204, 113));
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> {
            searchField.setText("");
            customerTable.setModel(DataManager.loadCustomerData());
            setupTableActionColumn(customerTable);
        });
        topButtonPanel.add(refreshButton);

        // Add New Customer button
        JButton addCustomerButton = new JButton("New Customer");
        addCustomerButton.setBackground(new Color(46, 204, 113));
        addCustomerButton.setForeground(Color.BLACK);
        addCustomerButton.setFocusPainted(false);
        addCustomerButton.addActionListener(e -> {
            CustomerRegistrationFrame registrationFrame = new CustomerRegistrationFrame();
            registrationFrame.setVisible(true);
        });
        topButtonPanel.add(addCustomerButton);
        
        headerPanel.add(topButtonPanel, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Set up the table with action buttons
        setupTableActionColumn(customerTable);
        
        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(customerTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private void setupTableActionColumn(JTable table) {
        if (table.getColumnCount() > 6) {
            // Create action panel with buttons
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            JButton editButton = new JButton("Edit");
            JButton deleteButton = new JButton("Delete");
            
            editButton.setBackground(new Color(52, 152, 219));
            editButton.setForeground(Color.BLACK);
            editButton.setFocusPainted(false);
            
            deleteButton.setBackground(new Color(231, 76, 60));
            deleteButton.setForeground(Color.BLACK);
            deleteButton.setFocusPainted(false);
            
            actionPanel.add(editButton);
            actionPanel.add(deleteButton);

            // Set custom renderer
            table.getColumnModel().getColumn(6).setCellRenderer((tbl, value, isSelected, hasFocus, row, column) -> {
                return actionPanel;
            });

            // Add mouse listener to handle button clicks
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int column = table.getColumnModel().getColumnIndexAtX(e.getX());
                    int row = e.getY() / table.getRowHeight();

                    if (row < table.getRowCount() && row >= 0 && column == 6) {
                        Point point = e.getPoint();
                        Rectangle cellRect = table.getCellRect(row, column, false);
                        point.translate(-cellRect.x, -cellRect.y);

                        // Calculate button positions
                        int buttonWidth = 75; // Approximate button width
                        int panelWidth = buttonWidth * 2 + 10; // Total width of both buttons + gap
                        int startX = (cellRect.width - panelWidth) / 2;

                        if (point.x >= startX && point.x <= startX + buttonWidth) {
                            // Edit button clicked
                            handleEdit(table, row);
                        } else if (point.x >= startX + buttonWidth + 10 && point.x <= startX + panelWidth) {
                            // Delete button clicked
                            handleDelete(table, row);
                        }
                    }
                }
            });

            // Make the actions column not editable
            table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JTextField()) {
                @Override
                public boolean isCellEditable(EventObject e) {
                    return false;
                }
            });
        }
    }

    private void handleEdit(JTable table, int row) {
        if (row != -1) {
            if (table == staffTable) {
                String id = table.getValueAt(row, 0).toString();
                String name = table.getValueAt(row, 1).toString();
                String role = table.getValueAt(row, 2).toString();
                String email = table.getValueAt(row, 3).toString();
                String phone = table.getValueAt(row, 4).toString();

                EditStaffDialog dialog = new EditStaffDialog(
                    this, id, name, role, email, phone
                );
                dialog.setVisible(true);

                if (dialog.isApproved()) {
                    table.setValueAt(dialog.getStaffName(), row, 1);
                    table.setValueAt(dialog.getRole(), row, 2);
                    table.setValueAt(dialog.getEmail(), row, 3);
                    table.setValueAt(dialog.getPhone(), row, 4);

                    DataManager.saveStaffData((DefaultTableModel) table.getModel());
                    updateDashboardStaffCount(); // Update dashboard after edit

                    JOptionPane.showMessageDialog(this,
                        "Staff information updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                // Customer table
                String id = table.getValueAt(row, 0).toString();
                String name = table.getValueAt(row, 1).toString();
                String email = table.getValueAt(row, 2).toString();
                String phone = table.getValueAt(row, 3).toString();
                String status = table.getValueAt(row, 5).toString();

                EditCustomerDialog dialog = new EditCustomerDialog(
                    this, id, name, email, phone, status
                );
                dialog.setVisible(true);

                if (dialog.isApproved()) {
                    table.setValueAt(dialog.getName(), row, 1);
                    table.setValueAt(dialog.getEmail(), row, 2);
                    table.setValueAt(dialog.getPhone(), row, 3);
                    table.setValueAt(dialog.getStatus(), row, 5);

                    DataManager.saveCustomerData((DefaultTableModel) table.getModel());

                    JOptionPane.showMessageDialog(this,
                        "Customer information updated successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void handleDelete(JTable table, int row) {
        if (row != -1) {
            String name = table.getValueAt(row, 1).toString();
            String type = table == staffTable ? "staff member" : "customer";
            
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete " + type + ": " + name + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                ((DefaultTableModel) table.getModel()).removeRow(row);
                
                if (table == staffTable) {
                    DataManager.saveStaffData((DefaultTableModel) table.getModel());
                    updateDashboardStaffCount(); // Update dashboard after delete
                } else {
                    DataManager.saveCustomerData((DefaultTableModel) table.getModel());
                }
            }
        }
    }

    private JPanel createCarManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Add header panel with title and buttons
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Car Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        // Create button panel with search
        JPanel topButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topButtonPanel.setBackground(Color.WHITE);

        // Create table first
        String[] columns = {"ID", "Brand", "Model", "Year", "Price", "Status", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only actions column is editable
            }
        };
        
        // Load car data from file
        model = DataManager.loadCarData();
        carTable = new JTable(model);
        carTable.setRowHeight(35);
        carTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carTable.getTableHeader().setReorderingAllowed(false);

        // Add search field
        JTextField searchField = new JTextField(20);
        searchField.putClientProperty("JTextField.placeholderText", "Search cars...");
        topButtonPanel.add(searchField);

        // Add search button
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(52, 152, 219));
        searchButton.setForeground(Color.BLACK);
        searchButton.setFocusPainted(false);
        searchButton.addActionListener(e -> {
            DefaultTableModel currentModel = (DefaultTableModel) carTable.getModel();
            String searchText = searchField.getText().toLowerCase();
            DefaultTableModel filteredModel = new DefaultTableModel(
                columns,
                0
            );
            
            for (int i = 0; i < currentModel.getRowCount(); i++) {
                boolean matches = false;
                for (int j = 0; j < currentModel.getColumnCount() - 1; j++) {
                    String cellValue = currentModel.getValueAt(i, j).toString().toLowerCase();
                    if (cellValue.contains(searchText)) {
                        matches = true;
                        break;
                    }
                }
                if (matches) {
                    Object[] rowData = new Object[currentModel.getColumnCount()];
                    for (int j = 0; j < currentModel.getColumnCount(); j++) {
                        rowData[j] = currentModel.getValueAt(i, j);
                    }
                    filteredModel.addRow(rowData);
                }
            }
            carTable.setModel(filteredModel);
            
            // Reapply the custom renderer and editor for the actions column
            if (carTable.getColumnCount() > 6) {
                TableCellRenderer actionRenderer = new TableCellRenderer() {
                    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                    private final JButton editButton = new JButton("Edit");
                    private final JButton deleteButton = new JButton("Delete");
                    
                    {
                        editButton.setBackground(new Color(52, 152, 219));
                        editButton.setForeground(Color.BLACK);
                        editButton.setFocusPainted(false);
                        
                        deleteButton.setBackground(new Color(231, 76, 60));
                        deleteButton.setForeground(Color.BLACK);
                        deleteButton.setFocusPainted(false);
                        
                        panel.add(editButton);
                        panel.add(deleteButton);
                    }
                    
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value,
                            boolean isSelected, boolean hasFocus, int row, int column) {
                        return panel;
                    }
                };
                carTable.getColumnModel().getColumn(6).setCellRenderer(actionRenderer);
                
                carTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JTextField()) {
                    private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
                    private final JButton editButton = new JButton("Edit");
                    private final JButton deleteButton = new JButton("Delete");
                    
                    {
                        editButton.setBackground(new Color(52, 152, 219));
                        editButton.setForeground(Color.BLACK);
                        editButton.setFocusPainted(false);
                        
                        deleteButton.setBackground(new Color(231, 76, 60));
                        deleteButton.setForeground(Color.BLACK);
                        deleteButton.setFocusPainted(false);
                        
                        editButton.addActionListener(e -> {
                            int row = carTable.getSelectedRow();
                            if (row != -1) {
                                String id = carTable.getValueAt(row, 0).toString();
                                String brand = carTable.getValueAt(row, 1).toString();
                                String model = carTable.getValueAt(row, 2).toString();
                                String year = carTable.getValueAt(row, 3).toString();
                                String price = carTable.getValueAt(row, 4).toString();
                                String status = carTable.getValueAt(row, 5).toString();

                                EditCarDialog dialog = new EditCarDialog(
                                    MainDashboard.this,
                                    id, brand, model, year, price, status
                                );
                                dialog.setVisible(true);

                                if (dialog.isApproved()) {
                                    // Update the table with new values
                                    carTable.setValueAt(dialog.getBrand(), row, 1);
                                    carTable.setValueAt(dialog.getModel(), row, 2);
                                    carTable.setValueAt(dialog.getYear(), row, 3);
                                    carTable.setValueAt("$" + dialog.getPrice(), row, 4);
                                    carTable.setValueAt(dialog.getStatus(), row, 5);

                                    // Save the changes
                                    DataManager.saveCarData((DefaultTableModel) carTable.getModel());

                                    // Show success message
                                    JOptionPane.showMessageDialog(MainDashboard.this,
                                        "Car information updated successfully!",
                                        "Success",
                                        JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                            fireEditingStopped();
                        });
                        
                        deleteButton.addActionListener(e -> {
                            int row = carTable.getSelectedRow();
                            if (row != -1) {
                                String brand = carTable.getValueAt(row, 1).toString();
                                String model = carTable.getValueAt(row, 2).toString();
                                int confirm = JOptionPane.showConfirmDialog(
                                    MainDashboard.this,
                                    "Are you sure you want to delete car: " + brand + " " + model + "?",
                                    "Confirm Delete",
                                    JOptionPane.YES_NO_OPTION
                                );
                                
                                if (confirm == JOptionPane.YES_OPTION) {
                                    ((DefaultTableModel) carTable.getModel()).removeRow(row);
                                    
                                    // Save the changes
                                    DataManager.saveCarData((DefaultTableModel) carTable.getModel());
                                }
                            }
                            fireEditingStopped();
                        });
                        
                        panel.add(editButton);
                        panel.add(deleteButton);
                    }
                    
                    @Override
                    public Component getTableCellEditorComponent(JTable table, Object value,
                            boolean isSelected, int row, int column) {
                        return panel;
                    }
                });
            }
        });
        topButtonPanel.add(searchButton);

        // Add refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBackground(new Color(46, 204, 113));
        refreshButton.setForeground(Color.BLACK);
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(e -> {
            searchField.setText("");
            carTable.setModel(DataManager.loadCarData());
            setupTableActionColumn(carTable);
        });
        topButtonPanel.add(refreshButton);

        // Add New Car button
        JButton addButton = new JButton("Add New Car");
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.BLACK);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> {
            AddCarDialog dialog = new AddCarDialog(this);
            dialog.setVisible(true);
            
            if (dialog.isApproved()) {
                DefaultTableModel carModel = (DefaultTableModel) carTable.getModel();
                String newId = DataManager.generateNewId("CAR", carModel);
                
                carModel.addRow(new Object[]{
                    newId,
                    dialog.getBrand(),
                    dialog.getModel(),
                    dialog.getYear(),
                    "$" + dialog.getPrice(),
                    dialog.getStatus(),
                    ""
                });
                
                // Save updated data
                DataManager.saveCarData(carModel);
                
                JOptionPane.showMessageDialog(this,
                    "Car added successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        topButtonPanel.add(addButton);
        
        headerPanel.add(topButtonPanel, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Set custom renderer for the actions column
        carTable.getColumnModel().getColumn(6).setCellRenderer(new TableCellRenderer() {
            private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            private final JButton editButton = new JButton("Edit");
            private final JButton deleteButton = new JButton("Delete");
            
            {
                editButton.setBackground(new Color(52, 152, 219));
                editButton.setForeground(Color.BLACK);
                editButton.setFocusPainted(false);
                
                deleteButton.setBackground(new Color(231, 76, 60));
                deleteButton.setForeground(Color.BLACK);
                deleteButton.setFocusPainted(false);
                
                panel.add(editButton);
                panel.add(deleteButton);
            }
            
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                return panel;
            }
        });
        
        // Set custom editor for the actions column
        carTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JTextField()) {
            private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
            private final JButton editButton = new JButton("Edit");
            private final JButton deleteButton = new JButton("Delete");
            
            {
                editButton.setBackground(new Color(52, 152, 219));
                editButton.setForeground(Color.BLACK);
                editButton.setFocusPainted(false);
                
                deleteButton.setBackground(new Color(231, 76, 60));
                deleteButton.setForeground(Color.BLACK);
                deleteButton.setFocusPainted(false);
                
                editButton.addActionListener(e -> {
                    int row = carTable.getSelectedRow();
                    if (row != -1) {
                        String id = carTable.getValueAt(row, 0).toString();
                        String brand = carTable.getValueAt(row, 1).toString();
                        String model = carTable.getValueAt(row, 2).toString();
                        String year = carTable.getValueAt(row, 3).toString();
                        String price = carTable.getValueAt(row, 4).toString();
                        String status = carTable.getValueAt(row, 5).toString();

                        EditCarDialog dialog = new EditCarDialog(
                            MainDashboard.this,
                            id, brand, model, year, price, status
                        );
                        dialog.setVisible(true);

                        if (dialog.isApproved()) {
                            // Update the table with new values
                            carTable.setValueAt(dialog.getBrand(), row, 1);
                            carTable.setValueAt(dialog.getModel(), row, 2);
                            carTable.setValueAt(dialog.getYear(), row, 3);
                            carTable.setValueAt("$" + dialog.getPrice(), row, 4);
                            carTable.setValueAt(dialog.getStatus(), row, 5);

                            // Save the changes
                            DataManager.saveCarData((DefaultTableModel) carTable.getModel());

                            // Show success message
                            JOptionPane.showMessageDialog(MainDashboard.this,
                                "Car information updated successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    fireEditingStopped();
                });
                
                deleteButton.addActionListener(e -> {
                    int row = carTable.getSelectedRow();
                    if (row != -1) {
                        String brand = carTable.getValueAt(row, 1).toString();
                        String model = carTable.getValueAt(row, 2).toString();
                        int confirm = JOptionPane.showConfirmDialog(
                            MainDashboard.this,
                            "Are you sure you want to delete car: " + brand + " " + model + "?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION
                        );
                        
                        if (confirm == JOptionPane.YES_OPTION) {
                            ((DefaultTableModel) carTable.getModel()).removeRow(row);
                            
                            // Save the changes
                            DataManager.saveCarData((DefaultTableModel) carTable.getModel());
                        }
                    }
                    fireEditingStopped();
                });
                
                panel.add(editButton);
                panel.add(deleteButton);
            }
            
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                    boolean isSelected, int row, int column) {
                return panel;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(carTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createAnalyticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Analytics & Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        contentPanel.setBackground(Color.WHITE);
        
        // Add report sections
        addReportSection(contentPanel, "Sales Report");
        addReportSection(contentPanel, "Inventory Report");
        addReportSection(contentPanel, "Customer Feedback");
        addReportSection(contentPanel, "Staff Performance");
        
        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }

    private void addReportSection(JPanel parent, String title) {
        JPanel section = new JPanel();
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createTitledBorder(title));
        
        JButton generateButton = new JButton("Generate Report");
        section.add(generateButton);
        
        parent.add(section);
    }

    private void handleLogout() {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            this.dispose();
            new LoginFrame().setVisible(true);
        }
    }
} 