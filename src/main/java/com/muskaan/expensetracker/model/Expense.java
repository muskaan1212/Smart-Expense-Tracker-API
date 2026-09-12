package com.muskaan.expensetracker.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Expense(long id, BigDecimal amount, String category, String description, LocalDate date) {}
