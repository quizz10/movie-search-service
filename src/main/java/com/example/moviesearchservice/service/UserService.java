package com.example.moviesearchservice.service;

import com.example.moviesearchservice.model.User;
import com.example.moviesearchservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    public User findUserById(long userId) {
        return userRepository.findUserByUserId(userId).orElseThrow(IllegalArgumentException::new);
    }
}
