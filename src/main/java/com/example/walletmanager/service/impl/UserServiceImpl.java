package com.example.walletmanager.service.impl;

import com.example.walletmanager.entity.Role;
import com.example.walletmanager.entity.User;
import com.example.walletmanager.repository.UserRepository;
import com.example.walletmanager.security.JwtService;
import com.example.walletmanager.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        var jwtToken = jwtService.generateToken(user);
        System.out.println(jwtToken);
        return userRepository.save(user);
    }

    @Override
    public String authenticate(String email, String password) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(email, password)
        );
        User user = userRepository.findByEmail(email).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return jwtToken;
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Boolean verify(String token, User user) {
        return jwtService.isTokenValid(token, user);
    }

}
