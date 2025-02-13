package com.workshopapp.workshopservice.controller;

import com.workshopapp.workshopservice.model.Order;
import com.workshopapp.workshopservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final OrderService orderService;

    public PaymentController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/pay")
    public Map<String, String> processPayment(@RequestBody Map<String, Object> paymentData) {
        String username = (String) paymentData.get("username");
        List<Integer> serviceIds = (List<Integer>) paymentData.get("serviceIds");
        LocalDateTime appointmentDate = LocalDateTime.parse((String) paymentData.get("appointmentDate"));
        String paymentMethod = (String) paymentData.get("paymentMethod");

        Order order = orderService.createOrder(username, serviceIds, appointmentDate, paymentMethod);

        if (paymentMethod.equalsIgnoreCase("cash")) {
            return Map.of("message", "Usługa została zarezerwowana na miejscu!");
        } else {
            return Map.of("paymentUrl", "https://secure.payu.com/order");
        }
    }
}
