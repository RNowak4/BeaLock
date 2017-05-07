package com.wpam.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController(value = "/test")
public class TestController {
    private RestTemplate template = new RestTemplate();

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public String test() {
        return "Test OK!";
    }

    @Scheduled(fixedRate = 5000)
    public void testa() {
        final ResponseEntity<String> result = template.getForEntity("https://localhost:9095/test", String.class);

        System.out.println(result.getBody());
    }
}
