package com.example.walletmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.example.walletmanager.entity.Role;
import com.example.walletmanager.entity.User;
import com.example.walletmanager.repository.UserRepository;
import com.example.walletmanager.service.impl.UserServiceImpl;

import lombok.RequiredArgsConstructor;


@SpringBootApplication
@RequiredArgsConstructor
public class WalletmanagerApplication implements ApplicationListener<ContextRefreshedEvent>{

	private final UserRepository userRepository;
	private final UserServiceImpl userServiceImpl;

	public static void main(String[] args) {
		SpringApplication.run(WalletmanagerApplication.class, args);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		/* Adding ADMIN account to database */
		if(userServiceImpl.findUserByEmail("admin") == null){
			User admin = new User("admin", "$2a$10$IxyyhWP1L837hrPM1kvzLO7/A/6B5t3nqc7Drb5dTjav9VJbwvr6W");
			admin.setRole(Role.ROLE_ADMIN);
			userRepository.save(admin);
		}
		
	}
}