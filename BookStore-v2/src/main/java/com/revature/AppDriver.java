package com.revature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.revature.screens.DashboardScreen;
import com.revature.screens.HomeScreen;
import com.revature.screens.LoginScreen;
import com.revature.screens.RegisterScreen;
import com.revature.util.Router;
import com.revature.util.UserSession;

public class AppDriver {
	
	public static UserSession currentSession;
	public static boolean appRunning;
	public static Router router = new Router();
	
	static {
		router.addScreen(new HomeScreen());
		router.addScreen(new LoginScreen());
		router.addScreen(new RegisterScreen());
		router.addScreen(new DashboardScreen());
	}
	
	public static void main(String[] args) {
		
		appRunning = true;
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(appRunning) {
			router.navigate("/home", br);
		}
		
		try {
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
	}

}
