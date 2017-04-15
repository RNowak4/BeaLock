package com.wpam.services;

import com.wpam.domain.Beacon;
import com.wpam.domain.ChildServer;
import com.wpam.domain.repositories.ChildServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LockService {
    private ChildServerRepository childServerRepository;

    @Autowired
    public LockService(ChildServerRepository childServerRepository) {
        this.childServerRepository = childServerRepository;
    }

    public void setLockOnBeacon(final Beacon beacon) {
        final List<ChildServer> servers = childServerRepository.findAll();

        for(final ChildServer childServer : servers) {
            // TODO wysylanie zapytan w osobnej metodzie
        }
    }
}
