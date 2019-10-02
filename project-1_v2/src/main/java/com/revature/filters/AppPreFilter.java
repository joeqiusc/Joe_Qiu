package com.revature.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Principal;
import com.revature.util.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

@WebFilter("/*")
public class AppPreFilter extends HttpFilter {

	private static final long serialVersionUID = 1L;
	private static Logger log = LogManager.getLogger(AppPreFilter.class);
	
	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
		log.info("Request intercepted by TokenAuthFilter.doFilter");
		
		extractPrincipal(req);
		attachCorsResponseHeaders(resp);
		chain.doFilter(req, resp);
		return;
	}
	
	public void extractPrincipal(HttpServletRequest req) throws IOException, ServletException {
        log.info("Attempting to extract principal object from JWT...");
        String header = req.getHeader(JwtConfig.HEADER);
        
        if(header == null || !header.startsWith(JwtConfig.PREFIX)) {
            log.info("No token found with required prefix");
            return;
        }
        
        String token = header.replaceAll(JwtConfig.PREFIX, "");
        
        try {
            
            Claims claims = Jwts.parser()
                    .setSigningKey(JwtConfig.SIGNING_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            
            Principal principal = new Principal();
            principal.setId(Integer.parseInt(claims.getId()));
            principal.setUsername(claims.getSubject());
            principal.setRole(claims.get("role", String.class));
            
            req.setAttribute("principal", principal);
            
        } catch (ExpiredJwtException eje) {
            log.info("JWT expired. Reauthentication required.");
        } catch (Exception e) {
            log.error("Error parsing JWT");
            e.printStackTrace();
        }
    }
	
	public void attachCorsResponseHeaders(HttpServletResponse resp) throws IOException, ServletException {
        log.info("Attaching CORS headers to HTTP response");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Content-type, Authorization");
        resp.setHeader("Access-Control-Expose-Headers", "Authorization");
    }
	

}
