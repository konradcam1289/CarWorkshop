package com.workshopapp.workshopservice.dto;

import com.workshopapp.workshopservice.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private Set<Role> roles;

    // Konstruktor domyślny wymagany przez Spring
    public RegisterRequest() {}

    // Konstruktor z wszystkimi polami
    public RegisterRequest(String username, String email, String password, String firstName,
                           String lastName, String phoneNumber, String address, Set<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.roles = roles;
    }

    // 🔹 Ręczne dodanie getterów, jeśli Lombok nie działa poprawnie
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public Set<Role> getRoles() { return roles; }
}
