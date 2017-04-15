package com.wpam.services;

import com.wpam.domain.Beacon;
import com.wpam.domain.BeaconStatus;
import com.wpam.domain.ChildServer;
import com.wpam.domain.User;
import com.wpam.domain.repositories.BeaconRepository;
import com.wpam.exceptions.BeaconAlreadyExistsException;
import com.wpam.exceptions.NoSuchBeaconException;
import com.wpam.exceptions.UserNotOwningBeaconException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class BeaconService {
    private BeaconRepository beaconRepository;
    private ChildServerService childServerService;
    private UserService userService;

    @Autowired
    public BeaconService(BeaconRepository beaconRepository, ChildServerService childServerService, UserService userService) {
        this.beaconRepository = beaconRepository;
        this.childServerService = childServerService;
        this.userService = userService;
    }

    public Beacon getBeaconByName(final String name) {
        return beaconRepository.findBeaconByName(name);
    }

    public void addBeacon(final String beaconName, final Principal principal) throws BeaconAlreadyExistsException {
        final Beacon beacon = beaconRepository.findBeaconByName(beaconName);

        if (beaconAlreadyExists(beacon)) {
            throw new BeaconAlreadyExistsException();
        }

        final User user = userService.getUserByLogin(principal.getName()).get();
        beacon.setStatus(BeaconStatus.NOT_LOCKED);
        beacon.setUser(user);

        beaconRepository.save(beacon);
    }

    public void removeBeacon(final Beacon beacon, final Principal principal)
            throws BeaconAlreadyExistsException, NoSuchBeaconException, UserNotOwningBeaconException {
        if (!beaconAlreadyExists(beacon)) {
            throw new NoSuchBeaconException();
        }

        if (!userOwnsBeacon(beacon, principal)) {
            throw new UserNotOwningBeaconException();
        }

        final Beacon beaconToDelete = beaconRepository.findBeaconByName(beacon.getName());
        beaconRepository.delete(beaconToDelete);
    }

    private boolean userOwnsBeacon(final Beacon beacon, final Principal principal) {
        final User user = userService.getUserByLogin(principal.getName()).get();

        return beacon.getUser().equals(user);
    }

    private boolean beaconAlreadyExists(final Beacon beacon) {
        return beacon != null && beaconRepository.findBeaconByName(beacon.getName()) != null;
    }

    public void lockBeacon(final String beaconName, final Principal principal) {
        final List<ChildServer> childServers = childServerService.getAllServers();

        for (final ChildServer childServer : childServers) {
            // TODO make requests
        }
    }
}