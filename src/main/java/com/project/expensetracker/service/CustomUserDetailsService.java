package com.project.expensetracker.service;

import com.project.expensetracker.entity.User;
import com.project.expensetracker.repository.UserRepository;
import com.project.expensetracker.service.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);

        if (user == null)
            throw new UsernameNotFoundException("User not found with email: " + email);

        return new CustomUserDetails(user);
    }
}
