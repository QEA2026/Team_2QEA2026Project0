package com.project0.model;

public class EmployeeTotal {

    private String username;
    private int totalExpenses;
    private double totalAmount;

    public EmployeeTotal(String username, int totalExpenses, double totalAmount) {
        this.username = username;
        this.totalExpenses = totalExpenses;
        this.totalAmount = totalAmount;
    }

    public String getUsername() {
        return username;
    }

    public int getTotalExpenses() {
        return totalExpenses;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
}