package com.mariuszilinskas.streamix.infra.gateway.filter;

import com.mariuszilinskas.streamix.infra.gateway.service.JwtServiceImpl;
import com.mariuszilinskas.streamix.infra.gateway.util.AppUtils;
import com.mariuszilinskas.streamix.infra.gateway.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserIdFilterTest {

    @Mock
    private JwtServiceImpl jwtService;

    @Mock
    private WebFilterChain chain;

    @InjectMocks
    private UserIdFilter filter;

    // ------------------------------------

    @Test
    void testFilter_PathContainsUserId_SubstitutesWithRealId() {
        // Arrange
        MockServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/users/" + AppUtils.PATH_USER_ID + "/profile").build()
        );
        when(jwtService.extractUserId(exchange)).thenReturn(TestUtils.userId);

        ArgumentCaptor<ServerWebExchange> captor = ArgumentCaptor.forClass(ServerWebExchange.class);
        when(chain.filter(captor.capture())).thenReturn(Mono.empty());

        // Act
        filter.filter(exchange, chain).block();

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
        verify(jwtService, never()).extractUserId(any());
        verify(chain).filter(exchange);
    }

}
