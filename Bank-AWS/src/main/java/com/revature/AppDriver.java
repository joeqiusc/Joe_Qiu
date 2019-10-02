package com.revature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.revature.screens.HomeScreen;
import com.revature.screens.Screen;
import com.revature.util.UserSession;
/**
 * This is the driver class, the entrance for the mockbank.
 * */
public class AppDriver {
	
	public static UserSession currentSession;
	public static boolean appRunning;
	
	public static void main(String[] args) {
		
		appRunning = true;
		
		Screen screen = new HomeScreen();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while(appRunning) {
			screen.start(br);
		}
		
		try {
			br.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		
	}
	
}
