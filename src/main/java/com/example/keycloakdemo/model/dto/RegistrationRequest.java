package com.example.keycloakdemo.model.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    private String username;
    private String password;
    private String role;
}