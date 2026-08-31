package com.mariuszilinskas.streamix.infra.gateway.filter;

import com.mariuszilinskas.streamix.infra.gateway.util.AppUtils;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Objects;

@NullMarked
@Component
public class UserIdFilter implements WebFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(UserIdFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String requestPath = exchange.getRequest().getURI().getPath();

        if (!requestPath.contains(AppUtils.PATH_USER_ID)) {
            return chain.filter(exchange);
        }

        return ReactiveSecurityContextHolder.getContext()
                .map(context -> Objects.requireNonNull(context.getAuthentication()).getName())
                .flatMap(userId -> {
                    String modifiedPath = requestPath.replace(AppUtils.PATH_USER_ID, userId);

                    logger.debug(
                            "Replacing _USER_ID_ path placeholder with user ID [userId: '{}']",
                            userId
                    );

                    return chain.filter(exchange.mutate()
                            .request(request -> request.path(modifiedPath))
                            .build());
                });
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

}
