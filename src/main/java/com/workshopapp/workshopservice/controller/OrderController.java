package com.workshopapp.workshopservice.controller;

import com.workshopapp.workshopservice.model.Order;
import com.workshopapp.workshopservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/reserved/{username}")
    public List<Order> getReservedOrders(@PathVariable String username) {
        return orderService.getReservedOrders(username);
    }
}
