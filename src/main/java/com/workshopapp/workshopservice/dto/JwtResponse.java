package com.workshopapp.workshopservice.dto;

public class JwtResponse {
    private String token;
    private String role; // 🔹 Dodajemy pole roli

    public JwtResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public String getRole() {
        return role;
    }
}
