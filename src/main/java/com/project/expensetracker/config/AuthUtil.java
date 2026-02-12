package com.project.expensetracker.config;

import com.project.expensetracker.entity.User;
import com.project.expensetracker.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private final UserService userService;

    public AuthUtil(UserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof String) {
            // principal is the email of the logged-in user
            String email = (String) principal;
            return userService.findUserByEmail(email);  // fetch your User entity from DB
        } else if (principal instanceof User user) {
            // if you somehow stored User object in authentication
            return user;
        }

        return null;
    }
}
