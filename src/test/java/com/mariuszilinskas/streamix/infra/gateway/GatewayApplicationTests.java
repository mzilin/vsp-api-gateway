package com.mariuszilinskas.streamix.infra.gateway;

import com.mariuszilinskas.streamix.infra.gateway.config.CorsConfig;
import com.mariuszilinskas.streamix.infra.gateway.config.GatewayConfig;
import com.mariuszilinskas.streamix.infra.gateway.config.SecurityConfig;
import com.mariuszilinskas.streamix.infra.gateway.filter.AuthenticationFilter;
import com.mariuszilinskas.streamix.infra.gateway.service.JwtServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GatewayApplicationTests {

    @Autowired
    private CorsConfig corsConfig;

    @Autowired
    private GatewayConfig gatewayConfig;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Autowired
    private JwtServiceImpl jwtService;

    @Test
    void contextLoads() {
    }

    // -----------------------------------------------------

    @Test
    void corsConfigBeanLoads() {
        assertNotNull(corsConfig, "Cors Config should have been auto-wired by Spring Context");
    }

    @Test
    void gatewayConfigBeanLoads() {
        assertNotNull(gatewayConfig, "Gateway Config should have been auto-wired by Spring Context");
    }

    @Test
    void securityConfigBeanLoads() {
        assertNotNull(securityConfig, "Security Config should have been auto-wired by Spring Context");
    }

    @Test
    void authenticationFilterBeanLoads() {
        assertNotNull(authenticationFilter, "Authentication Filter should have been auto-wired by Spring Context");
    }

    @Test
    void jwtServiceBeanLoads() {
        assertNotNull(jwtService, "Jwt Service should have been auto-wired by Spring Context");
    }

}
