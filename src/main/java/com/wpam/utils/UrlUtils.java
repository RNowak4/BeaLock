package com.wpam.utils;

import com.wpam.domain.ChildServer;
import org.springframework.stereotype.Component;

@Component
public class UrlUtils {
    private final static String HTTPS = "https://";

    public String getHttpsUrl(final ChildServer childServer) {
        return HTTPS + childServer.getIp() + ":" + childServer.getPort();
    }
}