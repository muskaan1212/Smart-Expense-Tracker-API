package com.muskaan.expensetracker.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateExpenseRequest(
    @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
    @NotBlank String category,
    @NotBlank String description,
    @NotNull @PastOrPresent LocalDate date) {}
