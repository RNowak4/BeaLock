package com.wpam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BealockApplication {
    private final static String KEYSTORE_LOCATION = "/home/pi/keys/server.jks";
    private final static String KEYSTORE_PASSWORD = "s3cr3t";

    static {
        System.setProperty("javax.net.ssl.trustStore", KEYSTORE_LOCATION);
        System.setProperty("javax.net.ssl.trustStorePassword", KEYSTORE_PASSWORD);
        System.setProperty("javax.net.ssl.keyStore", KEYSTORE_LOCATION);
        System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);

        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                (hostname, sslSession) -> hostname.equals("localhost"));
    }

    public static void main(String[] args) {
        SpringApplication.run(BealockApplication.class, args);
    }
}
