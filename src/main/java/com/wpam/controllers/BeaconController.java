package com.wpam.controllers;

import com.wpam.domain.Beacon;
import com.wpam.exceptions.BeaconAlreadyExistsException;
import com.wpam.exceptions.NoSuchBeaconException;
import com.wpam.exceptions.NoSuchUserException;
import com.wpam.exceptions.UserNotOwningBeaconException;
import com.wpam.services.BeaconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class BeaconController {
    private BeaconService beaconService;

    @Autowired
    public BeaconController(BeaconService beaconService) {
        this.beaconService = beaconService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/beacon")
    public List<String> getUserBeacons(final Principal principal) throws BeaconAlreadyExistsException {

        return beaconService.getUserBeacons(principal);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/beacon")
    public void addBeacon(@RequestParam("beaconName") final String beaconName,
                          final Principal principal) throws BeaconAlreadyExistsException {

        beaconService.addBeacon(beaconName, principal);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/beacon")
    public void removeBeacon(@RequestParam("beaconName") final String beaconName,
                             final Principal principal)
            throws BeaconAlreadyExistsException, UserNotOwningBeaconException, NoSuchBeaconException, NoSuchUserException {
        // TODO poprawic styl
        final Beacon beacon = new Beacon();
        beacon.setName(beaconName);

        beaconService.removeBeacon(beacon, principal);
    }
}