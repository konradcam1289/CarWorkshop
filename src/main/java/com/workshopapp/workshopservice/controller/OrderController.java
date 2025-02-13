package com.workshopapp.workshopservice.controller;

import com.workshopapp.workshopservice.model.Order;
import com.workshopapp.workshopservice.service.OrderService;
import com.workshopapp.workshopservice.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final JwtService jwtService;

    public OrderController(OrderService orderService, JwtService jwtService) {
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> orderData) {
        try {
            String username = (String) orderData.get("username");
            List<?> serviceIdObjects = (List<?>) orderData.get("serviceIds");
            String appointmentDateStr = (String) orderData.get("appointmentDate");
            String paymentMethod = (String) orderData.get("paymentMethod");

            if (username == null || serviceIdObjects == null || appointmentDateStr == null || paymentMethod == null) {
                return ResponseEntity.badRequest().body("Błąd: Brak wymaganych parametrów");
            }

            List<Long> serviceIds = serviceIdObjects.stream()
                    .map(o -> ((Number) o).longValue())
                    .collect(Collectors.toList());

            if (appointmentDateStr.endsWith("Z")) {
                appointmentDateStr = appointmentDateStr.substring(0, appointmentDateStr.length() - 1);
            }
            LocalDateTime appointmentDate = LocalDateTime.parse(appointmentDateStr);

            Order savedOrder = orderService.createOrder(username, serviceIds, appointmentDate, paymentMethod);
            return ResponseEntity.ok(savedOrder);

        } catch (Exception e) {
            return ResponseEntity.status(400).body("Błąd przetwarzania zamówienia: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    public ResponseEntity<List<Order>> getUserOrders(@RequestHeader("Authorization") String token) {
        try {
            String username = jwtService.extractUsername(token.replace("Bearer ", ""));
            if (username == null) {
                return ResponseEntity.status(401).body(null);
            }

            List<Order> orders = orderService.getUserOrders(username);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
