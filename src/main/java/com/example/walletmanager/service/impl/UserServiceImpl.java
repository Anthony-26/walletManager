package com.example.walletmanager.service.impl;

import com.example.walletmanager.entity.Role;
import com.example.walletmanager.entity.User;
import com.example.walletmanager.repository.UserRepository;
import com.example.walletmanager.security.JwtService;
import com.example.walletmanager.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public void updateUser(Long userId, User updatedUser){
        User userToModify = findUserById(userId);
        userToModify.setEmail(updatedUser.getUsername());
        userToModify.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userRepository.save(userToModify);
    }

    @Override
    public void deleteUserById(Long userId){
        userRepository.deleteById(userId);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Boolean verify(String token, User user) {
        return jwtService.isTokenValid(token, user);
    }

    @Override
    public User getUserWithJwtToken(String authHeader){
        String jwtToken = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(jwtToken);
        return userRepository.findByEmail(userEmail).get();
    }

    public boolean isTheSameUser(Authentication authentication, Long userId){
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                User user = findUserByEmail(userDetails.getUsername());
                if(userId != user.getId()) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
                return true;
            }
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
    }

}