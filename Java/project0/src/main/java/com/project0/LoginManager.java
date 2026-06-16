package com.project0;

public class LoginManager {

    private String username;
    private String password;
    private boolean isLoggedIn;

    public LoginManager(String username, String password){
        this.username = username;
        this.password = password;
        checkLogin();

        if (isLoggedIn){
            System.out.println("Login successful! Welcome " + username);
        } else {
            throw new IllegalArgumentException("Login failed! Please check your username and password.");
        }

    }

    private void checkLogin(){
        // Implement login sqlite database. Use Bcrypt to hash
        isLoggedIn = true; // Placeholder for successful login
    }

}
