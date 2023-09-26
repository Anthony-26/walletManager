package com.example.walletmanager.service;

import com.example.walletmanager.entity.User;
import java.util.List;

public interface UserService {
    
    User register(User user);
    
    String authenticate(String email, String password);
    
    User findUserById(Long id);

    User findByEmail(String email);

    User update(User user);
    
    void deleteById(Long id);
    
    List<User> findAll();

    Boolean verify(String token, User user);
}
