package com.mariuszilinskas.streamix.infra.gateway.config;

import com.mariuszilinskas.streamix.infra.gateway.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    @Value("${app.frontendBaseUrl}")
    private String frontendBaseUrl;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/")
                        .filters(f -> f.redirect(302, frontendBaseUrl))
                        .uri("lb://HOME"))
                .route(p -> p
                        .path(AppUtils.API_PREFIX + "/auth/**")
                        .filters(f -> applyServiceFilters(f, "/auth/(?<segment>.*)"))
                        .uri("lb://AUTH"))
                .route(p -> p
                        .path(AppUtils.API_PREFIX + "/users/**")
                        .filters(f -> applyServiceFilters(f, "/users/(?<segment>.*)"))
                        .uri("lb://USERS"))
                .route(p -> p
                        .path(AppUtils.API_PREFIX + "/user-profiles/**")
                        .filters(f -> applyServiceFilters(f, "/user-profiles/(?<segment>.*)"))
                        .uri("lb://PROFILES"))
                .route(p -> p
                        .path(AppUtils.API_PREFIX + "/content/**")
                        .filters(f -> applyServiceFilters(f, "/content/(?<segment>.*)"))
                        .uri("lb://CONTENT"))
                .route(p -> p
                        .path(AppUtils.API_PREFIX + "/search/**")
                        .filters(f -> applyServiceFilters(f, "/search/(?<segment>.*)"))
                        .uri("lb://SEARCH"))
                .route(p -> p
                        .path(AppUtils.API_PREFIX + "/streaming/**")
                        .filters(f -> applyServiceFilters(f, "/streaming/(?<segment>.*)"))
                        .uri("lb://STREAMING"))
                .build();
    }

    private GatewayFilterSpec applyServiceFilters(GatewayFilterSpec filterSpec, String path) {
        return filterSpec
                .rewritePath(AppUtils.API_PREFIX + path, "/${segment}")
                .removeRequestHeader("Authorization")
                .addResponseHeader("X-Response-Time", new Date().toString());
    }

}
