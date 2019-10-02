package com.revature.util;

import java.security.Key;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.SignatureAlgorithm;

public class JwtConfig {

	public static final String HEADER = "Authorization";
	public static final String PREFIX = "Bearer ";
	public static final String SECRET = "reimburse_app";
	public static final int EXPIRATION = 24 * 60 * 60 * 1000;
	public static final Key SIGNING_KEY;
	
	static {
		// Create a signing key using the SECRET keyword
		SignatureAlgorithm signatureAlg = SignatureAlgorithm.HS256;
		byte[] secretBytes = DatatypeConverter.parseBase64Binary(SECRET);
		SIGNING_KEY = new SecretKeySpec(secretBytes, signatureAlg.getJcaName());
	}
	
	private JwtConfig() {
		super();
	}
}
