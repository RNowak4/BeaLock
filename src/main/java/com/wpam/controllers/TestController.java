package com.wpam.controllers;

import com.wpam.domain.Beacon;
import com.wpam.domain.BeaconStatus;
import com.wpam.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController(value = "/user/test")
public class TestController {
    private RestTemplate template = new RestTemplate();

    @RequestMapping(method = RequestMethod.GET, value = "/user/test")
    public List<Beacon> test() {
        List<Beacon> beaconList = new ArrayList<>();
        Beacon beacona = new Beacon();
        User user = new User();
        beacona.setName("asd");
        beacona.setStatus(BeaconStatus.NOT_LOCKED);
        beacona.setUser(user);
        beaconList.add(beacona);
        return beaconList;
    }

    @Scheduled(fixedRate = 5000)
    public void testa() {
        final ResponseEntity<String> result = template.getForEntity("https://localhost:9095/test", String.class);

        System.out.println(result.getBody());
    }
}
