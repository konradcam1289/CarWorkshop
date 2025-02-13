package com.workshopapp.workshopservice.repository;

import com.workshopapp.workshopservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
