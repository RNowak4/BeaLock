package com.wpam.utils;

import com.wpam.domain.ChildServer;
import org.springframework.stereotype.Component;

@Component
public class IpUtils {
    private final static String HTTPS = "https://";

    public String getIpAddress(final ChildServer childServer) {
        return HTTPS + childServer.getIp() + ":" + childServer.getPort();
    }
}