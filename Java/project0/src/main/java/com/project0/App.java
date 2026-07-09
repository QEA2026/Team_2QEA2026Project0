package com.project0;
import java.util.List;
import java.util.Scanner;
import com.project0.dao.*;
import com.project0.model.Expense;
import com.project0.util.ExpensePrinter;

public class App 
{
    public static void main( String[] args )
    {
        
        Scanner input = new Scanner(System.in);
        //initialize for login details
        boolean isrunning = false;

        while(isrunning == false){
            isrunning = initializeApp(input);
            if(isrunning == false){
                System.out.println("Invalid Username or Password");
            }
        }

        // ASCII art for welcome message
        System.out.println(" __      __       .__                                \r\n" + //
                        "/  \\    /  \\ ____ |  |   ____  ____   _____   ____   \r\n" + //
                        "\\   \\/\\/   // __ \\|  | _/ ___\\/  _ \\ /     \\_/ __ \\  \r\n" + //
                        " \\        /\\  ___/|  |_\\  \\__(  <_> )  Y Y  \\  ___/  \r\n" + //
                        "  \\__/\\  /  \\___  >____/\\___  >____/|__|_|  /\\___  > \r\n" + //
                        "       \\/       \\/          \\/            \\/     \\/  ");

        // main loop
        managerDAO manager = new managerDAO();
        while (isrunning == true){
            System.out.println("L: List Expenses");
            System.out.println("A: Approve OR Deny Expenses");
            System.out.println("C: Add Comments to Expense Decisions");
            System.out.println("G: Generate Reports");
            System.out.println("Q: Quit Application");
            System.out.println("Please enter your operation: ");
            switch(input.next()){
                case "L": {
                    listExpenses(manager);
                    break;
                }
                case "A": {
                    approveDenyExpenses(input, manager);
                    break;
                }
                case "C": {
                    commentExpenses(input, manager);
                    break;
                }
                case "G": {
                    generateReports(input, manager);
                    break;
                }
                case "Q": {
                    // Quit application
                    input.close();
                    isrunning = false;
                    System.out.println("Exiting Manager Application. Goodbye!");
                    break;
                }
                default: {
                    System.out.println("ERROR; Not an operation.");
                }
            }
        }
        }

    public static boolean initializeApp(Scanner input){

        System.out.println("Initializing Manager Application...");
        System.out.println("Please enter your username: ");
        String username = input.next();
        System.out.println("Please enter your password: ");
        String password = input.next();
        loginDAO session = new loginDAO();
        boolean success = session.login(username, password);

        if(success == true){
            System.out.println("Login Successful.");
            return true;
        }else{
            return false;
        }

    }


    public static void approveDenyExpenses(Scanner input, managerDAO manager){
        System.out.println("Pending expenses...");
        List<Expense> expenses = manager.getPendingExpenses();
        ExpensePrinter.printExpenses(expenses);
        System.out.println("Please enter the ID of the expense to review (0 to cancel): ");
        
        int expenseid = input.nextInt();
        if(expenseid == 0){
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
        boolean success = manager.approveDenyExpenses(expenseid, status);

        if (success) {
        System.out.println("Expense updated.");
        } else {
        System.out.println("Expense not found.");
        }

    }

    public static void listExpenses(managerDAO manager){
        List<Expense> expenses = manager.getAllExpenses();
        ExpensePrinter.printExpenses(expenses);
    }

    public static void commentExpenses(Scanner input, managerDAO manager){
        List<Expense> expenses = manager.getAllExpenses();
        ExpensePrinter.printExpenses(expenses);

        System.out.println("Please enter the ID of the expense to comment (0 to cancel): ");
        int expenseid = input.nextInt();

        if (expenseid == 0){
            System.out.println("Canceling Operation");
            return;
        }

        System.out.println("Enter comment: ");
        input.nextLine();
        String comment = input.nextLine();

        boolean success = manager.commentExpenses(expenseid, comment);

        if (success == true){
            System.out.println("Expense successfully updated");
        }else{
            System.out.println("Expense not found");
        }
    }


       public static void generateReports(Scanner input, managerDAO manager){
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
            reportManager.reportByEmployee(input, manager);
            break;
        case 2:
            reportManager.reportByDate(input, manager);
            break;
        case 3:
            reportManager.employeeTotals(manager);
            break;
        case 0:
            return;
        default:
            System.out.println("Invalid selection.");
    }
}
}
