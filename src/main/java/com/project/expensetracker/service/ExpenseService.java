package com.project.expensetracker.service;

import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.project.expensetracker.entity.Expense;
import com.project.expensetracker.entity.User;
import com.project.expensetracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepo;

    // Get all expenses of logged-in user
    public List<Expense> getExpenses(User user) {
        return expenseRepo.findByUser(user);
    }

    // Add a new expense
    public Expense addExpense(Expense expense) {
        return expenseRepo.save(expense);
    }

    // Update an existing expense
    public Expense updateExpense(Long id, Expense updatedExpense, User user) {
        Expense expense = expenseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        // allow only owner to update
        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot update this expense");
        }

        expense.setTitle(updatedExpense.getTitle());
        expense.setAmount(updatedExpense.getAmount());
        expense.setCategory(updatedExpense.getCategory());
        expense.setDate(updatedExpense.getDate());
        expense.setDescription(updatedExpense.getDescription());

        return expenseRepo.save(expense);
    }

    // Delete expense
    public void deleteExpense(Long id, User user) {
        Expense expense = expenseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You cannot delete this expense");
        }

        expenseRepo.delete(expense);
    }

    // Filter by category
    public List<Expense> filterByCategory(User user, String category) {
        return expenseRepo.findByUserAndCategory(user, category);
    }

    // Filter by date range
    public List<Expense> filterByDate(User user, LocalDate start, LocalDate end) {
        return expenseRepo.findByUserAndDateBetween(user, start, end);
    }

    public byte[] generatePdf(User user) throws IOException {
        List<Expense> expenses = expenseRepo.findByUser(user);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Expense Report for " + user.getFullName()).setBold().setFontSize(16));

        Table table = new Table(new float[]{4, 2, 2, 2, 4});
        table.addHeaderCell("Title");
        table.addHeaderCell("Amount");
        table.addHeaderCell("Category");
        table.addHeaderCell("Date");
        table.addHeaderCell("Description");

        for (Expense e : expenses) {
            table.addCell(e.getTitle());
            table.addCell(String.valueOf(e.getAmount()));
            table.addCell(e.getCategory());
            table.addCell(e.getDate().toString());
            table.addCell(e.getDescription());
        }

        document.add(table);
        document.close();

        return baos.toByteArray();
    }

    public List<Expense> searchExpenses(Long userId, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return expenseRepo.findByUserId(userId);
        }
        return expenseRepo.searchByUserAndKeyword(userId, keyword);
    }

    public List<Expense> getAllExpenses(Long userId) {
        return expenseRepo.findByUserId(userId);
    }
}
