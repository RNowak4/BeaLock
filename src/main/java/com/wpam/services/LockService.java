package com.wpam.services;

import com.wpam.domain.Beacon;
import com.wpam.domain.ChildServer;
import com.wpam.domain.repositories.ChildServerRepository;
import com.wpam.utils.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LockService {
    private ChildServerRepository childServerRepository;
    private IpUtils ipUtils;
    private static RestTemplate template = new RestTemplate();

    @Autowired
    public LockService(ChildServerRepository childServerRepository, IpUtils ipUtils) {
        this.childServerRepository = childServerRepository;
        this.ipUtils = ipUtils;
    }

    public void setLockOnBeacon(final Beacon beacon) {
        final ExecutorService exec = Executors.newCachedThreadPool();
        final List<ChildServer> servers = childServerRepository.findAllByIsActive(true);

        for (final ChildServer childServer : servers) {
            exec.submit(() -> {
                final String ipAddress = ipUtils.getIpAddress(childServer);

//                template.postForEntity(ipAddress + "/" + beacon.getName());
            });
        }

        exec.shutdown();
    }

    public void deleteLockOnBeacon(final Beacon beacon) {
        final ExecutorService exec = Executors.newCachedThreadPool();
        final List<ChildServer> servers = childServerRepository.findAllByIsActive(true);

        for (final ChildServer childServer : servers) {
            exec.submit(() -> {
                final String ipAddress = ipUtils.getIpAddress(childServer);

                template.delete(ipAddress + "/" + beacon.getName());
            });
        }

        exec.shutdown();
    }
}