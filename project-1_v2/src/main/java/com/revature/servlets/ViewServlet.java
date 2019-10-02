package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.util.RequestViewHelper;

/**
 * This servlet serves as a front controller for retrieving partial HTML files.
 * 
 * With the component-based UI design, this servlet is defunct and exists only 
 * for example sake.
 * 
 * @author Wezley
 *
 */
@WebServlet("*.view")
public class ViewServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String nextView = RequestViewHelper.process(req);
		req.getRequestDispatcher(nextView).forward(req, resp);
		
		
	}

	
}
