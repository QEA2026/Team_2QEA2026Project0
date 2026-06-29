package com.project0.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.project0.util.ConnectManager;

public class managerDAO {
    
    public void listExpenses() {

    String sql =
        "SELECT e.id, u.username, e.amount, e.description, e.date, " +
        "a.status, a.comment " +
        "FROM expenses e " +
        "JOIN users u ON e.user_id = u.id " +
        "JOIN approvals a ON e.id = a.expense_id";
    try (
        Connection conn = ConnectManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()
    ) {

        while (rs.next()) {

            System.out.println("-------------------------");
            System.out.println("Expense ID: " + rs.getInt("id"));
            System.out.println("Employee : " + rs.getString("username"));
            System.out.println("Amount   : $" + rs.getDouble("amount"));
            System.out.println("Reason   : " + rs.getString("description"));
            System.out.println("Date     : " + rs.getString("date"));
            System.out.println("Status   : " + rs.getString("status"));
            System.out.println("Comment  : " + rs.getString("comment"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    private void listPendingExpenses(){
        String sql = 
            "SELECT e.id, u.username, e.amount, e.description, e.date, a.status " +
            "FROM expenses e " +
            "JOIN users u ON e.user_id = u.id " +
            "JOIN approvals a ON e.id = a.expense_id " +
            "WHERE a.status = 'pending'";
        
            //print pending expenses
        try (
        Connection conn = ConnectManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()
        ) {
        while (rs.next()) {
            System.out.println("-------------------------");
            System.out.println("Expense ID: " + rs.getInt("id"));
            System.out.println("Employee : " + rs.getString("username"));
            System.out.println("Amount   : $" + rs.getDouble("amount"));
            System.out.println("Reason   : " + rs.getString("description"));
            System.out.println("Date     : " + rs.getString("date"));
            System.out.println("Status   :" + rs.getString("status"));
        }
        } catch (SQLException e) {
        e.printStackTrace();
        }    
        

    }

    public void approveDenyExpenses(Scanner input){
        System.out.println("Pending expenses...");
        listPendingExpenses();
        System.out.println("Please enter the ID of the expense to review (0 to cancel): ");
        int expenseId = input.nextInt();
        if(expenseId == 0){
            System.out.println("Operation canceled");
            return;
        }

        System.out.println("Approve or Deny? (A/D) (0 to Cancel): ");
        String choice = input.next().trim().toUpperCase();

        String status;

        switch(choice){
            case "A": {
            status = "approved";
            break;
            }
            
            case "D":{
            status = "denied";
            break;
            }
            case "0":{
            return;
            }
            default:{
            System.out.println("Error, not a value.");
            return;
            }
        }
                String sql = "UPDATE approvals SET status = ? WHERE expense_id = ?";

           try (
        Connection conn = ConnectManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setString(1, status);
        stmt.setInt(2, expenseId);

        int rows = stmt.executeUpdate();

        if (rows > 0) {
            System.out.println("Expense " + expenseId + " has been " + status + ".");
        } else {
            System.out.println("Expense not found.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public void commentExpenses(Scanner input){
        listExpenses();
        System.out.println("Please enter the ID of the expense to comment (0 to cancel): ");
        int expenseId = input.nextInt();

        if (expenseId == 0){
            System.out.println("Canceling Operation");
            return;
        }

        System.out.println("Enter comment: ");
        input.nextLine();
        String comment = input.nextLine();

        String sql = "UPDATE approvals SET comment = ? WHERE expense_id = ? ";

        try (
        Connection conn = ConnectManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setString(1, comment);
        stmt.setInt(2, expenseId);

        int rows = stmt.executeUpdate();

        if (rows > 0) {
            System.out.println("Comment added successfully.");
        } else {
            System.out.println("Expense not found.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public void generateReports(Scanner input){
        System.out.println("   Generate Reports   ");
        System.out.println("1. Expenses by Employee");
        System.out.println("2. Expenses by Date");
        System.out.println("3. Total Expenses by Employee");
        System.out.println("0. Cancel");

        System.out.println("Please enter your selection: ");

        int option = input.nextInt();
        input.nextLine();

        switch (option) {
        case 1:
            reportByEmployee(input);
            break;
        case 2:
            reportByDate(input);
            break;
        case 3:
            employeeTotals();
            break;
        case 0:
            return;
        default:
            System.out.println("Invalid selection.");
    }
}


private void reportByEmployee(Scanner input) {

    System.out.print("Enter Employee username: ");
    String username = input.nextLine();

    String sql =
        "SELECT e.id, e.amount, e.description, e.date, a.status " +
        "FROM expenses e " +
        "JOIN users u ON e.user_id = u.id " +
        "JOIN approvals a ON e.id = a.expense_id " +
        "WHERE u.username = ?";

    try (
        Connection conn = ConnectManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            System.out.println("------------------");
            System.out.println("Expense ID: " + rs.getInt("id"));
            System.out.println("Amount: $" + rs.getDouble("amount"));
            System.out.println("Reason: " + rs.getString("description"));
            System.out.println("Date: " + rs.getString("date"));
            System.out.println("Status: " + rs.getString("status"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


private void reportByDate(Scanner input) {

    System.out.print("Enter date (YYYY-MM-DD): ");
    String date = input.nextLine();

    String sql =
        "SELECT u.username, e.amount, e.description, a.status " +
        "FROM expenses e " +
        "JOIN users u ON e.user_id = u.id " +
        "JOIN approvals a ON e.id = a.expense_id " +
        "WHERE e.date = ?";

    try (
        Connection conn = ConnectManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setString(1, date);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {

            System.out.println("------------------");
            System.out.println("Employee: " + rs.getString("username"));
            System.out.println("Amount: $" + rs.getDouble("amount"));
            System.out.println("Reason: " + rs.getString("description"));
            System.out.println("Status: " + rs.getString("status"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void employeeTotals() {

    String sql =
        "SELECT u.username, " +
        "COUNT(e.id) AS total_expenses, " +
        "SUM(e.amount) AS total_amount " +
        "FROM users u " +
        "JOIN expenses e ON u.id = e.user_id " +
        "GROUP BY u.username " +
        "ORDER BY total_amount DESC";

    try (
        Connection conn = ConnectManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()
    ) {

        while (rs.next()) {

            System.out.println("------------------");
            System.out.println("Employee: " + rs.getString("username"));
            System.out.println("Expenses Submitted: " + rs.getInt("total_expenses"));
            System.out.println("Total Amount: $" + rs.getDouble("total_amount"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


}
