package com.ticket.tracking;

import com.ticket.tracking.entity.role.LoginUser;
import com.ticket.tracking.entity.role.Role;
import com.ticket.tracking.repository.LoginUserRepository;
import com.ticket.tracking.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, LoginUserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        return args -> {

            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);

                LoginUser user = new LoginUser();
                user.setEmail("admin@gmail.com");
                user.setPassword(bCryptPasswordEncoder.encode("s123"));
                user.setEnabled(true);
                user.setFullName("Admin");
                user.setUserRole("ADMIN");
                Role userRole = roleRepository.findByRole("ADMIN");
                user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
                userRepository.save(user);
            }

            Role pmRole = roleRepository.findByRole("PM");
            if (pmRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("PM");
                roleRepository.save(newUserRole);
            }

            Role qarRole = roleRepository.findByRole("QA");
            if (qarRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("QA");
                roleRepository.save(newUserRole);
            }

            Role rdRole = roleRepository.findByRole("RD");
            if (rdRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("RD");
                roleRepository.save(newUserRole);
            }

        };

    }

}
