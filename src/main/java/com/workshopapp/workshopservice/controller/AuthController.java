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

        // Pobranie roli użytkownika BEZ dodawania podwójnego `ROLE_`
        String role = user.getPrimaryRole();

        // Automatyczne logowanie po rejestracji
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwt, role));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateToken(authentication);

            // ✅ Pobieramy rolę BEZPOŚREDNIO Z BAZY, aby uniknąć podwójnego `ROLE_`
            String role = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"))
                    .getPrimaryRole();

            System.out.println("DEBUG: Rola użytkownika -> " + role); // 🔹 Logowanie do konsoli

            return ResponseEntity.ok(new JwtResponse(jwt, role));
        } catch (Exception e) {
            System.err.println("Błąd logowania: " + e.getMessage());
            return ResponseEntity.status(401).body("Błędne dane logowania");
        }
    }
}
