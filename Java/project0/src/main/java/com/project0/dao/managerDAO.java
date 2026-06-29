package com.project0.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.project0.util.ConnectManager;

public class managerDAO {
    
    public static void listExpenses() {

    String sql =
        "SELECT e.id, u.username, e.amount, e.description, e.date, a.status " +
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
            System.out.println("Expense not found or already processed.");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public void commentExpenses(){
        //todo
    }

    public void generateReports(){
        //todo
        //reports, order by expenses amount per employee?

    }
}
