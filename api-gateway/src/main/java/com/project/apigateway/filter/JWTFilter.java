package com.project.apigateway.filter;

import com.project.apigateway.entity.User;
import com.project.apigateway.utility.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JWTFilter implements WebFilter {
	
	private final JWTUtil jwtUtil;

	public JWTFilter(JWTUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
        System.out.println("jwt filter init.");
	}

//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		final String authHeader = request.getHeader("Authorization");
//		String token = null;
//		String username = null;
//		if(authHeader != null && authHeader.startsWith("Bearer ")) {
//			token = authHeader.substring(7);
//			username = jwtUtil.getUsernameFromJwtToken(token);
//
//			if(username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
//				if(jwtUtil.validateToken(token)) {
//                    User userDetails = new User();
//                    userDetails.setUsername(username);
//                    userDetails.setRole(jwtUtil.getUserRoleFromJwtToken(token));
//					final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//					usernamePasswordAuthenticationToken.setDetails(request);
//					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//				}
//			}
//		}
//		filterChain.doFilter(request, response);
//	}

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        final String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtUtil.getUsernameFromJwtToken(token);

            if (username != null && jwtUtil.validateToken(token)) {
                User userDetails = new User();
                userDetails.setUsername(username);
                userDetails.setRole(jwtUtil.getUserRoleFromJwtToken(token));

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                return chain.filter(exchange)
                        .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(
                                Mono.just(new SecurityContextImpl(authentication))
                        ));
            }
        }
        return chain.filter(exchange);
    }

}
