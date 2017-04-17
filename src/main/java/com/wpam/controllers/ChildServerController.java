package com.wpam.controllers;

import com.wpam.exceptions.NoSuchBeaconException;
import com.wpam.services.ChildServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/childServer")
public class ChildServerController {
    private ChildServerService childServerService;

    @Autowired
    public ChildServerController(ChildServerService childServerService) {
        this.childServerService = childServerService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/childServer")
    public void registerChildServer(@RequestParam("ip") final String ip,
                                    @RequestParam("port") final String port) {
        childServerService.registerServer(ip, port);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/childServer/{ip}/{port}")
    public void deregisterChildServer(@PathVariable("ip") final String ip,
                                      @PathVariable("port") final String port) throws NoSuchBeaconException {
        childServerService.deregisterServer(ip, port);
    }
}