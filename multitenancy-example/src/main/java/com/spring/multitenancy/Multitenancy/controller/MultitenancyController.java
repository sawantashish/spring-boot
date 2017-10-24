package com.spring.multitenancy.Multitenancy.controller;

import com.spring.multitenancy.Multitenancy.dto.ClientDTO;
import com.spring.multitenancy.Multitenancy.service.MultitenancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MultitenancyController {

    @Autowired
    MultitenancyService multitenancyService;

    @GetMapping("/schema")
    public ClientDTO schema() {
        return multitenancyService.getClientInfo();
    }
}
