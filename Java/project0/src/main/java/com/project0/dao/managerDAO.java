package com.project0.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.project0.util.ConnectManager;

public class managerDAO {
    
    public static void listExpenses() {

    String sql =
        "SELECT e.id, u.username, e.amount, e.description, e.date " +
        "FROM expenses e " +
        "JOIN users u ON e.user_id = u.id " +
        "JOIN approvals a ON e.id = a.expense_id " +
        "WHERE a.status = 'Pending'";

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
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public void approveDenyExpenses(){
        //todo
    }

    public void commentExpenses(){
        //todo
    }

    public void generateReports(){
        //todo
        //reports, order by expenses amount per employee?
        
    }
}
