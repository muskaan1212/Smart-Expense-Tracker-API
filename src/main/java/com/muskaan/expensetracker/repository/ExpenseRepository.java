package com.muskaan.expensetracker.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.muskaan.expensetracker.model.Expense;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class ExpenseRepository {
  private final Path file;
  private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
  private final List<Expense> expenses;

  public ExpenseRepository(@Value("${expense.storage.file}") String fileName) {
    this.file = Path.of(fileName);
    this.expenses = load();
  }

  public synchronized List<Expense> findAll() { return List.copyOf(expenses); }
  public synchronized Expense save(Expense expense) { expenses.add(expense); persist(); return expense; }
  public synchronized boolean deleteById(long id) { boolean removed = expenses.removeIf(e -> e.id() == id); if (removed) persist(); return removed; }

  private List<Expense> load() {
    if (!Files.exists(file)) return new ArrayList<>();
    try { return new ArrayList<>(mapper.readValue(file.toFile(), new TypeReference<>() {})); }
    catch (IOException e) { throw new IllegalStateException("Unable to load expense data", e); }
  }
  private void persist() {
    try { if (file.getParent() != null) Files.createDirectories(file.getParent()); mapper.writerWithDefaultPrettyPrinter().writeValue(file.toFile(), expenses); }
    catch (IOException e) { throw new IllegalStateException("Unable to persist expense data", e); }
  }
}
