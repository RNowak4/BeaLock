package com.wpam.config;

import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public BrokerService broker() throws Exception {
        final BrokerService broker = new BrokerService();
        broker.addConnector("tcp://localhost:61616");
        broker.addConnector("vm://localhost");
        broker.setPersistent(false);

        return broker;
    }
}
