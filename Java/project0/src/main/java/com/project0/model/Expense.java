package com.project0.model;

public class Expense {

    private int id;
    private String username;
    private double amount;
    private String description;
    private String date;
    private String status;
    private String comment;

    public Expense(int id, String username, double amount,
                   String description, String date,
                   String status, String comment) {

        this.id = id;
        this.username = username;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.status = status;
        this.comment = comment;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public String getComment() { return comment; }
}