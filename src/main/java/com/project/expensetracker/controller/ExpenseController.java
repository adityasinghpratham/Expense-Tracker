package com.project.expensetracker.controller;

import com.itextpdf.io.exceptions.IOException;
import com.project.expensetracker.config.AuthUtil;
import com.project.expensetracker.entity.Expense;
import com.project.expensetracker.entity.User;
import com.project.expensetracker.repository.ExpenseRepository;
import com.project.expensetracker.service.ExpenseService;
import com.project.expensetracker.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ExpenseController {

    private final UserService userService;
    private final ExpenseService expenseService;
    private final ExpenseRepository expenseRepository;
    private final AuthUtil authUtil;
    // Helper method to get logged-in user from session
    private User getUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("User not logged in");
        }
        return user;
    }

    // Get all expenses for logged-in user
    @GetMapping
    public List<Expense> getAll(HttpSession session) {
        return expenseService.getExpenses(getUser(session));
    }

    // Add new expense
    @PostMapping
    public Expense add(@RequestBody Expense expense, HttpSession session) {
        expense.setUser(getUser(session));
        return expenseService.addExpense(expense);
    }

    // Update existing expense
    @PutMapping("/{id}")
    public Expense update(
            @PathVariable Long id,
            @RequestBody Expense updatedExpense,
            HttpSession session
    ) {
        User user = getUser(session);
        return expenseService.updateExpense(id, updatedExpense, user);
    }

    // Delete expense
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        User user = getUser(session);
        expenseService.deleteExpense(id, user);
        return "Expense deleted successfully";
    }

    // Filter expenses by category
    @GetMapping("/category")
    public List<Expense> filterByCategory(
            @RequestParam String category,
            HttpSession session
    ) {
        return expenseService.filterByCategory(getUser(session), category);
    }

    // Filter expenses by date range
    @GetMapping("/date")
    public List<Expense> filterByDate(
            @RequestParam String start,
            @RequestParam String end,
            HttpSession session
    ) {
        return expenseService.filterByDate(
                getUser(session),
                LocalDate.parse(start),
                LocalDate.parse(end)
        );
    }
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPdf(HttpSession session) throws IOException {
        User user = getUser(session);
        byte[] pdfBytes = expenseService.generatePdf(user);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=expenses.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }



    @GetMapping("/search")
    public ResponseEntity<List<Expense>> search(@RequestParam String keyword) {
        User currentUser = authUtil.getCurrentUser();
        if (currentUser == null) {
            return ResponseEntity.status(401).build();
        }

        List<Expense> results = expenseService.searchExpenses(currentUser.getId(), keyword);
        return ResponseEntity.ok(results);
    }

}
