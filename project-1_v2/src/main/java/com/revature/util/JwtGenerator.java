package com.revature.util;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Principal;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtGenerator {
	
	private static Logger log = LogManager.getLogger(JwtGenerator.class);

	public static String createJwt(Principal subject) {
		
		log.info("Creating JWT for: " + subject.getUsername());
		
		// The JWT Signature Algorithm used to sign the token
		SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;
		
		long nowMillis = System.currentTimeMillis();
		
		// Configure the JWT and set its claims
		JwtBuilder builder = Jwts.builder()
				.setId(Integer.toString(subject.getId()))
				.setSubject(subject.getUsername())
				.setIssuer("revature")
				.claim("role", subject.getRole())
				.setExpiration(new Date(nowMillis + JwtConfig.EXPIRATION))
				.signWith(sigAlg, JwtConfig.SIGNING_KEY);
		
		log.info("JWT successfully created for: " + subject.getUsername());
		
		// Build the JWT and serialize it into a compact, URL-safe string
		return builder.compact();
		
	}
}
