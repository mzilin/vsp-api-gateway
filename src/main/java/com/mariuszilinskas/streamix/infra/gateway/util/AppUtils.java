package com.mariuszilinskas.streamix.infra.gateway.util;

public final class AppUtils {

    private AppUtils() {}

    public static final String API_PREFIX        = "/api/v1";
    public static final String ACCESS_TOKEN_NAME = "vsp_access";
    public static final String PATH_USER_ID      = "_USER_ID_";

    public static final String CORRELATION_HEADER = "X-Correlation-Id";
    public static final String USER_ID_HEADER     = "X-User-Id";

    public static final String MDC_CONTEXT_KEY    = "slf4j.mdc";
    public static final String MDC_CORRELATION_ID = "correlation_id";
    public static final String MDC_USER_ID        = "user_id";
    public static final String MDC_SERVICE        = "service";
    public static final String MDC_ENVIRONMENT    = "environment";

}
