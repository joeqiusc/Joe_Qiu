package com.revature.util;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.revature.screens.Screen;

public class Router {
	
	private List<Screen> screens = new ArrayList<>();

	public List<Screen> getScreens() {
		return screens;
	}

	public void setScreens(List<Screen> screens) {
		this.screens = screens;
	}
	
	public boolean addScreen(Screen newScreen) {
		return screens.add(newScreen);
	}
	
	public void navigate(String view, BufferedReader br) {
		
		Optional<Screen> _screen = screens.stream().filter(screen -> screen.getRoute().equals(view)).findFirst();
		if(_screen.isPresent()) _screen.get().start(br);
		else throw new RuntimeException("Invalid view specified.");
		
	}

}
