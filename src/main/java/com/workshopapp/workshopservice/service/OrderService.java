package com.workshopapp.workshopservice.service;

import com.workshopapp.workshopservice.model.*;
import com.workshopapp.workshopservice.repository.OrderRepository;
import com.workshopapp.workshopservice.repository.UserRepository;
import com.workshopapp.workshopservice.repository.ServiceRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ServiceRepository serviceRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
    }

    public Order createOrder(String username, List<Integer> serviceIds, LocalDateTime appointmentDate, String paymentMethod) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Long> longServiceIds = serviceIds.stream()
                .map(Integer::longValue)
                .collect(Collectors.toList());

        List<WorkshopService> services = serviceRepository.findAllById(longServiceIds);

        Order order = new Order();
        order.setUser(user);
        order.setServices(services);
        order.setAppointmentDate(appointmentDate);
        order.setPaymentMethod(paymentMethod);
        order.setStatus(OrderStatus.PENDING);

        if (paymentMethod.equalsIgnoreCase("cash")) {
            order.setReserved(true);
            services.forEach(service -> service.setAvailable(false)); // Oznacz jako niedostępne
            serviceRepository.saveAll(services);
        }

        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie znaleziony"));
        return orderRepository.findByUser(user);
    }

    public List<Order> getReservedOrders(String username) {
        return orderRepository.findByUser_UsernameAndReservedIsTrue(username);
    }

    public void updateOrderStatus(Long orderId, OrderStatus status) {
        Optional<Order> order = orderRepository.findById(orderId);
        order.ifPresent(o -> {
            o.setStatus(status);
            orderRepository.save(o);
        });
    }
}
