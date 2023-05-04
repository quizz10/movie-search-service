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

    public Optional<User> findUserById(long userId) {
        return Optional.ofNullable(userRepository.findUserByUserId(userId).orElseThrow(IllegalArgumentException::new));
    }
}
