package com.mariuszilinskas.streamix.infra.gateway.service;

import com.mariuszilinskas.streamix.infra.gateway.dto.JwtPayload;
import org.springframework.web.server.ServerWebExchange;

import java.util.Optional;
import java.util.UUID;

public interface JwtService {

    Optional<JwtPayload> extractPayload(ServerWebExchange exchange);

    String extractAccessToken(ServerWebExchange exchange);

    UUID extractUserId(ServerWebExchange exchange);

}
