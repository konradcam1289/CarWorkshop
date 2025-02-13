package com.workshopapp.workshopservice.service;

import com.workshopapp.workshopservice.dto.RegisterRequest;
import com.workshopapp.workshopservice.model.User;
import com.workshopapp.workshopservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User registerUser(RegisterRequest request) {
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()), // 🔹 Tutaj pobieramy poprawnie hasło
                request.getFirstName(),
                request.getLastName(),
                request.getPhoneNumber(),
                request.getAddress(),
                request.getRoles()
        );

        return userRepository.save(user);
    }
}
