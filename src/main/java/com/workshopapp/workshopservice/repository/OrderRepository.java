package com.workshopapp.workshopservice.repository;

import com.workshopapp.workshopservice.model.Order;
import com.workshopapp.workshopservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    List<Order> findByUser_UsernameAndReservedIsTrue(String username);
}
