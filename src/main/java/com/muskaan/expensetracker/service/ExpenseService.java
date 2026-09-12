package com.muskaan.expensetracker.service;

import com.muskaan.expensetracker.model.CreateExpenseRequest;
import com.muskaan.expensetracker.model.Expense;
import com.muskaan.expensetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
public class ExpenseService {
  private final ExpenseRepository repository;
  public ExpenseService(ExpenseRepository repository) { this.repository = repository; }

  public Expense create(CreateExpenseRequest request) {
    long id = repository.findAll().stream().mapToLong(Expense::id).max().orElse(0) + 1;
    return repository.save(new Expense(id, request.amount(), request.category().trim(), request.description().trim(), request.date()));
  }
  public List<Expense> list(String category) {
    if (category == null || category.isBlank()) return repository.findAll();
    String wanted = category.trim().toLowerCase(Locale.ROOT);
    return repository.findAll().stream().filter(e -> e.category().toLowerCase(Locale.ROOT).equals(wanted)).toList();
  }
  public BigDecimal total(String category) { return list(category).stream().map(Expense::amount).reduce(BigDecimal.ZERO, BigDecimal::add); }
  public void delete(long id) { if (!repository.deleteById(id)) throw new ExpenseNotFoundException(id); }
}
