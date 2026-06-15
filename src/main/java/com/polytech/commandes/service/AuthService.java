package com.polytech.commandes.service;

import com.polytech.commandes.dto.AuthResponse;
import com.polytech.commandes.dto.LoginRequest;
import com.polytech.commandes.dto.RegisterRequest;
import com.polytech.commandes.entity.Role;
import com.polytech.commandes.entity.Token;
import com.polytech.commandes.entity.Utilisateur;
import com.polytech.commandes.repository.RoleRepository;
import com.polytech.commandes.repository.TokenRepository;
import com.polytech.commandes.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {

        if(utilisateurRepository.findByUsername(request.username()).isPresent()) {
            throw new RuntimeException("Username deja utilise");
        }

        Utilisateur user = new Utilisateur();

        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(
                passwordEncoder.encode(request.password())
        );

        user.setEnabled(true);

        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() ->
                        new RuntimeException("ROLE_USER introuvable"));

        Set<Role> roles = new HashSet<>();
        roles.add(roleUser);

        user.setRoles(roles);

        utilisateurRepository.save(user);

        String jwt = jwtService.generateToken(
                user.getUsername()
        );

        Token token = new Token();
        token.setToken(jwt);
        token.setUtilisateur(user);
        token.setRevoked(false);
        token.setExpiredDate(
                new Date(System.currentTimeMillis() + 86400000)
        );

        tokenRepository.save(token);

        return new AuthResponse(jwt);
    }

    public AuthResponse login(LoginRequest request) {

        Utilisateur user =
                utilisateurRepository.findByUsername(request.username())
                        .orElseThrow(() ->
                                new RuntimeException("Utilisateur introuvable"));

        if(!passwordEncoder.matches(
                request.password(),
                user.getPassword()
        )) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        String jwt = jwtService.generateToken(
                user.getUsername()
        );

        Token token = new Token();

        token.setToken(jwt);
        token.setUtilisateur(user);
        token.setRevoked(false);
        token.setExpiredDate(
                new Date(System.currentTimeMillis() + 86400000)
        );

        tokenRepository.save(token);

        return new AuthResponse(jwt);
    }
}