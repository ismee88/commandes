package com.polytech.commandes.init;

import com.polytech.commandes.entity.Role;
import com.polytech.commandes.entity.Utilisateur;
import com.polytech.commandes.repository.RoleRepository;
import com.polytech.commandes.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class InitAdmin implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (utilisateurRepository.findByUsername("admin").isEmpty()) {

            Role adminRole = roleRepository
                    .findByName("ROLE_ADMIN")
                    .orElseThrow();

            Utilisateur admin = new Utilisateur();

            admin.setUsername("admin");
            admin.setEmail("admin@test.com");
            admin.setPassword(
                    passwordEncoder.encode("admin123")
            );
            admin.setEnabled(true);

            admin.getRoles().add(adminRole);

            utilisateurRepository.save(admin);

            System.out.println("ADMIN créé avec succès");
        }
    }
}