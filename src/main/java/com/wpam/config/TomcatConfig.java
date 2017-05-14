package com.wpam.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class TomcatConfig {
    public static final Set<String> USER_URLS = new HashSet<>(Arrays.asList("/user", "/login", "/logout", "/beacon", "/lock"));

    @Value("${http.port}")
    private int httpPort;

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                TomcatEmbeddedServletContainerFactory containerFactory =
                        (TomcatEmbeddedServletContainerFactory) container;

                Connector connector = new Connector(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL);
                connector.setPort(httpPort);
                containerFactory.addAdditionalTomcatConnectors(connector);
            }
        };
    }

}
