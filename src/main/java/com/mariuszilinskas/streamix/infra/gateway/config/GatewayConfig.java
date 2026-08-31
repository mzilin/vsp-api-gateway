package com.mariuszilinskas.streamix.infra.gateway.config;

import com.mariuszilinskas.streamix.infra.gateway.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
@RequiredArgsConstructor
public class GatewayConfig {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String RESPONSE_TIME_HEADER = "X-Response-Time";

    @Value("${app.frontendBaseUrl}")
    private String frontendBaseUrl;

    private final AppProperties appProps;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        var routes = builder.routes();

        registerRootRoute(routes);

        appProps.routes()
                .forEach(route -> registerServiceRoute(routes, route));

        return routes.build();
    }

    private void registerRootRoute(RouteLocatorBuilder.Builder routes) {
        routes.route(route -> route
                .path("/")
                .filters(filters -> filters.redirect(302, frontendBaseUrl))
                .uri(frontendBaseUrl));
    }

    private void registerServiceRoute(RouteLocatorBuilder.Builder routes, AppProperties.RouteDefinition route) {
        String prefix = AppUtils.API_PREFIX + "/" + route.path();
        String pathPattern = prefix + "/(?<segment>.*)";
        String apiKey = getClusterApiKey(route.cluster());
        String serviceUri = "lb://" + route.cluster() + "-" + route.service();

        routes.route(builder -> builder
                .path(prefix + "/**")
                .filters(filters -> applyServiceFilters(filters, pathPattern, apiKey))
                .uri(serviceUri));
    }

    private String getClusterApiKey(String cluster) {
        String apiKey = appProps.clusterKeys().get(cluster);

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("Missing API key for cluster: " + cluster);
        }

        return apiKey;
    }

    private GatewayFilterSpec applyServiceFilters(GatewayFilterSpec filters, String pathPattern, String apiKey) {
        return filters
                .rewritePath(pathPattern, "/${segment}")
                .removeRequestHeader(AUTHORIZATION_HEADER)
                .addRequestHeader(AUTHORIZATION_HEADER, apiKey)
                .filter(responseTimeFilter());
    }

    private GatewayFilter responseTimeFilter() {
        return (exchange, chain) -> {
            long startTime = System.nanoTime();

            exchange.getResponse().beforeCommit(() -> {
                long duration = System.nanoTime() - startTime;
                long durationMillis = duration / 1_000_000;

                exchange.getResponse()
                        .getHeaders()
                        .set(RESPONSE_TIME_HEADER, durationMillis + "ms");

                return Mono.empty();
            });

            return chain.filter(exchange);
        };
    }

}
