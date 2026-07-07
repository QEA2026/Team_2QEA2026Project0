package com.project0;

import java.util.List;
import java.util.Scanner;

import com.project0.dao.managerDAO;
import com.project0.model.*;
import com.project0.util.ExpensePrinter;


public class reportManager {

    public static void reportByEmployee(Scanner input, managerDAO manager) {

    System.out.print("Enter Employee username: ");
    String username = input.next();

    List<Expense> expenses = manager.reportByEmployee(username);

    ExpensePrinter.printExpenses(expenses);
    }

    public static void reportByDate(Scanner input, managerDAO manager) {

    System.out.print("Enter date (YYYY-MM-DD): ");
    String date = input.next();

    List<Expense> expenses = manager.reportByDate(date);

    ExpensePrinter.printExpenses(expenses);
    }


    public static void employeeTotals(managerDAO manager) {

    List<EmployeeTotal> totals = manager.employeeTotals();

    for (EmployeeTotal total : totals) {

            System.out.println("------------------");
            System.out.println("Employee: " + total.getUsername());
            System.out.println("Expenses Submitted: " + total.getTotalExpenses());
            System.out.println("Total Amount: $" + total.getTotalAmount());
        }
    }   
    
}
