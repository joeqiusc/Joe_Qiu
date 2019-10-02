package com.revature.util;

import javax.servlet.http.HttpServletRequest;

public class RequestViewHelper {
	
	public static String process(HttpServletRequest req) {
		
		switch(req.getRequestURI()) {
		
		case "/project-1_v2/login.view":
			return "partials/login.html";
			
		case "/project-1_v2/register.view":
			return "partials/register.html";
			
		case "/project-1_v2/home.view":
			return "partials/home.html";
			
		default:
			return "index.html";
			
		}
	}

}
