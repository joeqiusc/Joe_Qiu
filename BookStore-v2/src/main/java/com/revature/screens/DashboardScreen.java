package com.revature.screens;

import static com.revature.AppDriver.*;

import java.io.BufferedReader;

public class DashboardScreen extends Screen {
	
	public DashboardScreen() {
		super("/dashboard");
		System.out.println("Initializing DashboardScreen");
	}

	@Override
	public void start(BufferedReader br) {
		System.out.println("Dashboard screen undergoing refactor.");
		router.navigate("/home", br);
		return;
	}

}
