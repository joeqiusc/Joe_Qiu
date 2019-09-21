package com.revature.screens;

import static com.revature.AppDriver.*;

import java.io.BufferedReader;
import java.sql.Connection;

import com.revature.AppDriver;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.ConnectionFactory;
import com.revature.util.UserSession;

public class RegisterScreen extends Screen {
    
    private UserService userService = new UserService();
    
    public RegisterScreen() {
    	super("/register");
    	System.out.println("Initializing RegisterScreen");
    }

    @Override
    public void start(BufferedReader br) {
        
        // Method-scoped references to hold registration input
        String username;
        String password;
        String firstName;
        String lastName;
        
        try {
            
            // Get user registration input
            System.out.println("\n+---------------------------------+\n");
            System.out.println("Sign up for a new account");
            System.out.print("Username: ");
            username = br.readLine();
            System.out.print("Password: ");
            password = br.readLine();
            System.out.print("First name: ");
            firstName = br.readLine();
            System.out.print("Last name: ");
            lastName = br.readLine();
            
            // Create a user object using the input provided, set the role to USER by default
            User newUser = new User(username, password, firstName, lastName, new Role("USER"));
            newUser = userService.register(newUser);
            
            if(newUser != null) {
            	Connection conn = ConnectionFactory.getInstance().getConnection(newUser);
            	currentSession = new UserSession(newUser, conn);	
            
                System.out.println("New user created! Logging in and navigating to dashboard...");
                router.navigate("/dashboard", br);
                return;
            } 
            
            // If AppState.currentUser is null, then the registration process failed due to invalid field values
            else {
                System.out.println("User not created, invalid field values");
            }
            
        } catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Error reading input from console");
            AppDriver.appRunning = false;
        }
        
    }

}