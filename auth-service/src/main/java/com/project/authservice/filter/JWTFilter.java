package com.project.authservice.filter;

import java.io.IOException;

import com.project.authservice.service.MausamAuthUserDetailsService;
import com.project.authservice.utility.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {
	
	private final JWTUtil jwtUtil;
	private final MausamAuthUserDetailsService mausamAuthUserDetailsService;
	
	public JWTFilter(JWTUtil jwtUtil, MausamAuthUserDetailsService mausamAuthUserDetailsService) {
		this.jwtUtil = jwtUtil;
		this.mausamAuthUserDetailsService = mausamAuthUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
			username = jwtUtil.getUsernameFromJwtToken(token);
			
			if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
				final UserDetails userDetails = mausamAuthUserDetailsService.loadUserByUsername(username);
				if(jwtUtil.validateToken(userDetails, username, token)) {
					final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(request);
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
