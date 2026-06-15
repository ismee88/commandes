package com.polytech.commandes.init;

import com.polytech.commandes.entity.Role;
import com.polytech.commandes.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class InitRoles implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        if(roleRepository.findByName("ROLE_ADMIN").isEmpty()) {

            Role admin = new Role();
            admin.setName("ROLE_ADMIN");

            roleRepository.save(admin);
        }

        if(roleRepository.findByName("ROLE_USER").isEmpty()) {

            Role user = new Role();
            user.setName("ROLE_USER");

            roleRepository.save(user);
        }
    }
}