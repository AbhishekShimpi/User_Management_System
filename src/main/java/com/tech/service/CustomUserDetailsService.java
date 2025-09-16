package com.tech.service;

import com.tech.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.tech.repository.UserRepository;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		com.tech.model.User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				user.getRoles().stream()
					.map(role -> new SimpleGrantedAuthority(role.getName()))
					.collect(Collectors.toList())
		);
	}
}