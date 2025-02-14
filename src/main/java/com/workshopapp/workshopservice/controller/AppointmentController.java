package com.workshopapp.workshopservice.controller;

import com.workshopapp.workshopservice.model.Appointment;
import com.workshopapp.workshopservice.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/available")
    public List<Appointment> getAvailableAppointments() {
        System.out.println("📌 Pobieranie dostępnych terminów...");
        return appointmentService.getAvailableAppointments();
    }
}
