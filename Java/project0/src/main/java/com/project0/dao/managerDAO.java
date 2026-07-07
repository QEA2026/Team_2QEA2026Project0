package com.project0.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.project0.util.ConnectManager;
import com.project0.model.EmployeeTotal;
import com.project0.model.Expense;

public class managerDAO {
    
    public List<Expense> getAllExpenses() {

    List<Expense> expenses = new ArrayList<>();    
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

            Expense expense = new Expense(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getDouble("amount"),
                rs.getString("description"),
                rs.getString("date"),
                rs.getString("status"),
                rs.getString("comment")
            );

            expenses.add(expense);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return expenses;
}

    public List<Expense> getPendingExpenses() {

    List<Expense> expenses = new ArrayList<>();

    String sql =
        "SELECT e.id, u.username, e.amount, e.description, e.date, " +
        "a.status, a.comment " +
        "FROM expenses e " +
        "JOIN users u ON e.user_id = u.id " +
        "JOIN approvals a ON e.id = a.expense_id " +
        "WHERE a.status = 'pending'";

    try (
        Connection conn = ConnectManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()
    ) {

        while (rs.next()) {

            Expense expense = new Expense(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getDouble("amount"),
                rs.getString("description"),
                rs.getString("date"),
                rs.getString("status"),
                rs.getString("comment")
            );

            expenses.add(expense);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return expenses;
}

    public boolean approveDenyExpenses(int expenseid, String status){
        String sql = "UPDATE approvals SET status = ? WHERE expense_id = ?";

           try (
        Connection conn = ConnectManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setString(1, status);
        stmt.setInt(2, expenseid);

        int rows = stmt.executeUpdate();

        if (rows > 0) {
            return true;
        } else {
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


    public boolean commentExpenses(int expenseid, String comment){

        String sql = "UPDATE approvals SET comment = ? WHERE expense_id = ? ";

        try (
        Connection conn = ConnectManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)
    ) {

        stmt.setString(1, comment);
        stmt.setInt(2, expenseid);

        int rows = stmt.executeUpdate();

        if (rows > 0) {
            return true;
        } else {
            return false;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


public List<Expense> reportByEmployee(String username) {

    List<Expense> expenses = new ArrayList<>();

    String sql =
    "SELECT e.id, u.username, e.amount, e.description, e.date, " +
    "a.status, a.comment " +
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

            Expense expense = new Expense(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getDouble("amount"),
                rs.getString("description"),
                rs.getString("date"),
                rs.getString("status"),
                rs.getString("comment")
                );

            expenses.add(expense);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return expenses;
}


public List<Expense> reportByDate(String date) {

    List<Expense> expenses = new ArrayList<>();

    String sql =
        "SELECT e.id, u.username, e.amount, e.description, e.date, " +
        "a.status, a.comment " +
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

            Expense expense = new Expense(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getDouble("amount"),
                rs.getString("description"),
                rs.getString("date"),
                rs.getString("status"),
                rs.getString("comment")
            );

            expenses.add(expense);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return expenses;
}

public List<EmployeeTotal> employeeTotals() {

    List<EmployeeTotal> totals = new ArrayList<>();

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

            EmployeeTotal total = new EmployeeTotal(
                rs.getString("username"),
                rs.getInt("total_expenses"),
                rs.getDouble("total_amount")
            );

            totals.add(total);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return totals;
}

}
