package com.example.walletmanager.service;

import com.example.walletmanager.entity.User;
import java.util.List;

public interface UserService {
    
    User register(User user);
    
    String authenticate(String email, String password);

    void updateUser(Long userId, User newUser);

    void deleteUserById(Long userId);
    
    User findUserById(Long id);

    User findUserByEmail(String email);
    
    List<User> findAll();

    Boolean verify(String token, User user);

    public User getUserWithJwtToken(String authHeader);
}
