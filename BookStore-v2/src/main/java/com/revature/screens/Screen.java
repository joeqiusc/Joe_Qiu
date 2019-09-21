package com.revature.screens;

import java.io.BufferedReader;

public abstract class Screen {
	
	private String route;
	
	protected Screen(String route) {
		super();
		this.route = route;
	}
	
	public String getRoute() {
		return route;
	}
	
	public abstract void start(BufferedReader br);

}
