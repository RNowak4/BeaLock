package com.wpam.services;

import com.wpam.domain.Beacon;
import com.wpam.domain.BeaconStatus;
import com.wpam.domain.User;
import com.wpam.domain.repositories.BeaconRepository;
import com.wpam.exceptions.BeaconAlreadyExistsException;
import com.wpam.exceptions.NoSuchBeaconException;
import com.wpam.exceptions.NoSuchUserException;
import com.wpam.exceptions.UserNotOwningBeaconException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BeaconService {
    private BeaconRepository beaconRepository;
    private UserService userService;

    @Autowired
    public BeaconService(BeaconRepository beaconRepository, UserService userService) {
        this.beaconRepository = beaconRepository;
        this.userService = userService;
    }

    public Beacon getBeaconByName(final String name) {
        return beaconRepository.findBeaconByName(name);
    }

    public void addBeacon(final String beaconName, final Principal principal) throws BeaconAlreadyExistsException, NoSuchUserException {
        final Beacon beacon = new Beacon();
        beacon.setName(beaconName);

        if (beaconAlreadyExists(beacon)) {
            throw new BeaconAlreadyExistsException();
        }

        final User user = userService.getUserByLogin(principal.getName())
                .map(foundUser -> foundUser)
                .orElseThrow(NoSuchUserException::new);
        beacon.setStatus(BeaconStatus.NOT_LOCKED);
        beacon.setUser(user);

        beaconRepository.save(beacon);
    }

    public void removeBeacon(final Beacon beacon, final Principal principal)
            throws BeaconAlreadyExistsException, NoSuchBeaconException, UserNotOwningBeaconException, NoSuchUserException {
        if (!beaconAlreadyExists(beacon)) {
            throw new NoSuchBeaconException();
        }

        if (!userOwnsBeacon(beacon, principal)) {
            throw new UserNotOwningBeaconException();
        }

        final Beacon beaconToDelete = beaconRepository.findBeaconByName(beacon.getName());
        beaconRepository.delete(beaconToDelete);
    }

    private boolean userOwnsBeacon(final Beacon beacon, final Principal principal) throws NoSuchUserException {
        final Optional<User> foundUser = userService.getUserByLogin(principal.getName());

        return foundUser.map(user -> beacon.getUser().equals(user))
                .orElseThrow(NoSuchUserException::new);
    }

    private boolean beaconAlreadyExists(final Beacon beacon) {
        return beacon != null && beaconRepository.findBeaconByName(beacon.getName()) != null;
    }

    public List<String> getUserBeacons(final Principal principal) {
        final Optional<User> user = userService.getUserByLogin(principal.getName());

        return user.map(foundUser -> beaconRepository.findBeaconsByUser(foundUser).stream()
                .map(Beacon::getName)
                .collect(Collectors.toList()))
                .orElse(null);
    }
}