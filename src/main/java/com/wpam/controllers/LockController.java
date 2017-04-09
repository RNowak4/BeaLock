package com.wpam.controllers;

import com.wpam.domain.Beacon;
import com.wpam.services.BeaconService;
import com.wpam.services.LockService;
import com.wpam.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/lock")
public class LockController {
    private BeaconService beaconService;
    private LockService lockService;
    private UserService userService;

    @Autowired
    public LockController(BeaconService beaconService, LockService lockService, UserService userService) {
        this.beaconService = beaconService;
        this.lockService = lockService;
        this.userService = userService;
    }

    @RequestMapping(value = "/lock", method = RequestMethod.GET)
    public String getLock() {
        return "LOCK/aa";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/{beaconName}")
    public void setLock(@PathVariable(name = "beaconName") final String beaconName) {
        final Beacon beacon = beaconService.getBeaconByName(beaconName);

        if (beacon == null) {
            throw new RuntimeException("No such beacon!");
        }

        lockService.setLockOnBeacon(beacon);
    }
}