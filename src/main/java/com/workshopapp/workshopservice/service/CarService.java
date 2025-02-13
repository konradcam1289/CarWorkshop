package com.workshopapp.workshopservice.service;

import com.workshopapp.workshopservice.model.Car;
import com.workshopapp.workshopservice.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public Car updateCar(Long id, Car car) {
        Car existingCar = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
        existingCar.setMake(car.getMake());
        existingCar.setModel(car.getModel());
        existingCar.setYear(car.getYear());
        return carRepository.save(existingCar);
    }

}
