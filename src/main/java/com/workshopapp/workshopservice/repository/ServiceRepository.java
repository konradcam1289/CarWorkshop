package com.workshopapp.workshopservice.repository;

import com.workshopapp.workshopservice.model.WorkshopService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<WorkshopService, Long> {
}
