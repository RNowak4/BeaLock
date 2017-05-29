package com.wpam.services;

import com.wpam.domain.Beacon;
import com.wpam.domain.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    private BeaconService beaconService;
    private UserService userService;

    @Value("${server.google.token}")
    private String serverToken;

    private final String GOOGLE_API_URL = "https://gcm-http.googleapis.com/gcm/send\n";

    @Autowired
    public NotificationService(BeaconService beaconService, UserService userService) {
        this.beaconService = beaconService;
        this.userService = userService;
    }

    private void sendNotificationToApp(final String appToken) throws JSONException {
        final RestTemplate template = new RestTemplate();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final JSONObject requestData = new JSONObject();
        final JSONObject data = new JSONObject();
        requestData.put("data", data);
        requestData.put("to", appToken);

        final HttpEntity<String> request = new HttpEntity<>(data.toString(), headers);
        template.postForEntity(GOOGLE_API_URL, request, Void.class);
    }

    // TODO dodac Preauthorize
    public void notifyApp(final String beaconId) throws JSONException {
        final Beacon beacon = beaconService.getBeaconByName(beaconId);
        final User user = beacon.getUser();
        final String appToken = user.getToken();

        sendNotificationToApp(appToken);
    }
}
