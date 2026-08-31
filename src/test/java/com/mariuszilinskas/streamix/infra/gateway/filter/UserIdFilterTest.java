package com.mariuszilinskas.streamix.infra.gateway.filter;

import com.mariuszilinskas.streamix.infra.gateway.util.AppUtils;
import com.mariuszilinskas.streamix.infra.gateway.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserIdFilterTest {

    @Mock
    private WebFilterChain chain;

    private final UserIdFilter filter = new UserIdFilter();

    // ------------------------------------

    @Test
    void testFilter_PathContainsUserId_SubstitutesWithRealId() {
        // Arrange
        MockServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/users/" + AppUtils.PATH_USER_ID + "/profile").build()
        );

        ArgumentCaptor<ServerWebExchange> captor = ArgumentCaptor.forClass(ServerWebExchange.class);
        when(chain.filter(captor.capture())).thenReturn(Mono.empty());

        var authentication = new UsernamePasswordAuthenticationToken(
                TestUtils.userId.toString(), null, List.of()
        );

        // Act
        filter.filter(exchange, chain)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication))
                .block();

        // Assert
        String expectedPath = "/users/" + TestUtils.userId + "/profile";
        assertEquals(expectedPath, captor.getValue().getRequest().getURI().getPath());
    }

    // ------------------------------------

    @Test
    void testFilter_PathWithoutUserId_PassesThrough() {
        // Arrange
        MockServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/users/profile").build()
        );
        when(chain.filter(exchange)).thenReturn(Mono.empty());

        // Act
        filter.filter(exchange, chain).block();

        // Assert
        verify(chain).filter(exchange);
    }

}
