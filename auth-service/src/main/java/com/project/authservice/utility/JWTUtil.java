package com.project.authservice.utility;

import com.project.authservice.enums.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {

    private final String SECRET_KEY = "hey_this_is_my_super_secret_key(*)(*)234156";
	private final SecretKey encryptedKey =  Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    @Cacheable(value = AuthConstants.AUTH_CACHE_WITH_EXPIRY_NAMESPACE, key = "#username")
	public String generateToken(String username, UserRole role) {
		return Jwts.builder()
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + AuthConstants.AUTH_JWT_EXPIRY_TIME_IN_MILLIS))
				.subject(username)
                .claim(AuthConstants.AUTH_JWT_ROLE_CLAIM, role.name())
				.signWith(encryptedKey)
				.compact();
	}

}
