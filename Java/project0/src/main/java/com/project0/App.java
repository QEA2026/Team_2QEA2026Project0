package com.project0;
import java.util.Scanner;
import com.project0.LoginManager;
public class App 
{
    static String Username;
    static String Password;
    public static void main( String[] args )
    {
        Scanner input = new Scanner(System.in);
        //initialize for login details
        initializeApp(input);
        
        // ASCII art for welcome message
        System.out.println(" __      __       .__                                \r\n" + //
                        "/  \\    /  \\ ____ |  |   ____  ____   _____   ____   \r\n" + //
                        "\\   \\/\\/   // __ \\|  | _/ ___\\/  _ \\ /     \\_/ __ \\  \r\n" + //
                        " \\        /\\  ___/|  |_\\  \\__(  <_> )  Y Y  \\  ___/  \r\n" + //
                        "  \\__/\\  /  \\___  >____/\\___  >____/|__|_|  /\\___  > \r\n" + //
                        "       \\/       \\/          \\/            \\/     \\/  ");
        // main loop
        
        boolean isrunning = true;
        while (isrunning == true){
            System.out.println("L: List Pending Expenses");
            System.out.println("A: Approve OR Deny Expenses");
            System.out.println("C: Add Comments to Expense Decisions");
            System.out.println("G: Generate Reports");
            System.out.println("Q: Quit Application");
            System.out.println("Please enter your operation: ");

            switch(input.next()){
                case "L": {
                    // List pending expenses
                    break;
                }
                case "A": {
                    // Approve or deny expenses
                    break;
                }
                case "C": {
                    // Add comments to expense decisions
                    break;
                }
                case "G": {
                    // Generate reports
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

    public static void initializeApp(Scanner input){
        System.out.println("Initializing Manager Application...");
        System.out.println("Please enter your username: ");
        Username = input.next();
        System.out.println("Please enter your password: ");
        Password = input.next();
        LoginManager Session = new LoginManager(Username, Password);
    }
}
