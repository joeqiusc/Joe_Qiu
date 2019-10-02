package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.ErrorResponse;
import com.revature.models.Principal;
import com.revature.models.User;
import com.revature.services.UserService;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(UserServlet.class);
	
	private final UserService userService = new UserService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		log.info("Request received by UserServlet.doGet");
		resp.setContentType("application/json");
		
		Principal principal = (Principal) req.getAttribute("principal");
		PrintWriter writer = resp.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		
		if(principal == null) {
			log.warn("No principal object found on request.");
			
			ErrorResponse err = new ErrorResponse(401, System.currentTimeMillis());
			err.setMessage("Unauthorized: No authentication token present on request.");
			
			resp.setStatus(401); // Unauthorized
			writer.write(mapper.writeValueAsString(err));
			return;
		}
		
		if(!principal.getRole().equalsIgnoreCase("ADMIN")) {
			log.warn("Unauthorized access attempt made.");
			
			ErrorResponse err = new ErrorResponse(403, System.currentTimeMillis());
			err.setMessage("Forbidden: Your role does not permit you to access this endpoint.");
			
			resp.setStatus(403); // Forbidden
			writer.write(mapper.writeValueAsString(err));
			return;
		}
		
		try {
			String userIdParam = req.getParameter("userId");
			
			if(userIdParam != null) {
				int userId =  Integer.parseInt(userIdParam);
				User user = userService.getUserById(userId);
				writer.write(mapper.writeValueAsString(user));
				
			} else {
				List<User> users = userService.getAllUsers();
				writer.write(mapper.writeValueAsString(users));
			}
			
			resp.setStatus(200);
			
		} catch (UserNotFoundException unfe) {
			log.error(unfe.getMessage());
			
			ErrorResponse err = new ErrorResponse(404, System.currentTimeMillis());
			err.setMessage("Resource Not Found: No resource found using given query params.");
			
			resp.setStatus(404);
			writer.write(mapper.writeValueAsString(err));
			return;
			
		} catch (NumberFormatException nfe) {
			log.error(nfe.getMessage());
			
			ErrorResponse err = new ErrorResponse(400, System.currentTimeMillis());
			err.setMessage("Bad Request: Malformed userId param value provided.");
			
			resp.setStatus(404);
			writer.write(mapper.writeValueAsString(err));
			return;
			
		} catch (Exception e) {
			log.error(e.getMessage());
			
			ErrorResponse err = new ErrorResponse(500, System.currentTimeMillis());
			err.setMessage("Internal Server Error: It's not you, it's us.");
			
			resp.setStatus(500);
			writer.write(mapper.writeValueAsString(err));
			return;
			
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.info("Request received by UserServlet.doPost");
		resp.setContentType("application/json");
		
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter writer = resp.getWriter();
		User newUser = null;
		
		try {
			newUser = mapper.readValue(req.getInputStream(), User.class);
			User persistedUser = userService.register(newUser);
			writer.write(mapper.writeValueAsString(persistedUser));
			resp.setStatus(201); // CREATED

		} catch (MismatchedInputException mie) {
			log.error(mie.getMessage());
			
			ErrorResponse err = new ErrorResponse();
			err.setStatus(400);
			err.setMessage("Bad Request: Malformed credentials object found in response body.");
			err.setTimestamp(System.currentTimeMillis());
			
			resp.setStatus(400);
			writer.write(mapper.writeValueAsString(err));
			
			return;
		
		} catch (Exception e) {
			log.error(e.getMessage());
			
			ErrorResponse err = new ErrorResponse();
			err.setStatus(500);
			err.setMessage("Internal Server Error: It's not you, it's us.");
			err.setTimestamp(System.currentTimeMillis());
			
			resp.setStatus(500);
			writer.write(mapper.writeValueAsString(err));
			
			return;
			
		}
		
		
	}

}
