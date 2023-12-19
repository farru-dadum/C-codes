import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class EmployeeManagementGUI extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    private JTextArea resultArea;

    public EmployeeManagementGUI() {
        setTitle("Employee Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        JButton displayAllButton = new JButton("Display All Employees (Descending Order of Salary)");
        JButton displayByIdButton = new JButton("Display Employee by ID");
        JButton displayByNationalityAndDesignationButton = new JButton("Display Employees by Nationality and Designation");
        JButton deleteByYearButton = new JButton("Delete Employees Joined Before Year");

        setLayout(new FlowLayout());
        add(displayAllButton);
        add(displayByIdButton);
        add(displayByNationalityAndDesignationButton);
        add(deleteByYearButton);
        add(new JScrollPane(resultArea));

        displayAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllEmployees();
            }
        });

        displayByIdButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String empId = JOptionPane.showInputDialog("Enter Employee ID:");
                displayEmployeeById(empId);
            }
        });

        displayByNationalityAndDesignationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nationality = JOptionPane.showInputDialog("Enter Nationality:");
                String designation = JOptionPane.showInputDialog("Enter Designation:");
                displayEmployeesByNationalityAndDesignation(nationality, designation);
            }
        });

        deleteByYearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String year = JOptionPane.showInputDialog("Enter Year:");
                deleteEmployeesByYear(year);
            }
        });
    }

    private void displayAllEmployees() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM employees ORDER BY salary DESC";
            ResultSet resultSet = statement.executeQuery(query);
            displayResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void displayEmployeeById(String empId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employees WHERE empid = ?");
            preparedStatement.setString(1, empId);
            ResultSet resultSet = preparedStatement.executeQuery();
            displayResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void displayEmployeesByNationalityAndDesignation(String nationality, String designation) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM employees WHERE nationality = ? AND designation = ?");
            preparedStatement.setString(1, nationality);
            preparedStatement.setString(2, designation);
            ResultSet resultSet = preparedStatement.executeQuery();
            displayResultSet(resultSet);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteEmployeesByYear(String year) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employees WHERE year_of_joining < ?");
            preparedStatement.setString(1, year);
            int rowsAffected = preparedStatement.executeUpdate();
            resultArea.setText(rowsAffected + " rows deleted.");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void displayResultSet(ResultSet resultSet) throws SQLException {
        StringBuilder result = new StringBuilder();
        while (resultSet.next()) {
            result.append("EmpID: ").append(resultSet.getString("empid")).append(", ");
            result.append("Name: ").append(resultSet.getString("name")).append(", ");
            result.append("Designation: ").append(resultSet.getString("designation")).append(", ");
            result.append("Nationality: ").append(resultSet.getString("nationality")).append(", ");
            result.append("Year of Joining: ").append(resultSet.getString("year_of_joining")).append(", ");
            result.append("Salary: ").append(resultSet.getString("salary")).append("\n");
        }
        resultArea.setText(result.toString());
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                EmployeeManagementGUI frame = new EmployeeManagementGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
