package org.jewelryshop.apigatewayservice.filter;

import org.jewelryshop.apigatewayservice.configuration.CustomJwtDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    public JwtAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            if (path.startsWith("/authentication/") || path.contains("/product/public")) {
                return chain.filter(exchange);
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);
            try {
                Jwt jwt = customJwtDecoder.decode(token);
                String userId = jwt.getSubject();
                List<String> roles = jwt.getClaimAsStringList("scope");

                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("User-Id", userId)
                        .header("Roles", roles.get(0))
                        .build();

                return chain.filter(exchange.mutate().request(modifiedRequest).build());

            } catch (JwtException e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }
    public static class Config {
        // Bạn có thể thêm các config cần thiết ở đây
    }
}

