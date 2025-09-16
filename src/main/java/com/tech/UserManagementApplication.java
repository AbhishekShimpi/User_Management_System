package com.tech;

import com.tech.model.Role;
import com.tech.model.User;
import com.tech.repository.RoleRepository;
import com.tech.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class UserManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementApplication.class, args);
	}
	
	
	 
	@Bean
	CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Step 1: Initialize Roles if they don't exist
			Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
			if (userRole.isEmpty()) {
				Role roleUser = new Role();
				roleUser.setName("ROLE_USER");
				roleRepository.save(roleUser);
			}
      
			Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
			if (adminRole.isEmpty()) {
				Role roleAdmin = new Role();
				roleAdmin.setName("ROLE_ADMIN");
				roleRepository.save(roleAdmin);
			}

			// Step 2: Create Admin user if they don't exist
			if (userRepository.findByEmail("admin@example.com").isEmpty()) {
				User admin = new User();
				admin.setName("Admin");
				admin.setEmail("admin@example.com");
				admin.setPassword(passwordEncoder.encode("admin123"));
				
				// Fetch the admin role from the database and set it
				Role foundAdminRole = roleRepository.findByName("ROLE_ADMIN").get();
				admin.setRoles(Set.of(foundAdminRole));
				
				userRepository.save(admin);
				System.out.println("Admin user created: admin@example.com / admin123");
			}
		};
	}
}