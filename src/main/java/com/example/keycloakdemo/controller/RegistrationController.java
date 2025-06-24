package com.example.keycloakdemo.controller;

import com.example.keycloakdemo.model.dto.RegistrationRequest;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegistrationRequest request) {
        Keycloak keycloak = Keycloak.getInstance(
                serverUrl,
                "master",
                "admin",
                "admin",
                "admin-cli");

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getPassword());
        credential.setTemporary(false);

        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        Response response = usersResource.create(user);

        if (response.getStatus() == 201) {
            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

            usersResource.get(userId).roles().realmLevel()
                    .add(Collections.singletonList(realmResource.roles().get(request.getRole()).toRepresentation()));

            return "User registered successfully";
        }

        return response.getStatus() + " ";
    }
}

