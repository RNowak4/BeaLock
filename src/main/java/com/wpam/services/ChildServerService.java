package com.wpam.services;

import com.wpam.domain.ChildServer;
import com.wpam.domain.repositories.ChildServerRepository;
import com.wpam.exceptions.NoSuchBeaconException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChildServerService {
    private ChildServerRepository childServerRepository;

    @Autowired
    public ChildServerService(ChildServerRepository childServerRepository) {
        this.childServerRepository = childServerRepository;
    }

    public List<ChildServer> getAllServers() {
        return childServerRepository.findAll();
    }

    public void registerServer(final String ip, final String port) {
        final Optional<ChildServer> foundServer = childServerRepository.findOneByIpAndPort(ip, port);

        if (foundServer.isPresent()) {
            foundServer.get().setActive(true);
            childServerRepository.save(foundServer.get());
        } else {
            final ChildServer newChildServer = new ChildServer();
            newChildServer.setIp(ip);
            newChildServer.setPort(port);
            newChildServer.setActive(true);
            childServerRepository.save(newChildServer);
        }
    }

    public void deregisterServer(final String ip, final String port) throws NoSuchBeaconException {
        final Optional<ChildServer> foundServer = childServerRepository.findOneByIpAndPort(ip, port);

        if (foundServer.isPresent()) {
            foundServer.get().setActive(false);
            childServerRepository.save(foundServer.get());
        } else {
            throw new NoSuchBeaconException();
        }
    }
}