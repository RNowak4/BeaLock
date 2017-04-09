package com.wpam.services;

import com.wpam.domain.Beacon;
import com.wpam.domain.repositories.BeaconRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeaconService {
    private BeaconRepository beaconRepository;

    @Autowired
    public BeaconService(BeaconRepository beaconRepository) {
        this.beaconRepository = beaconRepository;
    }

    public Beacon getBeaconByName(final String name) {
        return beaconRepository.findBeaconByName(name);
    }
}
