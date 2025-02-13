package com.workshopapp.workshopservice.controller;

import com.workshopapp.workshopservice.model.WorkshopService;
import com.workshopapp.workshopservice.service.ServiceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceController {
    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public List<WorkshopService> getAllServices() {
        return serviceService.getAllServices();
    }
}
