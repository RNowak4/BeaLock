package com.wpam.services;

import com.wpam.domain.Beacon;
import com.wpam.domain.ChildServer;
import com.wpam.domain.repositories.ChildServerRepository;
import com.wpam.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LockService {
    private ChildServerRepository childServerRepository;
    private UrlUtils urlUtils;
    private static RestTemplate template = new RestTemplate();

    @Autowired
    public LockService(ChildServerRepository childServerRepository, UrlUtils urlUtils) {
        this.childServerRepository = childServerRepository;
        this.urlUtils = urlUtils;
    }

    public void setLockOnBeacon(final Beacon beacon) {
        final ExecutorService exec = Executors.newCachedThreadPool();
        final List<ChildServer> servers = childServerRepository.findAllByIsActive(true);

        for (final ChildServer childServer : servers) {
            exec.submit(() -> {
                final String ipAddress = urlUtils.getHttpsUrl(childServer);

                template.postForEntity(ipAddress + "/lock/" + beacon.getName(), null, Void.class);
            });
        }

        exec.shutdown();
    }

    public void deleteLockOnBeacon(final Beacon beacon) {
        final ExecutorService exec = Executors.newCachedThreadPool();
        final List<ChildServer> servers = childServerRepository.findAllByIsActive(true);

        for (final ChildServer childServer : servers) {
            exec.submit(() -> {
                final String ipAddress = urlUtils.getHttpsUrl(childServer);

                template.delete(ipAddress + "/lock/" + beacon.getName());
            });
        }

        exec.shutdown();
    }
}