package com.project0.util;

import java.util.List;
import com.project0.model.Expense;

public class ExpensePrinter {

    public static void printExpenses(List<Expense> expenses) {

        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        for (Expense expense : expenses) {
            System.out.println("-------------------------");
            System.out.println("Expense ID: " + expense.getId());
            System.out.println("Employee : " + expense.getUsername());
            System.out.println("Amount   : $" + expense.getAmount());
            System.out.println("Reason   : " + expense.getDescription());
            System.out.println("Date     : " + expense.getDate());
            System.out.println("Status   : " + expense.getStatus());
            System.out.println("Comment  : " + expense.getComment());
        }
    }
}