package com.workshopapp.workshopservice.controller;

import com.workshopapp.workshopservice.model.Order;
import com.workshopapp.workshopservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        // Konwersja List<Integer> na List<Long>
        List<Long> serviceIds = ((List<Integer>) paymentData.get("serviceIds"))
                .stream()
                .map(Integer::longValue)
                .collect(Collectors.toList());

        LocalDateTime appointmentDate = LocalDateTime.parse((String) paymentData.get("appointmentDate"));
        String paymentMethod = (String) paymentData.get("paymentMethod");

        Order order = orderService.createOrder(username, serviceIds, appointmentDate, paymentMethod);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Płatność przetworzona pomyślnie");
        response.put("orderId", order.getId().toString());

        return response;
    }
}
