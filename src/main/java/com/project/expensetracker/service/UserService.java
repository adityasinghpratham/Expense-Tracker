package com.project.expensetracker.service;

import com.project.expensetracker.entity.User;
import com.project.expensetracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
    public User findUserByEmail(String email) {
        return userRepo.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
