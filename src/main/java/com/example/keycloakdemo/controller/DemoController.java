
package com.example.keycloakdemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "This is a public endpoint accessible by anyone.";
    }

    @GetMapping("/check")
    public String whoami(Authentication authentication) {
        return "Authorities: " + authentication.getAuthorities().toString();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint() {
        return "This is an ADMIN endpoint. Only users with ADMIN role can access this.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userEndpoint() {
        return "This is a USER endpoint. Only users with USER role can access this.";
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorEndpoint() {
        return "This is a MODERATOR endpoint. Only users with MODERATOR role can access this.";
    }
}