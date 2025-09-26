package com.project.apigateway.utility;

import com.project.apigateway.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {
	
	private final String SECRET_KEY = "hey_this_is_my_super_secret_key(*)(*)234156";
	private final SecretKey encryptedKey =  Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private final RedisCacheUtil redisCacheUtil;

    public JWTUtil(RedisCacheUtil redisCacheUtil) {
        this.redisCacheUtil = redisCacheUtil;
    }

    public String getUsernameFromJwtToken(final String token) {
        return getClaimsFromToken(token).getSubject();
	}

    public UserRole getUserRoleFromJwtToken(final String token) {
        final Claims claims = getClaimsFromToken(token);
        return UserRole.valueOf(claims.get(AuthConstants.AUTH_JWT_ROLE_CLAIM, String.class));
    }

    private Claims getClaimsFromToken(final String token) {
		return Jwts.parser()
				.verifyWith(encryptedKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public boolean validateToken(final String token) {
		final Claims claimsFromToken = getClaimsFromToken(token);
        final String cacheToken = redisCacheUtil.getCacheData(AuthConstants.AUTH_CACHE_WITH_EXPIRY_NAMESPACE, getUsernameFromJwtToken(token)).toString();
        return cacheToken.equals(token) && !claimsFromToken.getExpiration().before(new Date());
	}

}
