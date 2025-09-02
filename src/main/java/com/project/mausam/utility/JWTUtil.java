package com.project.mausam.utility;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	
	private String SECRET_KEY = "hey_this_is_my_super_secret_key(*)(*)234156";
	private SecretKey encryptedKey =  Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	
	public String generateToken(String username) {
		return Jwts.builder()
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 600000))
				.subject(username)
				.signWith(encryptedKey)
				.compact();
	}

	public String getUsernameFromJwtToken(final String token) {
		final Claims claimsFromToken = getClaimsFromToken(token);
		return claimsFromToken.getSubject();
	}

	private Claims getClaimsFromToken(final String token) {
		return Jwts.parser()
				.verifyWith(encryptedKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public boolean validateToken(final UserDetails userDetails, final String username, final String token) {
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token); 
	}

	private boolean isTokenExpired(String token) {
		final Claims claimsFromToken = getClaimsFromToken(token);
		return claimsFromToken.getExpiration().before(new Date());
	}

}
