package com.wpam.services;

import com.wpam.domain.ChildServer;
import com.wpam.domain.repositories.ChildServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}