package com.workshopapp.workshopservice.service;

import com.workshopapp.workshopservice.model.WorkshopService;
import com.workshopapp.workshopservice.repository.ServiceRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServiceService {
    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<WorkshopService> getAllServices() {
        List<WorkshopService> services = serviceRepository.findAll();
        System.out.println("📢 Usługi w bazie: " + services.size()); // Dodaj log
        return services;
    }
}

