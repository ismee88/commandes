package com.polytech.commandes.controller;

import com.polytech.commandes.dto.AuthResponse;
import com.polytech.commandes.dto.LoginRequest;
import com.polytech.commandes.dto.RegisterRequest;
import com.polytech.commandes.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Authentification",
        description = "Gestion de l'inscription et de la connexion des utilisateurs"
)
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "Inscription utilisateur",
            description = "Crée un nouveau compte utilisateur avec le rôle ROLE_USER et retourne un token JWT"
    )
    @PostMapping("/register")
    public AuthResponse register(
            @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }

    @Operation(
            summary = "Connexion utilisateur",
            description = "Authentifie un utilisateur et retourne un token JWT valide"
    )
    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}