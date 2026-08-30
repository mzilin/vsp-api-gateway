package com.mariuszilinskas.streamix.infra.gateway.config;

import com.mariuszilinskas.streamix.infra.gateway.enums.UserRole;
import com.mariuszilinskas.streamix.infra.gateway.filter.AuthenticationFilter;
import com.mariuszilinskas.streamix.infra.gateway.filter.UserIdFilter;
import com.mariuszilinskas.streamix.infra.gateway.util.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;
    private final AuthenticationFilter authenticationFilter;
    private final UserIdFilter userIdFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http.cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorize -> authorize
                        .pathMatchers(HttpMethod.GET, AppUtils.getGetPublicPaths()).permitAll()
                        .pathMatchers(HttpMethod.POST, AppUtils.getPostPublicPaths()).permitAll()
                        .pathMatchers(AppUtils.getAnyMethodPublicPaths()).permitAll()
                        .pathMatchers(AppUtils.getAdminAccessPaths()).hasRole(UserRole.ADMIN.name())
                        .anyExchange().authenticated()
                )
                .addFilterAt(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .addFilterAfter(userIdFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

}
