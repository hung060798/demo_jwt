package com.example.demojwt.service;

import com.example.demojwt.entity.User;
import com.example.demojwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
     public User save(User user){
         userRepository.save(user);
         return user;
     }
}
