package com.workshopapp.workshopservice.service;

import com.workshopapp.workshopservice.model.Appointment;
import com.workshopapp.workshopservice.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public List<Appointment> getAvailableAppointments() {
        return appointmentRepository.findByAvailableTrue();
    }
}
