package com.project0;
import java.util.Scanner;
import com.project0.dao.*;

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
        while (isrunning == true){
            System.out.println("L: List Pending Expenses");
            System.out.println("A: Approve OR Deny Expenses");
            System.out.println("C: Add Comments to Expense Decisions");
            System.out.println("G: Generate Reports");
            System.out.println("Q: Quit Application");
            System.out.println("Please enter your operation: ");


            managerDAO managerDAO = new managerDAO();
            switch(input.next()){
                case "L": {
                    managerDAO.listExpenses();
                    break;
                }
                case "A": {
                    managerDAO.approveDenyExpenses(input);
                    break;
                }
                case "C": {
                    managerDAO.commentExpenses(input);
                    break;
                }
                case "G": {
                    managerDAO.generateReports(input);
                    break;
                }
                case "Q": {
                    // Quit application
                    input.close();
                    isrunning = false;
                    System.out.println("Exiting Manager Application. Goodbye!");

                    System.exit(0);
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
}
