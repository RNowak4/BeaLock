package com.wpam.controllers;

import com.wpam.services.NotificationService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/notify")
public class NotificationsController {
    private NotificationService notificationService;

    @Autowired
    public NotificationsController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/notify")
    public void notifyClient(@RequestParam("beaconId") final String beaconId) throws JSONException {
        notificationService.notifyApp(beaconId);
    }
}