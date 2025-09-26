package com.project.apigateway.filter;

import com.project.apigateway.configuration.JwtValidationProperties;
import com.project.apigateway.configuration.ServiceRoleMappingProperties;
import com.project.apigateway.enums.UserPermission;
import com.project.apigateway.enums.UserRole;
import com.project.apigateway.utility.JWTUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JWTFilter implements GlobalFilter {
	
	private final JWTUtil jwtUtil;
    private final ServiceRoleMappingProperties serviceRoleMappingProperties;
    private final JwtValidationProperties jwtValidationProperties;

    private final PathPatternParser pathMatcher = new PathPatternParser();

	public JWTFilter(JWTUtil jwtUtil, ServiceRoleMappingProperties serviceRoleMappingProperties, JwtValidationProperties jwtValidationProperties) {
		this.jwtUtil = jwtUtil;
        this.serviceRoleMappingProperties = serviceRoleMappingProperties;
        this.jwtValidationProperties = jwtValidationProperties;
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
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("INSIDE JWT FILTER");
        final ServerHttpRequest request = exchange.getRequest();
        final String requestPath = request.getPath().toString();

        if(jwtValidationProperties.getExcludedServices().contains(requestPath)) {
           return chain.filter(exchange);
        }

        final String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, chain);
        }

        final String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            return unauthorized(exchange, chain);
        }

        final String username = jwtUtil.getUsernameFromJwtToken(token);
        final UserRole role = jwtUtil.getUserRoleFromJwtToken(token);

        if (!isAuthorised(request, role)) {
            return forbidden(exchange, chain);
        }
        request.mutate()
                .header("X-Username", username)
                .build();

        return chain.filter(exchange);

    }

    private boolean isAuthorised(ServerHttpRequest request, UserRole role) {
        final String path = request.getPath().toString();
        final String method = request.getMethod().name();

        return serviceRoleMappingProperties.getEndpoints().stream()
                .filter(endpoint -> pathMatcher.parse(endpoint.getPath()).matches(PathContainer.parsePath(path))
                        && endpoint.getMethod().equalsIgnoreCase(method))
                .findFirst()
                .map(endpoint -> role.getUserPermissions().contains(UserPermission.valueOf(endpoint.getPermission())))
                .orElse(false);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    private Mono<Void> forbidden(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }

//    User userDetails = new User();
//        userDetails.setUsername(username);
//        userDetails.setRole(role);
//
//    UsernamePasswordAuthenticationToken authentication =
//            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//        return chain.filter(exchange)
//            .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(
//            Mono.just(new SecurityContextImpl(authentication))
//            ));



}
