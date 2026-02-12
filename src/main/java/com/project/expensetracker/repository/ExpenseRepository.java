package com.project.expensetracker.repository;

import com.project.expensetracker.entity.Expense;
import com.project.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Get all expenses for the logged-in user
    List<Expense> findByUser(User user);

    // Filter by category
    List<Expense> findByUserAndCategory(User user, String category);

    // Filter by date
    List<Expense> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
//    @Query("SELECT e FROM Expense e WHERE " +
//            "LOWER(e.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(e.category) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
//            "LOWER(e.description) LIKE LOWER(CONCAT('%', :query, '%'))")
//    List<Expense> searchExpenses(@Param("query") String query);
    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND (" +
            "LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.category) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')) )")
    List<Expense> searchByUserAndKeyword(@Param("userId") Long userId,
                                         @Param("keyword") String keyword);

    List<Expense> findByUserId(Long userId);
}
