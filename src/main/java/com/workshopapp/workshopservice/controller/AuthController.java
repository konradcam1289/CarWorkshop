package com.workshopapp.workshopservice.controller;

import com.workshopapp.workshopservice.dto.JwtResponse;
import com.workshopapp.workshopservice.dto.LoginRequest;
import com.workshopapp.workshopservice.dto.RegisterRequest;
import com.workshopapp.workshopservice.model.User;
import com.workshopapp.workshopservice.repository.UserRepository;
import com.workshopapp.workshopservice.security.JwtService;
import com.workshopapp.workshopservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService, UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request);
        String role = user.getPrimaryRole();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(authentication);

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("username", user.getUsername());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateToken(authentication);

            String role = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"))
                    .getPrimaryRole();

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("username", request.getUsername());
            response.put("role", role);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Błędne dane logowania");
        }
    }

    // 🆕 ✅ Nowy endpoint, aby frontend mógł pobrać username na podstawie tokena
    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).body("Brak autoryzacji");
        }

        String username = authentication.getName();
        return ResponseEntity.ok(Map.of("username", username));
    }
}
