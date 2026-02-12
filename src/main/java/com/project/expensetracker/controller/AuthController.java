package com.project.expensetracker.controller;

import com.project.expensetracker.service.CustomUserDetails;
import com.project.expensetracker.entity.User;
import com.project.expensetracker.service.UserService;
import com.project.expensetracker.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserService userService;
    private final MailService mailService;
    @PostMapping("/signup")
    public String signup(@RequestBody User user) {
        userService.register(user);
        String subject = "Welcome to Expense Tracker!";
        String body = "Hi " + user.getFullName() + ",\n\n" +
                "Thank you for signing up for Expense Tracker.\n" +
                "You can now add and manage your expenses effortlessly.\n\n" +
                "Best Regards,\nExpense Tracker Team";

        mailService.sendEmail(user.getEmail(), subject, body);

        return "Signup successful! Check your email for welcome message.";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpSession session) {

        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),   // changed
                        user.getPassword()
                )
        );

        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

        session.setAttribute("user", userDetails.getUser());

        return "Login successful";
    }

    @GetMapping("/me")
    public User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "Logged out";
    }
}
