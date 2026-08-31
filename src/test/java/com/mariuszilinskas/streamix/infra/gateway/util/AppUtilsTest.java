package com.mariuszilinskas.streamix.infra.gateway.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppUtilsTest {

    @Test
    void testConstants_AreCorrectlyDefined() {
        assertEquals("/api/v1", AppUtils.API_PREFIX);
        assertEquals("vsp_access", AppUtils.ACCESS_TOKEN_NAME);
        assertEquals("_USER_ID_", AppUtils.PATH_USER_ID);
        assertEquals("X-Correlation-Id", AppUtils.CORRELATION_HEADER);
        assertEquals("X-User-Id", AppUtils.USER_ID_HEADER);
        assertEquals("slf4j.mdc", AppUtils.MDC_CONTEXT_KEY);
        assertEquals("correlation_id", AppUtils.MDC_CORRELATION_ID);
        assertEquals("user_id", AppUtils.MDC_USER_ID);
        assertEquals("service", AppUtils.MDC_SERVICE);
        assertEquals("environment", AppUtils.MDC_ENVIRONMENT);
    }

}
