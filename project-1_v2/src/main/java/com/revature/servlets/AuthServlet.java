package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.models.Credentials;
import com.revature.models.ErrorResponse;
import com.revature.models.Principal;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.util.JwtConfig;
import com.revature.util.JwtGenerator;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(AuthServlet.class);
	
	private final UserService userService = new UserService();

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		
		log.info("Request received by AuthServlet.doPost");
		
		// Create some method-scoped convenience variables
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter writer = resp.getWriter();
		Credentials credentials = null;
		User authUser = null;
		
		try {
			
			// Use Jackson API to read the request body and map the JSON inside to a Java POJO
			credentials = mapper.readValue(req.getInputStream(), Credentials.class);

			// Validate that the credentials object retrieved does not contain non-values
			if(credentials == null || credentials.getUsername() == null || credentials.getUsername().equals("") 
					|| credentials.getPassword() == null || credentials.getPassword().equals("")) {
				log.warn("Invalid credentials object in request body");
				resp.setStatus(400);
				return;
			}
			
			log.info("Attempting to login using provided credentials...");
			authUser = userService.login(credentials.getUsername(), credentials.getPassword());
			
			
			if(authUser == null) {
				log.warn("Invalid credentials provided, no user found.");
				resp.setStatus(401);
				return;
			}
			
			log.info("User successfully retrieved using provided credentials.");
			Principal principal = new Principal(authUser.getId(), authUser.getUsername(), authUser.getRole().getRoleName());
			String token = JwtGenerator.createJwt(principal);
						
			resp.setContentType("application/json");
			resp.addHeader(JwtConfig.HEADER, JwtConfig.PREFIX + token);
			writer.write(mapper.writeValueAsString(principal));
			
			/*
			 * If we wanted to do server-side session management, we would use the Servlet API's
			 * HttpSession interface to manage a client's session with our server:
			 * 
			 * 		HttpSession session = request.getSession();
			 * 
			 * Invalidating the session would require another endpoint to handle logging out:
			 * 
			 * 		request.getSession().invalidate();
			 */

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
