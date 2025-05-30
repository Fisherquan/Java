package com.apu.assignment.gui;

import java.io.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class DataManager {
    private static final String DATA_DIR = "src/main/resources/data/";
    private static final String STAFF_FILE = DATA_DIR + "staff.txt";
    private static final String CUSTOMERS_FILE = DATA_DIR + "customers.txt";
    private static final String CARS_FILE = DATA_DIR + "cars.txt";

    // Load staff data
    public static DefaultTableModel loadStaffData() {
        String[] columns = {"ID", "Name", "Role", "Email", "Phone", "Status", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(STAFF_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    model.addRow(new Object[]{
                        data[0], // ID
                        data[1], // Name
                        data[2], // Role
                        data[3], // Email
                        data[4], // Phone
                        data[5], // Status
                        ""      // Actions column (empty)
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    // Save staff data
    public static void saveStaffData(DefaultTableModel model) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STAFF_FILE))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                writer.println(String.format("%s,%s,%s,%s,%s,%s",
                    model.getValueAt(i, 0), // ID
                    model.getValueAt(i, 1), // Name
                    model.getValueAt(i, 2), // Role
                    model.getValueAt(i, 3), // Email
                    model.getValueAt(i, 4), // Phone
                    model.getValueAt(i, 5)  // Status
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load customer data
    public static DefaultTableModel loadCustomerData() {
        String[] columns = {"ID", "Name", "Email", "Phone", "Registration Date", "Status", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    model.addRow(new Object[]{
                        data[0], // ID
                        data[1], // Name
                        data[2], // Email
                        data[3], // Phone
                        data[4], // Registration Date
                        data[5], // Status
                        ""      // Actions column (empty)
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    // Save customer data
    public static void saveCustomerData(DefaultTableModel model) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CUSTOMERS_FILE))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                writer.println(String.format("%s,%s,%s,%s,%s,%s",
                    model.getValueAt(i, 0), // ID
                    model.getValueAt(i, 1), // Name
                    model.getValueAt(i, 2), // Email
                    model.getValueAt(i, 3), // Phone
                    model.getValueAt(i, 4), // Registration Date
                    model.getValueAt(i, 5)  // Status
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load car data
    public static DefaultTableModel loadCarData() {
        String[] columns = {"ID", "Brand", "Model", "Year", "Price", "Status", "Actions"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(CARS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 6) {
                    model.addRow(new Object[]{
                        data[0], // ID
                        data[1], // Brand
                        data[2], // Model
                        data[3], // Year
                        "$" + data[4], // Price
                        data[5], // Status
                        ""      // Actions column (empty)
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    // Save car data
    public static void saveCarData(DefaultTableModel model) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CARS_FILE))) {
            for (int i = 0; i < model.getRowCount(); i++) {
                String price = model.getValueAt(i, 4).toString().replace("$", "").replace(",", "");
                writer.println(String.format("%s,%s,%s,%s,%s,%s",
                    model.getValueAt(i, 0), // ID
                    model.getValueAt(i, 1), // Brand
                    model.getValueAt(i, 2), // Model
                    model.getValueAt(i, 3), // Year
                    price, // Price (without $ and commas)
                    model.getValueAt(i, 5)  // Status
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generate new ID for each type
    public static String generateNewId(String type, DefaultTableModel model) {
        int maxNumber = 0;
        String prefix = "";
        
        switch (type.toUpperCase()) {
            case "STAFF":
                prefix = "MS";
                break;
            case "CUSTOMER":
                prefix = "C";
                break;
            case "CAR":
                prefix = "CAR";
                break;
        }
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String id = model.getValueAt(i, 0).toString();
            try {
                int num = Integer.parseInt(id.replaceAll("[^0-9]", ""));
                maxNumber = Math.max(maxNumber, num);
            } catch (NumberFormatException e) {
                // Skip if ID format is invalid
            }
        }
        
        return prefix + String.format("%03d", maxNumber + 1);
    }
} 